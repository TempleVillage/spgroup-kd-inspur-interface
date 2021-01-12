package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.SaleOrderEntry;
import com.kingdee.model.vo.Result;
import com.kingdee.service.SaleOrderEntryService;
import com.kingdee.util.K3CloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jambin
 * @description 销售订单分录
 * @date 2020/11/12 17:49
 */
@RequestMapping
@RestController
public class SaleOrderEntryController {
    @Autowired
    private SaleOrderEntryService saleOrderEntryService;

    @MyLog(value = "销售订单分录")
    @RequestMapping("/synaleOrderEntry")
    public Result synaleOrderEntry() {
        Result result = new Result();
        result.setType(1);
        result.setRequest("销售订单分录请求");
        result.setResult("");
        System.out.println(new Date() + " 销售订单分录同步后台事务开始");
        result.setResult(result.getResult() + synaleOrderEntryFromCloud());
        //result.setResult(result.getResult() + synaleOrderEntryFromWise());
        return result;
    }

    private String synaleOrderEntryFromCloud() {
        String str_result = "";
        Long startTime = System.currentTimeMillis();
        List<List<Object>> responseSaleOrder = new ArrayList<List<Object>>();
        //to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FSaleOrderEntry_FEntryID,FBillNo,FMaterialId.fnumber,FQty,FBaseUnitId.fnumber,FAmount_LC,FAllAmount_LC,FCreateDate,FModifyDate,FSaleOrderEntry_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FSaleOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FSaleOrderEntry_FEntryID,FBillNo,FMaterialId.fnumber,FQty,FBaseUnitId.fnumber,FAmount_LC,FAllAmount_LC,FCreateDate,FModifyDate,FSaleOrderEntry_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FSaleOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FSaleOrderEntry_FEntryID,FBillNo,FMaterialId.fnumber,FQty,FBaseUnitId.fnumber,FAmount_LC,FAllAmount_LC,FCreateDate,FModifyDate,FSaleOrderEntry_FSEQ,FSaleOrgId.fnumber\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')>='2020-01-01'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FSaleOrderEntry_FEntryID,FBillNo,FMaterialId.fnumber,FQty,FBaseUnitId.fnumber,FAmount_LC,FAllAmount_LC,FCreateDate,FModifyDate,FSaleOrderEntry_FSEQ,FSaleOrgId.fnumber\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')>='2019-01-01' and to_char(FModifyDate,'yyyy-MM-dd')<='2019-12-31' \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            responseSaleOrder = client.executeBillQuery(request);
            for (int i = 0; i < responseSaleOrder.size(); i++) {
                List<Object> tempList = responseSaleOrder.get(i);
                SaleOrderEntry saleOrder = new SaleOrderEntry();
                saleOrder.setVgsdm(InspurConstant.LMGSDM); //公司代码
                saleOrder.setVgsdm(tempList.get(10).toString()); //公司代码
                saleOrder.setVfid("Cloud_" + tempList.get(0).toString()); //id
                saleOrder.setVtdbh(tempList.get(1).toString());//销售提单编号
                saleOrder.setVtdxmh(tempList.get(9).toString());
                saleOrder.setVwlbm(tempList.get(2).toString());//物料编码
                saleOrder.setNxssl(new BigDecimal(tempList.get(3).toString()));//销售数量
                saleOrder.setVjldw(tempList.get(4).toString());//计量单位
                saleOrder.setNxsje(new BigDecimal(tempList.get(5).toString()));
                saleOrder.setNhsxsje(new BigDecimal(tempList.get(6).toString()));//含税销售金额
                saleOrder.setDcjrq(DateUtil.parse(tempList.get(7).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));//创建日期
                saleOrder.setDxgrq(DateUtil.parse(tempList.get(8).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));//修改日期
                saleOrderEntryService.saveInspur(saleOrder);
                saleOrderEntryService.saveLocal(saleOrder);
            }
            str_result += "Cloud销售订单分录数据操作成功完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "Cloud销售订单分录数据操作未能完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }

    //wise
    private String synaleOrderEntryFromWise() {
        String str_result = "";
        Long startTime = System.currentTimeMillis();
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");

        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT se.FBIllNo,entry.fentryid ,item.fnumber as itemNumber ,entry.FQty,unit.FNumber as unitNumber,entry.FAmount ,entry.fallamount,se.FDATE FROM SEOrderEntry entry LEFT JOIN SEOrder se on se.finterid=entry.finterid LEFT JOIN t_ICItem item on item.fitemid=entry.fitemid LEFT JOIN t_MeasureUnit unit on unit.FMeasureUnitID =entry.FUnitID WHERE se.FCancellation=0 and  datediff(day, se.FDATE,getdate())=0 ORDER BY entry.finterid");
            for (int i = 0; i < result.size(); i++) {
                SaleOrderEntry saleOrder = new SaleOrderEntry();
                saleOrder.setVgsdm(InspurConstant.GDZYGSDM); //公司代码
                saleOrder.setVfid("Wise_" + result.get(i).get("FBIllNo") + result.get(i).get("fentryid")); //id
                saleOrder.setVtdbh(result.get(i).get("FBIllNo").toString());//销售提单编号
                saleOrder.setVtdxmh(result.get(i).get("fentryid").toString());
                saleOrder.setVwlbm(result.get(i).get("itemNumber").toString());//物料编码
                saleOrder.setNxssl(new BigDecimal(result.get(i).get("FQty").toString()));//销售数量
                saleOrder.setVjldw(result.get(i).get("unitNumber").toString());//计量单位
                saleOrder.setNxsje(new BigDecimal(result.get(i).get("FAmount").toString()));
                saleOrder.setNhsxsje(new BigDecimal(result.get(i).get("fallamount").toString()));//含税销售金额
                saleOrder.setDcjrq(result.get(i).getDate("FDATE"));//创建日期
                saleOrder.setDxgrq(result.get(i).getDate("FDATE"));//修改日期

                saleOrderEntryService.saveInspur(saleOrder);
                saleOrderEntryService.saveLocal(saleOrder);
            }
            str_result += "Wise销售订单分录数据操作成功完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "Wise销售订单分录数据操作未能完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }
}
