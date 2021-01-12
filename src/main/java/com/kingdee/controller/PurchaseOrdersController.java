package com.kingdee.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.model.po.PurchaseOrders;
import com.kingdee.model.po.PurchasePlans;
import com.kingdee.model.po.PurorderItem;
import com.kingdee.model.vo.Result;
import com.kingdee.model.vo.Visitlog;
import com.kingdee.service.LogService;
import com.kingdee.service.PurchaseOrdersService;
import com.kingdee.util.K3CloudUtil;
import org.n3r.idworker.Sid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PurchaseOrdersController
 * @Description TODO
 * @Autor Strong Li
 * @Date 11/20/2020 14:04
 * @Version V1.0
 **/
@RequestMapping
@RestController
public class PurchaseOrdersController {

    @Resource
    private PurchaseOrdersService purchaseOrdersService;

    @Resource
    private LogService logService;

    K3CloudApiClient client = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sjsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat noFormat = new SimpleDateFormat("yyyyMMdd");
    String gsdm = "2279"; //王朝公司代码
    String year, month;
    private Sid sid;


    /**
     * description 抽取采购订单信息
     * @param
     * @return
     */
    @MyLog(value = "王朝采购订单传递")
    @RequestMapping("/purchaseOrdersWC")
    public Result extractedPurchaseOrder2WCERP() {
        Visitlog sysLog = new Visitlog();
        Result result = new Result();
        String str_result = "";
        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        //调用接口
        //List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "";
        try {
            //查询 当日订单 参照状态为 0   由ERP系统对单据回写状态进行标记，共：0未生成；1已生产，两种状态
            PurchaseOrders purchaseOrders4Q = new PurchaseOrders();
            //purchaseOrders4Q.setCreatedate(noFormat.format(new Date()));
            purchaseOrders4Q.setOrderstate("0");
            List<PurchaseOrders> list_PurchaseOrders = purchaseOrdersService.findInspurWC(purchaseOrders4Q);
            if (null != list_PurchaseOrders && list_PurchaseOrders.size() > 0) {
                for (PurchaseOrders purchaseOrders : list_PurchaseOrders) {
                    //订单单头本地持久化
                    purchaseOrdersService.saveLocal(purchaseOrders);

                    //先做订单分录本地持久化
                    List<PurorderItem> list_PurorderItem = purchaseOrdersService.findItemByOrderIdInspurWC(purchaseOrders);
                    for (PurorderItem purorderItem : list_PurorderItem) {
                        purchaseOrdersService.saveItemLocal(purorderItem);
                    }
                    str_result += "0、本地中间表数据存储已完成； \n";

                    //再向远程终端推送数据,
                    //按照组织进行隔离获取采购订单明细
                    Map<String, List<PurorderItem>> map_PurorderItem = purchaseOrdersService.findSplitOrgItemByOrderIdInspurWC(purchaseOrders);
                    if (null != map_PurorderItem && map_PurorderItem.size() > 0) {

                        for (Map.Entry<String, List<PurorderItem>> entry_PurorderItem : map_PurorderItem.entrySet()) {
                            String company = entry_PurorderItem.getKey();
                            String actualComNumber = "", createDate = "";
                            List<PurorderItem> list_Com_PurorderItem = entry_PurorderItem.getValue();
                            createDate = filledWith10Bit4Date(purchaseOrders.getCreatedate());
                            //1、此时要特别注意：要根据分录里的结算组织进行分类了，不同的结算组织要推送到不同的星空终端
                            if ("2279".equals(company)) {
                                actualComNumber = "11";
                                client = K3CloudUtil.getWCK3CloudApiClient();
                                //根据供应商社会统一信用代码获取组织编码FNumber
                                //废弃
                                String supplierNumber = getSupplierNumberBySocialCode(client, purchaseOrders.getLswldwCertificate());
                                //1 组装订单单据表头信息
                                request += "{ \"Model\": { \"FBillNo\": \"" + purchaseOrders.getPurordercode() + "\""
                                        + ",\"FDate\": \"" + createDate + "\""
                                        + ",\"FSupplierId\": {\"FNumber\": \"" + supplierNumber + "\"}"
                                        + ",\"FPurchaseOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                        + ",\"FPurchaseDeptId\": {\"FNumber\": \"1115\"}"
                                        + ",\"FPurchaserId\": {\"FNumber\": \"110156\"}"
                                        + ",\"FSettleId\": {\"FNumber\": \"" + supplierNumber + "\"}";
                                //特别指明星空终端

                            }
                            str_result += "1、连接组织：" + company+ " 的星空系统成功； \n";
                            //List<PurorderItem> list_K3Cloud_PurorderItem = entry_PurorderItem.getValue();
                            if (null != list_Com_PurorderItem && list_Com_PurorderItem.size() > 0) {
                                //2 拼接分录保存字符串
                                request += ",\"FPOOrderEntry\": [";
                                int count = 0;
                                for (PurorderItem purorderItem : list_Com_PurorderItem) {
                                    //需将ERP系统中物料编号前加上4位组织编号
                                    String materialNumber = getMaterialNumber4BitCode(purorderItem.getMaterialcode());
                                    String srcBillId = getReqisitionBillId(client, purorderItem.getErpprbillcode());
                                    //配置分录
                                    request += "{ \"FEntryID\": " + count
                                            + ",\"FMaterialId\": {\"FNumber\": \"" + materialNumber + "\"}"
                                            + ",\"FUnitId\": {\"FName\": \"" + purorderItem.getPurunitid() + "\"}"
                                            + ",\"FStockUnitID\": {\"FName\": \"" + purorderItem.getPurunitid() + "\"}"
                                            + ",\"FQty\": " + purorderItem.getQuantity()
                                            + ",\"FPriceUnitQty\": " + purorderItem.getQuantity()
                                            + ",\"FPriceBaseQty\": " + purorderItem.getQuantity()
                                            + ",\"FDeliveryDate\": \"" + createDate + "\""
                                            + ",\"FTaxPrice\": " + purorderItem.getTaxinprice()
                                            + ",\"FRequireOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FReceiveOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FEntrySettleOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FStockQty\": " + purorderItem.getQuantity()
                                            + ",\"FStockBaseQty\": " + purorderItem.getQuantity();
                                    //3 配置关联上游采购申请单id
                                    request += ", \"FPOOrderEntry_Link\": [{"
                                            + " \"FPOOrderEntry_Link_FRuleId\": \"PUR_Requisition-PUR_PurchaseOrder\""
                                            + ", \"FPOOrderEntry_Link_FSTableName\": \"T_PUR_ReqEntry\""
                                            + ", \"FPOOrderEntry_Link_FSBillId\": " + srcBillId
                                            + ", \"FPOOrderEntry_Link_FSId\": \"" + purorderItem.getErpprbillitemcode() + "\""
                                            + ", \"FPOOrderEntry_Link_FBaseUnitQTY\": " + purorderItem.getQuantity()
                                            + ", \"FPOOrderEntry_Link_FBaseUnitQTYOLD\": " + purorderItem.getQuantity() + "}]";
                                    request += "},";
                                    //count分录序号只能为0,如果顺序号的话，无法保存。
                                    //count += 1;
                                }
                                request = request.substring(0, request.length() - 1);
                                request += "]}}";
                            }
                            str_result += "2、即将推送星空ERP的数据组装完成； \n";
                            //
                            //response = client.executeBillQuery(request);
                            //String response = client.save("PUR_PurchaseOrder",request);
                            String response = client.draft("PUR_PurchaseOrder",request);
                            System.out.println(response);
                            //解析response 如果发现IsSuccess的值为false，则需要将该订单的对应的明细中间表数据和本地存储数据
                            //的orderstate参照状态 设置成未生成状态。
                            boolean isSuccess = isSuccess(response);
                            if(isSuccess){
                                str_result += "3、生成星空ERP的采购订单完成； \n";
                                for(PurorderItem purorderItem : list_Com_PurorderItem){
                                    //purorderItem.setOrderstate("1");
                                    //purchaseOrdersService.updateItemInspurWC(purorderItem);
                                    //purchaseOrdersService.updateItemLocal(purorderItem);
                                }
                                str_result += "4、反写本地中间明细表、浪潮中间订单明细表的orderstate参照状态完成； \n";
                            } else {
                                str_result += "3、生成星空ERP的采购订单失败； \n";
                            }

                        } // end for map_PurorderItem
                    } // end if map_PurorderItem
                    purchaseOrders.setOrderstate("1");
                    purchaseOrdersService.updateOrderLocal(purchaseOrders);
                    purchaseOrdersService.updateOrderInspurWC(purchaseOrders);
                    str_result += "5、反写本地中间明细表、浪潮中间采购订单表的orderstate参照状态完成； \n";
                } // end for list_PurchaseOrders
            } // end if list_PurchaseOrders
            result.setType(1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setType(-1);
        } finally {
            result.setResult(str_result);
            result.setRequest("采购订单传递");
            Long endTime= System.currentTimeMillis() - startTime;
            sysLog.setId(sid.nextShort());
            sysLog.setType(result.getType());
            sysLog.setParams(str_result);
            sysLog.setMessage(result.getResult());
            sysLog.setRequest(result.getRequest());
            sysLog.setTime(new Date());
            sysLog.setRuntime(endTime.toString()+"ms");
            //获取用户ip地址
            sysLog.setIp("");

            //调用service保存SysLog实体类到数据库
            //logService.save(sysLog);
            return result;
        }
    }

    /**
     * description 2 利民抽取采购订单信息
     * @param
     * @return
     */
    @MyLog(value = "利民采购订单传递")
    @RequestMapping("/purchaseOrdersLM")
    public Result extractedPurchaseOrder2LMERP() {
        Visitlog sysLog = new Visitlog();
        Result result = new Result();
        String str_result = "";
        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        //调用接口
        //List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "";
        try {
            //查询 当日订单 参照状态为 0   由ERP系统对单据回写状态进行标记，共：0未生成；1已生产，两种状态
            PurchaseOrders purchaseOrders4Q = new PurchaseOrders();
            //purchaseOrders4Q.setCreatedate(noFormat.format(new Date()));
            purchaseOrders4Q.setOrderstate("0");
            List<PurchaseOrders> list_PurchaseOrders = purchaseOrdersService.findInspurLM(purchaseOrders4Q);
            if (null != list_PurchaseOrders && list_PurchaseOrders.size() > 0) {
                for (PurchaseOrders purchaseOrders : list_PurchaseOrders) {
                    //订单单头本地持久化
                    purchaseOrdersService.saveLocal(purchaseOrders);

                    //先做订单分录本地持久化
                    List<PurorderItem> list_PurorderItem = purchaseOrdersService.findItemByOrderIdInspurLM(purchaseOrders);
                    for (PurorderItem purorderItem : list_PurorderItem) {
                        purchaseOrdersService.saveItemLocal(purorderItem);
                    }
                    str_result += "0、本地中间表数据存储已完成； \n";

                    //再向远程终端推送数据,
                    //按照组织进行隔离获取采购订单明细
                    Map<String, List<PurorderItem>> map_PurorderItem = purchaseOrdersService.findSplitOrgItemByOrderIdInspurLM(purchaseOrders);
                    if (null != map_PurorderItem && map_PurorderItem.size() > 0) {

                        for (Map.Entry<String, List<PurorderItem>> entry_PurorderItem : map_PurorderItem.entrySet()) {
                            String company = entry_PurorderItem.getKey();
                            String actualComNumber = "", createDate = "";
                            List<PurorderItem> list_Com_PurorderItem = entry_PurorderItem.getValue();
                            createDate = filledWith10Bit4Date(purchaseOrders.getCreatedate());
                            //1、此时要特别注意：要根据分录里的结算组织进行分类了，不同的结算组织要推送到不同的星空终端
                            if ("2221".equals(company)) {
                                actualComNumber = "2221";
                                client = K3CloudUtil.getLMK3CloudApiClient();
                                //根据供应商社会统一信用代码获取组织编码FNumber
                                //废弃
                                String supplierNumber = getSupplierNumberBySocialCode(client, purchaseOrders.getLswldwCertificate());
                                //1 组装订单单据表头信息
                                request += "{ \"Model\": { \"FBillNo\": \"" + purchaseOrders.getPurordercode() + "\""
                                        + ",\"FDate\": \"" + createDate + "\""
                                        + ",\"FSupplierId\": {\"FNumber\": \"" + supplierNumber + "\"}"
                                        + ",\"FPurchaseOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                        //+ ",\"FPurchaseDeptId\": {\"FNumber\": \"1115\"}"
                                        //+ ",\"FPurchaserId\": {\"FNumber\": \"110156\"}"
                                        + ",\"FBillTypeID\": {\"FNumber\": \"CGDD01_SYS\"}"
                                        //+ ",\"FExchangeRate\": \"1\""
                                        + ",\"FSettleId\": {\"FNumber\": \"" + supplierNumber + "\"}";
                                //特别指明星空终端

                            }

                            str_result += "1、连接组织：" + company+ " 的星空系统成功； \n";
                            //List<PurorderItem> list_K3Cloud_PurorderItem = entry_PurorderItem.getValue();
                            if (null != list_Com_PurorderItem && list_Com_PurorderItem.size() > 0) {
                                //2 拼接分录保存字符串
                                request += ",\"FPOOrderEntry\": [";
                                int count = 0;
                                for (PurorderItem purorderItem : list_Com_PurorderItem) {
                                    //需将ERP系统中物料编号前加上4位组织编号
                                    String materialNumber = getMaterialNumber4BitCode(purorderItem.getMaterialcode());
                                    String srcBillId = getReqisitionBillId(client, purorderItem.getErpprbillcode());
                                    //配置分录
                                    request += "{ \"FEntryID\": " + count
                                            + ",\"FMaterialId\": {\"FNumber\": \"" + materialNumber + "\"}"
                                            + ",\"FUnitId\": {\"FName\": \"" + purorderItem.getPurunitid() + "\"}"
                                            + ",\"FStockUnitID\": {\"FName\": \"" + purorderItem.getPurunitid() + "\"}"
                                            + ",\"FQty\": " + purorderItem.getQuantity()
                                            + ",\"FPriceUnitQty\": " + purorderItem.getQuantity()
                                            + ",\"FPriceBaseQty\": " + purorderItem.getQuantity()
                                            + ",\"FDeliveryDate\": \"" + createDate + "\""
                                            + ",\"FTaxPrice\": " + purorderItem.getTaxinprice()
                                            + ",\"FRequireOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FReceiveOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FEntrySettleOrgId\": {\"FNumber\": \"" + actualComNumber + "\"}"
                                            + ",\"FStockQty\": " + purorderItem.getQuantity()
                                            + ",\"FStockBaseQty\": " + purorderItem.getQuantity();
                                    request += "},";
                                    //count分录序号只能为0,如果顺序号的话，无法保存。
                                    //count += 1;
                                }
                                request = request.substring(0, request.length() - 1);
                                request += "]}}";
                            }
                            str_result += "2、即将推送星空ERP的数据组装完成； \n";
                            //
                            //response = client.executeBillQuery(request);
                            //String response = client.save("PUR_PurchaseOrder",request);
                            String response = client.draft("PUR_PurchaseOrder",request);
                            System.out.println(response);
                            //解析response 如果发现IsSuccess的值为false，则需要将该订单的对应的明细中间表数据和本地存储数据
                            //的orderstate参照状态 设置成未生成状态。
                            boolean isSuccess = isSuccess(response);
                            if(isSuccess){
                                str_result += "3、生成星空ERP的采购订单完成； \n";
                                for(PurorderItem purorderItem : list_Com_PurorderItem){
                                    //purorderItem.setOrderstate("1");
                                    //purchaseOrdersService.updateItemInspurWC(purorderItem);
                                    //purchaseOrdersService.updateItemLocal(purorderItem);
                                }
                                str_result += "4、反写本地中间明细表、浪潮中间订单明细表的orderstate参照状态完成； \n";
                            } else {
                                str_result += "3、生成星空ERP的采购订单失败； \n";
                            }

                        } // end for map_PurorderItem
                    } // end if map_PurorderItem
                    purchaseOrders.setOrderstate("1");
                    purchaseOrdersService.updateOrderLocal(purchaseOrders);
                    purchaseOrdersService.updateOrderInspurLM(purchaseOrders);
                    str_result += "5、反写本地中间明细表、浪潮中间采购订单表的orderstate参照状态完成； \n";
                } // end for list_PurchaseOrders
            } // end if list_PurchaseOrders
            result.setType(1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setType(-1);
        } finally {
            result.setResult(str_result);
            result.setRequest("采购订单传递");
            Long endTime= System.currentTimeMillis() - startTime;
            sysLog.setId(sid.nextShort());
            sysLog.setType(result.getType());
            sysLog.setParams(str_result);
            sysLog.setMessage(result.getResult());
            sysLog.setRequest(result.getRequest());
            sysLog.setTime(new Date());
            sysLog.setRuntime(endTime.toString()+"ms");
            //获取用户ip地址
            sysLog.setIp("");

            //调用service保存SysLog实体类到数据库
            //logService.save(sysLog);
            return result;
        }
    }

    /**
     * description 根据采购申请单的单据编号，查询星空中的单据FID
     * @param client
     * @param erpprbillcode
     * @return
     */
    private String getReqisitionBillId(K3CloudApiClient client, String erpprbillcode) throws Exception {
        String reqisitionBillId = "";
        if(null != erpprbillcode && !"".equals(erpprbillcode) && null != client) {
            String request = "{ \"FormId\": \"PUR_Requisition\", \"FieldKeys\": \"fid,fbillno\", "
                    + "\"FilterString\": \"fbillno = '" + erpprbillcode + "'\","
                    + "\"OrderString\": \"\", \"TopRowCount\": 0, \"StartRow\": 0, \"Limit\": 0 }";
            //调用接口
            List<List<Object>> response = new ArrayList<List<Object>>();
            response = client.executeBillQuery(request);
            System.out.println(response);
            List<PurchasePlans> list_PurchasePlans = new ArrayList<PurchasePlans>();
            if (response.size() > 0) {
                List<Object> tempList = response.get(0);
                if (null != tempList.get(0)) {
                    //返回fid
                    return tempList.get(0).toString();
                }
            }
        }
        return reqisitionBillId;
    }

    /**
     * description 日期字符串处理，将 '20200812' 转为 '2020-08-12'， 因为星空只能接收后者。
     * @param createdate
     * @return
     */
    private String filledWith10Bit4Date(String createdate) {

        if(null != createdate && !"".equals(createdate)){
            return createdate.substring(0, 4) + "-" + createdate.substring(4, 6) + "-" + createdate.substring(6, 8);
        }
        return createdate;
    }

    /**
     * description 获取物料编码，因为浪潮中间表的信息为 “2279105030039” 前四位为组织编码，后面的才是真正的物料编码
     * @param materialNumber 浪潮物料编码粗信息
     * @return
     */
    private String getMaterialNumber4BitCode(String materialNumber) {
        if(null != materialNumber && !"".equals(materialNumber)){
            return materialNumber.substring(4, materialNumber.length());
        }
        return "";
    }

    /**
     * description 通过社会统一信用代码获取星空中的供应商编码
     * @param lswldwCertificate 社会统一信用代码
     * @return
     */
    private String getSupplierNumberBySocialCode(K3CloudApiClient client, String lswldwCertificate) {
        //调用接口
        List<List<Object>> response = new ArrayList<List<Object>>();
        //
        String request = "{ \"FormId\": \"BD_Supplier\",\"FieldKeys\": \"FNumber,FName,FSOCIALCRECODE\","
                + "\"FilterString\": \"FSOCIALCRECODE='" + lswldwCertificate + "'\","
                + "\"OrderString\": \"\",\"TopRowCount\": 0,\"StartRow\": 0,\"Limit\": 0";
        try {
            response = client.executeBillQuery(request);
            if(null != response && response.size() > 0) {
                List<Object> tempList = response.get(0);
                if (null != tempList.get(0) && !"".equals(tempList.get(0).toString())) {
                    return tempList.get(0).toString(); //物料编号
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * description 只有在通过WebAPI采购订单生成时调用了一下，其实应该每次WebAPI访问时都调用该方法才严谨，在此表达歉意！
     * @param response
     * @return
     */
    private boolean isSuccess(String response) {
        System.out.println(response);
        //JSONArray jsonArray =  JSONObject.parseArray(response);
        JSONObject json =  JSONObject.parseObject(response);
        JSONObject result = json.getJSONObject("Result");
        JSONObject responseStatus = result.getJSONObject("ResponseStatus");
        String isSuccess = responseStatus.getString("IsSuccess");
        if(null != isSuccess && "true".equals(isSuccess)){
            return true;
        } else{
            return false;
        }
        //return true;
    }
}
