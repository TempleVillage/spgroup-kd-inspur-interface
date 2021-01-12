package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.constant.InspurConstant;
import com.kingdee.constant.InspurWCConstant;
import com.kingdee.model.po.InvBal;
import com.kingdee.model.po.Inventory;
import com.kingdee.model.po.Material;
import com.kingdee.model.vo.Result;
import com.kingdee.service.InvBalService;
import com.kingdee.token.TokenKit;
import com.kingdee.util.CommonBizUtil;
import com.kingdee.util.K3CloudUtil;
import com.kingdee.util.URLTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description 存货余额
 * @date 2020/11/12 17:23
 */
@RequestMapping
@RestController
public class InvBalController {
    @Autowired
    private InvBalService invBalService;

    SimpleDateFormat noFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String lastPeriod = CommonBizUtil.getLastPeriod();

    @MyLog(value = "存货余额传递")
    @RequestMapping("/synInvBal")
    public Result synInvBal() {
        System.out.println(new Date() + " 存货余额后台事务开始");
        Result result = new Result();
        result.setType(1);
        result.setRequest("存货余额请求");
        result.setResult("");
        //Cloud
        result.setResult(result.getResult() + synInvBalFromCloud());
        //Wise
        result.setResult(result.getResult() + synInvBalFromWise());
        return result;
    }

    private String synInvBalFromCloud() {
        String str_result = "";
        List<List<Object>> response = new ArrayList<List<Object>>();
        Long startTime = System.currentTimeMillis();
        //老的库存余额
        //String request = "{\"FormId\":\"STK_InvBal\",\"FieldKeys\":\"FMaterialId.fnumber,FLot,FBaseEndQty,FBaseUnitId.FNumber,FBalDate\",\"FilterString\":\"FStockOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        //新做的视图+单据
        lastPeriod = "201912";
        String request = ""
                + "{\"FormId\":\"PAEZ_CVHSBALANCE\",\"FieldKeys\":\"FNumber,FYear,FPeriod,FUnit,FQty,FAmount,forgNumber\"," +
                "\"FilterString\":\"FYear = " + Integer.valueOf(lastPeriod.substring(0, 4)) + "and FPeriod = " + Integer.valueOf(lastPeriod.substring(4, 6))   + "\"," +
                "\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";

        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
            //先擦除当前期间远程浪潮或本地备份数据库中的数据，然后再持久化新数据
            if(response.size() > 0){
                InvBal invBal = new InvBal();
                invBal.setVqj(lastPeriod);
                invBalService.eraseByPeriod_Local(invBal);
                invBalService.eraseByPeriod_Inspur(invBal);
            }
            //然后再持久化
            for (int i = 0; i < response.size(); i++) {
                InvBal invBal = new InvBal();
                List<Object> oneInfo = response.get(i);
                invBal.setVfid("Cloud_" + format.format(new Date()) + oneInfo.get(0).toString());
                invBal.setVgsdm(InspurConstant.LMGSDM);
                invBal.setVgsdm(oneInfo.get(6).toString());
                invBal.setVqj(lastPeriod);
                //invBal.setVywrq(format.format(new Date()));
                if (null != oneInfo.get(0)) {
                    invBal.setVwlbm(oneInfo.get(0).toString());
                } else {
                    invBal.setVwlbm("");
                }
                if (null != oneInfo.get(3)) {
                    invBal.setVjldw(oneInfo.get(3).toString());
                } else {
                    invBal.setVjldw("");
                }
                invBal.setVpch("");
                //数量
                if (null != oneInfo.get(4)) {
                    invBal.setNsl(new BigDecimal(oneInfo.get(4).toString()));
                } else {
                    invBal.setNsl(BigDecimal.ZERO);
                }
                //金额
                if (null != oneInfo.get(5)) {
                    invBal.setNje(new BigDecimal(oneInfo.get(5).toString()));
                } else {
                    invBal.setNje(BigDecimal.ZERO);
                }
                invBal.setCrkrq("");
                invBal.setDcjrq(new Date());
                invBal.setDxgrq(new Date());
                invBalService.saveInspur(invBal);
                invBalService.saveLocal(invBal);
            }
            str_result += "6、wise存货余额数据操作完成！\n任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "6、wise存货余额数据操作未能完成，请核查详细日志！\n 任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }

    public String synInvBalFromWise() {
        String str_result = "";
        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            String sql = "select bal.FItemID,ic.fnumber,ic.fname,bal.FBatchNo,bal.FEndQty,bal.FEndBal,bal.FKFDate,"
                    + " bal.FPeriod,bal.FYear,bal.FStockInDate,un.fnumber AS MNUMBER"
                    + " from icinvbal bal"
                    + " left join t_ICItem ic on ic.FItemID = bal.FItemID"
                    + " LEFT JOIN t_MeasureUnit un on un.FItemID =ic.FUnitID"
                + " where bal.FPeriod = 10 and bal.FYear = 2020";
            result = Db.use(dataSource).query(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            for (int i = 0; i < result.size(); i++) {
                InvBal invBal = new InvBal();
                invBal.setVfid("Wise_" + i + format.format(new Date()) + result.get(i).get("FItemID"));
                invBal.setVgsdm(InspurWCConstant.GSDM);
                invBal.setVqj(lastPeriod);
                if (null != result.get(i).get("FItemID")) {
                    invBal.setVwlbm(result.get(i).get("FItemID").toString());
                } else {
                    invBal.setVwlbm("");
                }
                if (null != result.get(i).get("FBatchNo")) {
                    invBal.setVpch(result.get(i).get("FBatchNo").toString());
                } else {
                    invBal.setVpch("");
                }
                if (null != result.get(i).getBigDecimal("FEndBal")) {
                    invBal.setNje(result.get(i).getBigDecimal("FEndBal"));
                } else {
                    invBal.setNje(BigDecimal.ZERO);
                }
                if (null != result.get(i).getBigDecimal("FEndQty")) {
                    invBal.setNsl(result.get(i).getBigDecimal("FEndQty"));
                } else {
                    invBal.setNsl(BigDecimal.ZERO);
                }
                if (null != result.get(i).get("MNUMBER")) {
                    invBal.setVjldw(result.get(i).get("MNUMBER").toString());
                } else {
                    invBal.setVjldw("");
                }
                if (null != result.get(i).get("FKFDate") && !"".equals(result.get(i).get("FKFDate"))) {
                    invBal.setCrkrq(noFormat.format(format.parse(result.get(i).get("FKFDate").toString())));
                    invBal.setDcjrq(noFormat.parse(result.get(i).get("FKFDate").toString()));
                    invBal.setDxgrq(format.parse(result.get(i).get("FKFDate").toString()));
                } else {
                    invBal.setCrkrq("");
                    invBal.setDcjrq(new Date());
                    invBal.setDxgrq(new Date());
                }
                //invBalService.saveInspur(invBal);
                invBalService.saveLocal(invBal);
            }
            str_result += "6、wise存货余额数据操作完成！\n任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "6、wise存货余额数据操作未能完成，请核查详细日志！\n 任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }
}
