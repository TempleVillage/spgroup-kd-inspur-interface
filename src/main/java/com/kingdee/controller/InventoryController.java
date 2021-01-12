package com.kingdee.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.config.MyLog;
import com.kingdee.constant.InspurConstant;
import com.kingdee.constant.InspurWCConstant;
import com.kingdee.model.po.Currency;
import com.kingdee.model.po.InvBal;
import com.kingdee.model.po.Inventory;
import com.kingdee.model.vo.Result;
import com.kingdee.service.InventoryService;
import com.kingdee.token.TokenKit;
import com.kingdee.util.CommonBizUtil;
import com.kingdee.util.K3CloudUtil;
import com.kingdee.util.URLTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description 即时库存余额
 * @date 2020/11/12 17:30
 */
@RequestMapping
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    SimpleDateFormat noFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String currentPeriod = CommonBizUtil.getCurrentPeriod();

    @MyLog(value = "库存余额传递测试")
    @RequestMapping("/synInventory")
    public Result synInventory() {
        System.out.println(new Date() + " 即时库存余额后台事务开始");
        Result result = new Result();
        result.setType(1);
        result.setRequest("库存余额测试请求");
        result.setResult("");
        //Cloud
        result.setResult(result.getResult() + synInventoryFromCloud());
        //Wise
        result.setResult(result.getResult() + synInventoryFromWise());
        return result;
    }

    private String synInventoryFromCloud() {
        String str_result = "";
        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        List<List<Object>> response = new ArrayList<List<Object>>();
        //String request = "{\"FormId\":\"STK_Inventory\",\"FieldKeys\":\"FMaterialId.fnumber,FLot,FBaseQty,FBaseUnitId.Fnumber,FLotProduceDate,FUpdateTime\",\"FilterString\":\"FStockOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String request = "{\"FormId\":\"STK_Inventory\",\"FieldKeys\":\"FMaterialId.fnumber,FLot,FBaseQty,FBaseUnitId.Fnumber,FLotProduceDate,FUpdateTime,FStockOrgId.fnumber\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
            //先擦除当前期间远程浪潮或本地备份数据库中的数据，然后再持久化新数据
            if(response.size() > 0){
                Inventory inventory = new Inventory();
                inventory.setVqj(currentPeriod);
                inventoryService.eraseByPeriod_Local(inventory);
                inventoryService.eraseByPeriod_Inspur(inventory);
            }
            //然后再持久化
            for (int i = 0; i < response.size(); i++) {
                Inventory inventory = new Inventory();
                List<Object> oneInfo = response.get(i);
                inventory.setVfid("Cloud_" + format.format(new Date()) + oneInfo.get(0).toString());
                inventory.setVgsdm(InspurConstant.LMGSDM);
                inventory.setVgsdm(oneInfo.get(6).toString());
                inventory.setVqj(currentPeriod);
                inventory.setVywrq(noFormat.format(new Date()));
                if (null != oneInfo.get(0)) {
                    inventory.setVwlbm(oneInfo.get(0).toString());
                } else {
                    inventory.setVwlbm("");
                }
                if (null != oneInfo.get(1)) {
                    inventory.setVpch(oneInfo.get(1).toString());
                } else {
                    inventory.setVpch("");
                }
                inventory.setNje(BigDecimal.ZERO);
                if (null != oneInfo.get(2)) {
                    inventory.setNsl(new BigDecimal(oneInfo.get(2).toString()));
                } else {
                    inventory.setNsl(BigDecimal.ZERO);
                }
                if (null != oneInfo.get(3)) {
                    inventory.setVjldw(oneInfo.get(3).toString());
                } else {
                    inventory.setVjldw("");
                }
                if (null != oneInfo.get(4)) {
                    inventory.setCrkrq(noFormat.format(format.parse(oneInfo.get(4).toString())));
                    inventory.setDcjrq(format.parse(oneInfo.get(4).toString()));
                } else {
                    inventory.setCrkrq("");
                    inventory.setDcjrq(new Date());
                }
                inventory.setDxgrq(format.parse(oneInfo.get(5).toString()));
                inventoryService.saveInspur(inventory);
                inventoryService.saveLocal(inventory);
            }
            str_result += "6、Cloud库存余额数据操作完成！\n 任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "6、Cloud库存余额数据操作未能完成，请核查详细日志！\n 任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        }  finally {
            return str_result;
        }
    }

    public String synInventoryFromWise() {
        String str_result = "";
        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        //wise接口地址map
        Iterator<Map.Entry<String, String>> wiseMap = InspurWCConstant.WISEADDRESSMAP.entrySet().iterator();
        try {
            while (wiseMap.hasNext()) {
                Map.Entry<String, String> entry = wiseMap.next();

                String url = URLTemplate.getList(entry.getValue(), "SQLReport101", TokenKit.getAccessToken(entry.getValue(), entry.getKey()));
                //String url = URLTemplate.getList(entry.getValue(), "Bill1009104", TokenKit.getAccessToken(entry.getValue(), entry.getKey()));
                String request = "{\n" +
                        "  \"Data\": {\n" +
                        "    \"PageSize\": \"10000\",\n" +
                        "    \"PageIndex\": \"1\"\n" +
                        "  },\n" +
                        "  \"*FItemShortNo*\": \"\"\n" +
                        "}";
                String request1 = "{\"Data\": {\"Top\": \"100\","
                        + "\"PageSize\": \"10\",\"PageIndex\": \"1\","
                        //+ "\"Filter\": \"[FBillNo] like '%001%' \","
                        //+ "\"OrderBy\": \"[FBillNo] asc\","
                        //+ "\"SelectPage\": \"2\","
                        + "\"Fields\": \"FBillNo,FItemID,FItemName,FStockQty,FAmtSum\"}}";
                    //调用接口获取数据
                String response = HttpUtil.post(url, request);
                //解析json数据
                JSONObject responseObj = JSONUtil.parseObj(response);
                JSONObject dataObj = responseObj.getJSONObject("Data");
                JSONArray dataArr = dataObj.getJSONArray("DATA");

                for (int i = 0; i < dataArr.size(); i++) {
                    //0 物料代码 1 规格型号 2 物料名称 3 基本计量单位 4 计量单位编码 5 批次号 6 入库日期 7 基本单位数量
                    Inventory inventory = new Inventory();
                    JSONObject temp = dataArr.getJSONObject(i);
                    temp.get("常用单位数量");
                    inventory.setVfid("Wise_"+format.format(new Date()) + temp.get("物料代码").toString());
                    inventory.setVgsdm(InspurWCConstant.GSDM);
                    inventory.setVqj(currentPeriod);
                    inventory.setVywrq(noFormat.format(new Date()));
                    if (null != temp.get("物料代码")) {
                        inventory.setVwlbm(temp.get("物料代码").toString());
                    } else {
                        inventory.setVwlbm("");
                    }
                    if (null != temp.get("批次号")) {
                        inventory.setVpch(temp.get("批次号").toString());
                    } else {
                        inventory.setVpch("");
                    }
                    inventory.setNje(BigDecimal.ZERO);
                    if (null != temp.get("基本单位数量")) {
                        inventory.setNsl(new BigDecimal(temp.get("基本单位数量").toString()));
                    } else {
                        inventory.setNsl(BigDecimal.ZERO);
                    }
                    if (null != temp.get("基本计量单位")) {
                        inventory.setVjldw(temp.get("基本计量单位").toString());
                    } else {
                        inventory.setVjldw("");
                    }
                    if (null != temp.get("入库日期") && !"".equals(temp.get("入库日期"))) {
                        inventory.setCrkrq(noFormat.format(format.parse(temp.get("入库日期").toString())));
                        inventory.setDcjrq(format.parse(temp.get("入库日期").toString()));
                        inventory.setDxgrq(format.parse(temp.get("入库日期").toString()));
                    } else {
                        inventory.setCrkrq("");
                        inventory.setDcjrq(new Date());
                        inventory.setDxgrq(new Date());
                    }
                    //inventoryService.saveInspur(inventory);
                    inventoryService.saveLocal(inventory);
                }
            }
            str_result += "6、wise库存余额数据操作完成！\n任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } catch (Exception e) {
            e.printStackTrace();
            str_result += "6、wise库存余额数据操作未能完成，请核查详细日志！\n 任务持续时长："
                    +  (System.currentTimeMillis() - startTime) + "MS";
        } finally {
            return str_result;
        }
    }
}
