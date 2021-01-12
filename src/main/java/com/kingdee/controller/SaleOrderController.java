package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.SaleOrder;
import com.kingdee.model.vo.Result;
import com.kingdee.service.SaleOrderService;
import com.kingdee.util.K3CloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jambin
 * @description 销售单据体
 * @date 2020/11/12 17:52
 */
@RequestMapping
@RestController
public class SaleOrderController {
    @Autowired
    private SaleOrderService saleOrderService;

    @MyLog(value = "销售订单")
    @RequestMapping("/synSaleOrder")
    public Result synSaleOrder() {
        Result result = new Result();
        result.setType(1);
        result.setRequest("销售订单请求");
        result.setResult("");
        System.out.println("销售订单后台任务开始。。。");
        result.setResult(result.getResult() + synSaleOrderFromCloud());
        //result.setResult(result.getResult() + synSaleOrderFromWise());
        return result;
    }

    private String synSaleOrderFromCloud() {
        String str_result = "";
        Long startTime = System.currentTimeMillis();
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FID,FBillNo,FCustId.fnumber,FCreateDate as bizdate,FSettleCurrId.fnumber,FExchangeRate,FDocumentStatus,FBusinessType,FCreatorid.FUserAccount,FCreateDate,FModifyDate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FSaleOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FID,FBillNo,FCustId.fnumber,FCreateDate as bizdate,FSettleCurrId.fnumber,FExchangeRate,FDocumentStatus,FBusinessType,FCreatorid.FUserAccount,FCreateDate,FModifyDate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        //String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FID,FBillNo,FCustId.fnumber,FCreateDate as bizdate,FSettleCurrId.fnumber,FExchangeRate,FDocumentStatus,FBusinessType,FCreatorid.FUserAccount,FCreateDate,FModifyDate,FSaleOrgId.fnumber\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')>='2020-01-01') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FID,FBillNo,FCustId.fnumber,FCreateDate as bizdate,FSettleCurrId.fnumber,FExchangeRate,FDocumentStatus,FBusinessType,FCreatorid.FUserAccount,FCreateDate,FModifyDate,FSaleOrgId.fnumber\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')>='2019-01-01') and to_char(FModifyDate,'yyyy-MM-dd')<='2019-12-31') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        List<List<Object>> responseSaleOrder = new ArrayList<List<Object>>();
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
//            System.out.println(client);
            responseSaleOrder = client.executeBillQuery(request);
//            System.out.println(responseSaleOrder);
            for (int i = 0; i < responseSaleOrder.size(); i++) {
                List<Object> tempList = responseSaleOrder.get(i);
                SaleOrder saleOrder = new SaleOrder();
                saleOrder.setVgsdm(InspurConstant.LMGSDM); //公司代码
                saleOrder.setVgsdm(tempList.get(11).toString()); //公司代码
                saleOrder.setVfid("Cloud_" + tempList.get(0).toString()); //id
                saleOrder.setVxstdbh(tempList.get(1).toString());//销售提单编号
                saleOrder.setVkhbh(tempList.get(2).toString());//客户编号

                //saleOrder.setCqj(qjsdf.format(qjsdf.parse(tempList.get(3).toString()))); //期间
                saleOrder.setCqj(DateUtil.format(DateUtil.parse(tempList.get(3).toString().replaceAll("T", " ")), "yyyyMM"));
                saleOrder.setCdjrq(DateUtil.format(DateUtil.parse(tempList.get(3).toString().replaceAll("T", " ")), "yyyyMMdd"));//单据日期
                saleOrder.setVhblx(tempList.get(4).toString());//货币类型
                saleOrder.setNzhhl(new BigDecimal(tempList.get(5).toString()));//转换汇率
                //提单状态
                String status = tempList.get(6).toString();
                if ("Z".equals(status)) {
                    saleOrder.setVtdzt("1");
                } else if ("B".equals(status)) {
                    saleOrder.setVtdzt("2");
                } else if ("C".equals(status)) {
                    saleOrder.setVtdzt("3");
                } else {
                    saleOrder.setVtdzt("1");
                }
                saleOrder.setVxslx("1");//销售类型
                saleOrder.setVywy(tempList.get(8).toString());//业务员
                saleOrder.setDcjrq(DateUtil.parse(tempList.get(9).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));//创建日期
                saleOrder.setDxgrq(DateUtil.parse(tempList.get(10).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));//修改日期
                saleOrderService.saveInspur(saleOrder);
                saleOrderService.saveLocal(saleOrder);
            }
            str_result += "Cloud销售订单数据操作成功完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "Cloud销售订单数据操作未能完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }

    }

    private String synSaleOrderFromWise() {
        String str_result = "";
        Long startTime = System.currentTimeMillis();
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");

        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("  SELECT se.FUUID, se.FDATE,se.FBIllNo,cus.FNUMBER as cusNumber,se.FCurrencyID,se.FExChangeRate,se.fstatus,se.FSaleStyle,emp.FNumber as empNumber FROM SEOrder se LEFT JOIN t_Organization cus on cus.FItemID=se.FCustID LEFT JOIN t_Emp emp on emp.FItemID=se.FEmpID WHERE se.FCancellation=0 and  datediff(day, se.FDATE,getdate())=0");
            for (int i = 0; i < result.size(); i++) {
                SaleOrder saleOrder = new SaleOrder();
                saleOrder.setVgsdm(InspurConstant.GDZYGSDM); //公司代码
                saleOrder.setVfid("Wise_" + result.get(i).get("FUUID")); //id
                saleOrder.setVxstdbh(result.get(i).get("FBIllNo").toString());//销售提单编号
                saleOrder.setVkhbh(result.get(i).get("cusNumber").toString());//客户编号
                //saleOrder.setCqj(qjsdf.format(qjsdf.parse(tempList.get(3).toString()))); //期间
                saleOrder.setCqj(DateUtil.format(result.get(i).getDate("FDATE"), "yyyyMM"));
                saleOrder.setCdjrq(DateUtil.format(result.get(i).getDate("FDATE"), "yyyyMMdd"));//单据日期
                saleOrder.setVhblx(result.get(i).get("FCurrencyID").toString());//货币类型
                saleOrder.setNzhhl(new BigDecimal(result.get(i).getFloat("FExChangeRate")));//转换汇率
                //提单状态
                String status = result.get(i).get("fstatus").toString();
                if ("0".equals(status)) {
                    saleOrder.setVtdzt("1");
                } else if ("1".equals(status)) {
                    saleOrder.setVtdzt("2");
                } else {
                    saleOrder.setVtdzt("1");
                }
                saleOrder.setVxslx("1");//销售类型
                saleOrder.setVywy(result.get(i).get("empNumber").toString());//业务员
                saleOrder.setDcjrq(result.get(i).getDate("FDATE"));//创建日期
                saleOrder.setDxgrq(result.get(i).getDate("FDATE"));//修改日期
                saleOrderService.saveInspur(saleOrder);
                saleOrderService.saveLocal(saleOrder);
            }
            str_result += "Wise销售订单数据操作成功完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "Wise销售订单数据操作未能完成，请核查详细日志！\n 任务持续时长：" +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }
}

