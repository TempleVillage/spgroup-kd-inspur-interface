package com.kingdee.controller;

import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.model.po.PurchasePlans;
import com.kingdee.model.vo.Result;
import com.kingdee.service.PurchasePlansService;
import com.kingdee.util.K3CloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName PurchasePlansController
 * @Description TODO
 * @Autor Strong Li
 * @Date 11/19/2020 10:29
 * @Version V1.0
 **/
@RequestMapping
@RestController
public class PurchasePlansController {

    @Autowired
    private PurchasePlansService purchasePlansService;

    K3CloudApiClient client = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sjsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat noFormat = new SimpleDateFormat("yyyyMMdd");
    String gsdm = "2279"; //王朝公司代码
    String year, month;

    public String getUUID32() {

        String uUid32 = UUID.randomUUID().toString();
        // TODO Auto-generated method stub
        return uUid32.replace("-", "");
    }

    @MyLog(value = "采购申请单传递")
    @RequestMapping("/purchasePlans")
    public Result synPurchasePlans() {
//        Visitlog sysLog = new Visitlog();
        Result result = new Result();
        String str_result = "";
        String str_Date = noFormat.format(new Date());
        year = str_Date.substring(0, 4);
        month = str_Date.substring(4, 6);

        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        //调用接口
        List<List<Object>> response = new ArrayList<List<Object>>();
        //生产环境只能取当日数据
        String request =
                "{\"FormId\":\"PUR_Requisition\"," +
                        "\"FieldKeys\":\"FMaterialId.Fnumber,FMaterialName,FMaterialModel,FUnitId.FName," +
                        "FApplicationOrgId.FNumber,FApplicantId.FName,FReqQty,FBillNo,FEntity_FEntryid," +
                        "FModifyDate\"," +
                        "\"FilterString\":\"to_char(FCreateDate,'yyyy-MM-dd') >= '2020-04-01'\"," +
                        "\"OrderString\":\"\",\"TopRowCount\":\"1000\","+
                        "\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {

            if (null == client) {
                client = K3CloudUtil.getWCK3CloudApiClient();
            }
            str_result += "1、星空系统连接成功； \n";
            response = client.executeBillQuery(request);
            System.out.println(response);
            str_result += "2、星空数据采集成功，采集数据总量为：" + response.size() + " 条；\n";
            List<PurchasePlans> list_PurchasePlans = new ArrayList<PurchasePlans>();
            for (int i = 0; i < response.size(); i++) {
                List<Object> tempList = response.get(i);
                PurchasePlans purchasePlans = new PurchasePlans();
                if(null != tempList.get(0)) {
                    //根据对接方案，传输过来的物料信息中，物料编号前，需加上对应单位的单位编号，如ERP中，物料编号为“12345678”，单位编号为“0001”，则传输至中间表中的物料编号，应为“000112345678”；
                    purchasePlans.setMaterialcode(gsdm + tempList.get(0).toString()); //物料编号
                } else {
                    purchasePlans.setMaterialcode("");//人员姓名
                }
                if(null != tempList.get(1)) {
                    purchasePlans.setMaterialname(tempList.get(1).toString());//物料名称
                } else {
                    purchasePlans.setMaterialname("");//人员姓名
                }
                if(null != tempList.get(2)) {
                    purchasePlans.setSpec(tempList.get(2).toString());//物料规格型号
                } else {
                    purchasePlans.setSpec("");//人员姓名
                }
                if(null != tempList.get(3)) {
                    purchasePlans.setPripurunitid(tempList.get(3).toString());//计量单位
                } else {
                    purchasePlans.setPripurunitid("");//人员姓名
                }
                purchasePlans.setPlancompanyid(gsdm); //公司代码
                if(null != tempList.get(5)) {
                    purchasePlans.setCreator(tempList.get(5).toString());//人员姓名
                } else {
                    purchasePlans.setCreator("");//人员姓名
                }
                if(null != tempList.get(6)) {
                    purchasePlans.setQuantity(new BigDecimal(tempList.get(6).toString()));//物料需求数量
                } else {
                    purchasePlans.setQuantity(BigDecimal.ZERO);//人员姓名
                }
                if(null != tempList.get(7)) {
                    purchasePlans.setErpprbillcode(tempList.get(7).toString());//ERP系统   采购申请单据编号
                } else {
                    purchasePlans.setErpprbillcode("");//
                }
                if(null != tempList.get(8)) {
                    purchasePlans.setErpprbillitemcode(tempList.get(8).toString());//ERP系统   采购申请分录流水
                    //设置主键
                    //purchasePlans.setkPurchaseplans(tempList.get(8).toString());
                    purchasePlans.setkPurchaseplans(purchasePlans.getErpprbillcode() + "_" + purchasePlans.getErpprbillitemcode());
                } else {
                    purchasePlans.setErpprbillitemcode("");//getUUID32()
                    //purchasePlans.setkPurchaseplans(getUUID32());
                    purchasePlans.setkPurchaseplans(purchasePlans.getErpprbillcode() + "_" + purchasePlans.getErpprbillitemcode());
                }
                purchasePlans.setFiscalyear(year);//
                purchasePlans.setFiscalperiod(month);//
                Date modifyDate;
                if (null != tempList.get(9) ) {
                    String str_ModifyDate = tempList.get(9).toString().replaceAll("T", " ");
                    modifyDate = sjsdf.parse(str_ModifyDate);
                } else {
                    modifyDate = new Date();
                }
                purchasePlans.setGslasttime(modifyDate);//GS最后修改时间
                purchasePlans.setErplasttime(modifyDate);//ERP最后修改时间
                purchasePlans.setErpcode("金蝶云星空");//ERP业务系统标识
                purchasePlans.setPlanstate("0");
                //该处暂不设置错误状态，最终在本地和远程持久化那一刻再进行设置
                //purchasePlans.setWrongstste("1");
                purchasePlans.setText1("");
                purchasePlans.setText2("");
                purchasePlans.setText3("");
                purchasePlans.setText4("");
                purchasePlans.setText5("");
                purchasePlans.setDate1(new Date());
                purchasePlans.setDate2(new Date());
                list_PurchasePlans.add(purchasePlans);
            }
            str_result += "3、星空数据解析完成，解析后数据总量为：" + list_PurchasePlans.size() + "条； \n";
            if (list_PurchasePlans.size() > 0) {
                //此处逻辑需要修订，需要到库里查找，如发现浪潮库里一存在该数据（以ERPPRBILLCODE和Erpprbillitemcode来识别）
                //如果存在 1.1、当purchasePlans.getWrongstste（由浪潮对异常单据进行标记，并由ERP系统对异常单据更新后，重置为正常。1正常；2物料异常、3组织异常、4日期异常 ）的值为1时
                //  不能保存，直接跳过，1.2、为2\3\4时，需要现将该数据删除；然后再保存
                //如果不存在 2、可以直接保存
                //purchasePlansService.saveLocal(list_PurchasePlans);
                str_result += "4、抽取数据本地持久成功； \n";
                /**
                 * oracle 批量保存报错，采用循环保存
                 */
                for (PurchasePlans purchasePlans : list_PurchasePlans) {
                    //判断是否存在该申请单
                    //创建临时特征匹配项，主要有：ERP系统采购申请单据编号、ERP系统采购申请分录流水
                    PurchasePlans match_PurchasePlans = new PurchasePlans();
                    match_PurchasePlans.setErpprbillcode(purchasePlans.getErpprbillcode());
                    match_PurchasePlans.setErpprbillitemcode(purchasePlans.getErpprbillitemcode());
                    if(existPP(match_PurchasePlans)){
                        //判断存在的记录是否为异常数据
                        if(isWrongstste(match_PurchasePlans)){
                            //擦出清理该数据
                            eraseWrongststeData(purchasePlans);
                            //5、存储浪潮方介质
                            //5、
                            purchasePlans.setWrongstste("1");
                            System.out.println("=====pk: " + purchasePlans.getkPurchaseplans() + "=====\n");
                            purchasePlansService.saveInspurWC(purchasePlans);
                            purchasePlansService.saveLocal(purchasePlans);
                        }
                        //如果数据没问题则不做任何处理
                    } else {
                        //表明该数据不存在，可以直接保存
                        //5、存储本地介质
                        purchasePlans.setWrongstste("1");
                        System.out.println("=====pk: " + purchasePlans.getkPurchaseplans() + "=====\n");
                        purchasePlansService.saveInspurWC(purchasePlans);
                        purchasePlansService.saveLocal(purchasePlans);
                    }
              }
                //service_Inventory.saveInspur(list_Inventory);
                str_result += "5、抽取数据浪潮持久成功； \n";
            }
            result.setType(1);
        } catch (Exception e) {
            e.printStackTrace();
//            result.setType(-1);
            str_result += "6、库存余额数据操作未能完成，请核查详细日志！； \n";
        } finally {
            result.setResult(str_result);
            result.setRequest("库存余额请求");
            Long endTime= System.currentTimeMillis() - startTime;
            return result;
        }
    }

    private void eraseWrongststeData(PurchasePlans purchasePlans) {
        if(null != purchasePlans){
            try {
                purchasePlansService.clearInspurWC(purchasePlans);
                purchasePlansService.clearLocal(purchasePlans);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isWrongstste(PurchasePlans purchasePlans) {

        try {
            PurchasePlans findde_PurchasePlans = purchasePlansService.findOneInspurWC(purchasePlans);
            if(null != findde_PurchasePlans){
                if(null != findde_PurchasePlans.getWrongstste() && !"1".equals(findde_PurchasePlans.getWrongstste())){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean existPP(PurchasePlans purchasePlans) {

        try {
            PurchasePlans findde_PurchasePlans = purchasePlansService.findOneInspurWC(purchasePlans);
            if(null != findde_PurchasePlans){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
