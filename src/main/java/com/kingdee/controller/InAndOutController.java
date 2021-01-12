package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.InAndOut;
import com.kingdee.service.InAndOutService;
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
 * @description 出入库同步
 * @date 2020/11/12 17:17
 */
@RequestMapping
@RestController
public class InAndOutController {
    @Autowired
    private InAndOutService inAndOutService;

    @RequestMapping("/synInAndOut")
    public void synInAndOut() {
        System.out.println(new Date() + " 出入库后台事务开始");
        //星空同步,获取所有需要同步的单据的请求参数
        List<String> requestList = getCloudRequest();
        for (int i = 0; i < requestList.size(); i++) {
            synInAndOutFromCloud(requestList.get(i));
        }
        //wise同步
        synInAndOutFromWise();
    }

    /**
     * @param request 请求参数字符串
     * @return void
     * @description: 星空出入库同步
     * @date: 2020/11/20 11:36
     * @author: Jambin
     */
    private void synInAndOutFromCloud(String request) {
        List<List<Object>> response = new ArrayList<List<Object>>();
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析请求参数的单据类型，赋值不同的浪潮标识
        JSONObject jsonObject = JSONUtil.parseObj(request);
        String YDLX = jsonObject.get("FormId").toString();
        String vydlx = "";
        if ("SAL_OUTSTOCK".equals(YDLX)) {
            vydlx = "21";
        }
        if ("STK_InStock".equals(YDLX)) {
            vydlx = "20";
        }
        if ("PRD_INSTOCK".equals(YDLX)) {
            vydlx = "50";
        }
        if ("STK_MISCELLANEOUS".equals(YDLX)) {
            vydlx = "90";
        }
        if ("STK_MisDelivery".equals(YDLX)) {
            vydlx = "91";
        }
        //遍历同步
        for (int i = 0; i < response.size(); i++) {
            InAndOut inAndOut = new InAndOut();
            try {
                List<Object> oneInfo = response.get(i);
                inAndOut.setVgsdm(InspurConstant.LMGSDM);
                inAndOut.setVydlx(vydlx);
                inAndOut.setVfid("Cloud_" + oneInfo.get(1).toString() + "_" + oneInfo.get(0).toString());
                inAndOut.setVdjh(oneInfo.get(2).toString());
                inAndOut.setCcrkrq(DateUtil.format(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " ")), "yyyyMMdd"));
                inAndOut.setVwlbm(oneInfo.get(4).toString());
                inAndOut.setVpch(oneInfo.get(5).toString());
                inAndOut.setNsl(new BigDecimal(oneInfo.get(6).toString()));
                inAndOut.setVjldw(oneInfo.get(7).toString());
                inAndOut.setNje(new BigDecimal(oneInfo.get(8).toString()));
                inAndOut.setVhblx(oneInfo.get(9).toString());
                inAndOut.setDcjrq(DateUtil.parse(oneInfo.get(10).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                inAndOut.setDxgrq(DateUtil.parse(oneInfo.get(11).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                inAndOut.setVxmh(oneInfo.get(12).toString());
                //如果没有转换汇率则跳过该字段
                if (oneInfo.size() > 13) {
                    inAndOut.setNzhhl(new BigDecimal(oneInfo.get(13).toString()));
                } else {
                    if ("PRE001".equals(oneInfo.get(9).toString())) {
                        inAndOut.setNzhhl(new BigDecimal(1));
                    }
                }
                inAndOutService.saveInspur(inAndOut);
                inAndOutService.saveLocal(inAndOut);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise 同步        其他入库：10 ；其他出库：29 ； 销售出库：21；采购入库 :1 ;生产入库：2
     * @date: 2020/11/20 11:39
     * @author: Jambin
     */
    private void synInAndOutFromWise() {
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query(" SELECT bill.fuuid,bill.FTranType, bill.FBIllNo,entry.FEntryID, bill.FDATE, item.fnumber as itemNumber,entry.FBatchNo,entry.FQty ,unit.FNumber as unitNumber, entry.FAmount FROM ICStockBillEntry  entry LEFT JOIN ICStockBill bill on bill.FInterID=entry.FInterID LEFT JOIN t_ICItem item on item.fitemid=entry.fitemid LEFT JOIN t_MeasureUnit unit on unit.FMeasureUnitID =entry.FUnitID WHERE bill.FCancellation =0 and  datediff(day, bill.FDATE,getdate())=0 and bill.FTranType in (1,2,10,21,29)");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                InAndOut inAndOut = new InAndOut();
                inAndOut.setVgsdm(InspurConstant.GDZYGSDM);
                String vydlx = "";
                if ("10".equals(result.get(i).get("FTranType").toString())) {
                    vydlx = "90";
                }
                if ("29".equals(result.get(i).get("FTranType").toString())) {
                    vydlx = "91";
                }
                if ("21".equals(result.get(i).get("FTranType").toString())) {
                    vydlx = "21";
                }
                if ("1".equals(result.get(i).get("FTranType").toString())) {
                    vydlx = "20";
                }
                if ("2".equals(result.get(i).get("FTranType").toString())) {
                    vydlx = "50";
                }
                inAndOut.setVydlx(vydlx);
                inAndOut.setVfid("Wise_" + result.get(i).get("fuuid") + "_" + result.get(i).get("FEntryID"));
                inAndOut.setVdjh(result.get(i).get("FBIllNo").toString());
                inAndOut.setCcrkrq(DateUtil.format(result.get(i).getDate("FDATE"), "yyyyMMdd"));
                inAndOut.setVwlbm(result.get(i).get("itemNumber").toString());
                if (result.get(i).get("FBatchNo") == null) {
                    inAndOut.setVpch("");
                } else {
                    inAndOut.setVpch(result.get(i).get("FBatchNo").toString());
                }
                inAndOut.setNsl(new BigDecimal(result.get(i).get("FQty").toString()));
                inAndOut.setVjldw(result.get(i).get("unitNumber").toString());
                inAndOut.setNje(new BigDecimal(result.get(i).get("FAmount").toString()));
                inAndOut.setDcjrq(result.get(i).getDate("FDATE"));
                inAndOut.setDxgrq(result.get(i).getDate("FDATE"));
                inAndOut.setVxmh(result.get(i).get("FEntryID").toString());

                inAndOutService.saveInspur(inAndOut);
                inAndOutService.saveLocal(inAndOut);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return requestList 参数集合
     * @description: 获取星空请求参数
     * @date: 2020/11/20 11:37
     * @author: Jambin
     */
    private List<String> getCloudRequest() {
        List<String> requestList = new ArrayList<>();
        //销售出库
        //requestList.add("{\"FormId\":\"SAL_OUTSTOCK\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitID.fnumber,FBillAmount_LC,FSettleCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ,FExchangeRate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FSaleOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //采购入库
        //requestList.add("{\"FormId\":\"STK_InStock\",\"FieldKeys\":\"FInStockEntry_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitID.fnumber,FBillAmount_LC,FSettleCurrId.fnumber,FCreateDate,FModifyDate,FInStockEntry_FSEQ,FExchangeRate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FPurchaseOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //生产入库
        //requestList.add("{\"FormId\":\"PRD_INSTOCK\",\"FieldKeys\":\"FEntity_FEntryID,FFORMID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitId.fnumber,FAmount,FCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FStockOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //其他入库
        //requestList.add("{\"FormId\":\"STK_MISCELLANEOUS\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FQty,FBaseUnitId.fnumber,FAmount,FBaseCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FStockOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //其他出库
        //requestList.add("{\"FormId\":\"STK_MisDelivery\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FQty,FBaseUnitId.fnumber,FAmount,FBaseCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FStockOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //销售出库
        requestList.add("{\"FormId\":\"SAL_OUTSTOCK\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitID.fnumber,FBillAmount_LC,FSettleCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ,FExchangeRate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //采购入库
        requestList.add("{\"FormId\":\"STK_InStock\",\"FieldKeys\":\"FInStockEntry_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitID.fnumber,FBillAmount_LC,FSettleCurrId.fnumber,FCreateDate,FModifyDate,FInStockEntry_FSEQ,FExchangeRate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //生产入库
        requestList.add("{\"FormId\":\"PRD_INSTOCK\",\"FieldKeys\":\"FEntity_FEntryID,FFORMID,FBillNo,FDate,FMaterialId.fnumber,FLot,FRealQty,FBaseUnitId.fnumber,FAmount,FCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //其他入库
        requestList.add("{\"FormId\":\"STK_MISCELLANEOUS\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FQty,FBaseUnitId.fnumber,FAmount,FBaseCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        //其他出库
        requestList.add("{\"FormId\":\"STK_MisDelivery\",\"FieldKeys\":\"FEntity_FEntryID,FOBJECTTYPEID,FBillNo,FDate,FMaterialId.fnumber,FLot,FQty,FBaseUnitId.fnumber,FAmount,FBaseCurrId.fnumber,FCreateDate,FModifyDate,FEntity_FSEQ\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}");
        return requestList;
    }
}
