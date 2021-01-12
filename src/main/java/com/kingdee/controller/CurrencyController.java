package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Currency;
import com.kingdee.service.CurrencyService;
import com.kingdee.token.TokenKit;
import com.kingdee.util.K3CloudUtil;
import com.kingdee.util.URLTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Jambin
 * @description 同步币别
 * @date 2020/11/12 14:44
 */
@RestController
@RequestMapping
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    /**
     * @param
     * @return void
     * @description: 同步币别
     * @date: 2020/11/12 14:45
     * @author: Jambin
     */
    @RequestMapping("/synCurrency")
    public void synCurrency() {
        System.out.println(new Date() + " 币别同步后台事务开始");
        synCurrencyFromCloud();
        synCurrencyFromWise();
    }

    private void synCurrencyFromCloud() {
        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BD_Currency\",\"FieldKeys\":\"FCURRENCYID,fnumber,Fname,FCreateDate,FModifyDate\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Currency currency = new Currency();
            try {
                List<Object> oneInfo = response.get(i);
                currency.setVfid("Cloud_" + oneInfo.get(0).toString());
                currency.setVgsdm(InspurConstant.LMGSDM);
                currency.setVdm(oneInfo.get(1).toString());
                currency.setVmc(oneInfo.get(2).toString());
                if (oneInfo.get(3) == null) {
                    currency.setDcjrq(null);
                } else {
                    currency.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                }
                if (oneInfo.get(4) == null) {
                    currency.setDxgrq(null);
                } else {
                    currency.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                }
                currencyService.saveInspur(currency);
                currencyService.saveLocal(currency);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise
     * @date: 2020/11/24 15:45
     * @author: Jambin
     */
    private void synCurrencyFromWise() {
        //wise接口地址map
        Iterator<Map.Entry<String, String>> wiseMap = InspurConstant.WISEADDRESSMAP.entrySet().iterator();
        while (wiseMap.hasNext()) {
            Map.Entry<String, String> entry = wiseMap.next();

            String url = URLTemplate.getList(entry.getValue(), InspurConstant.CURRENCY, TokenKit.getAccessToken(entry.getValue(), entry.getKey()));
            String request = "{\n" +
                    "  \"Data\": {\n" +
                    "    \"Top\": \"0\",\n" +
                    "    \"PageSize\": \"0\",\n" +
                    "    \"PageIndex\": \"0\",\n" +
                    "    \"Filter\": \" \",\n" +
                    "    \"OrderBy\": \"FNumber asc\",\n" +
                    "    \"Fields\": \"FNumber,FName\"\n" +
                    "  }\n" +
                    "}";
            //调用接口获取数据
            String response = HttpUtil.post(url, request);
            //解析json数据
            JSONObject responseObj = JSONUtil.parseObj(response);
            JSONObject dataObj = responseObj.getJSONObject("Data");
            JSONArray dataArr = dataObj.getJSONArray("Data");

            for (int i = 0; i < dataArr.size(); i++) {
                try {
                    Currency currency = new Currency();
                    JSONObject temp = dataArr.getJSONObject(i);
                    currency.setVgsdm(InspurConstant.GDZYGSDM);
                    currency.setVfid("Wise_" + temp.get("FCurrencyID").toString());
                    currency.setVdm(temp.get("FCurrencyID").toString());
                    currency.setVmc(temp.get("FName").toString());
                    currency.setDcjrq(new Date());
                    currency.setDxgrq(new Date());

                    currencyService.saveInspur(currency);
                    currencyService.saveLocal(currency);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

        }
    }
}
