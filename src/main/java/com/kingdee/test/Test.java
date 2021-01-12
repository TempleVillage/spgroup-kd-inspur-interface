package com.kingdee.test;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.http.HttpRequest;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.token.TokenKit;
import com.kingdee.util.K3CloudUtil;
import com.kingdee.util.URLTemplate;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @org.junit.Test
    public void test() {
        String url = URLTemplate.getList(InspurConstant.GDZY_ADDRESS, "Sales_Delivery", TokenKit.getAccessToken(InspurConstant.GDZY_ADDRESS, InspurConstant.GDZY_AUTHORITYCODE));
        String url1 = URLTemplate.getTemplate(InspurConstant.GDZY_ADDRESS, "SubMessage", TokenKit.getAccessToken(InspurConstant.GDZY_ADDRESS, InspurConstant.GDZY_AUTHORITYCODE));
        String url2 = URLTemplate.getDetail(InspurConstant.GDZY_ADDRESS, InspurConstant.SALEORDER, TokenKit.getAccessToken(InspurConstant.GDZY_ADDRESS, InspurConstant.GDZY_AUTHORITYCODE));

//         System.out.println(url);
        String request = "{\n" +
                "  \"Data\": {\n" +
                "    \"Top\": \"100\",\n" +
                "    \"PageSize\": \"10\",\n" +
                "    \"PageIndex\": \"1\",\n" +
                "    \"Filter\": \"\",\n" +
                "    \"OrderBy\": \"[FBillNo] asc\",\n" +
                "    \"SelectPage\": \"2\",\n" +
                "    \"Fields\": \"FBillNo\"\n" +
                "  }\n" +
                "}";

//        String request2 = "{\n" +
//                "  \"Data\": {\n" +
//                "    \"FBillNo\": \"01.XD12-05-0415\"\n" +
//                "  }\n" +
//                "}";
//        String response2 = HttpUtil.post(url2, request2);
//        System.out.println(response2);
//        String response = HttpUtil.post(url, request);
        String response = HttpRequest.post(url).body(request).timeout(600000).execute().body();
        System.out.println(response);
        //解析json数据
//        JSONObject responseObj = JSONUtil.parseObj(response);
//        JSONObject dataObj = responseObj.getJSONObject("Data");
//        JSONArray dataArr = dataObj.getJSONArray("Data");
//        for (int i = 0; i < dataArr.size(); i++) {
//            JSONObject temp = dataArr.getJSONObject(i);
//          //  System.out.println(temp.get("FItemID"));
//        }
//        System.out.println(JSONUtil.isJsonObj(response));
//        JSONObject responseObj = JSONUtil.parseObj(response);
//        JSONObject dataObj = responseObj.getJSONObject("Data");
//        JSONArray dataArr = dataObj.getJSONArray("Data");
        //System.out.println(responseObj + "\n" + dataObj + "\n" + dataArr);
//        for (int i = 0; i < dataArr.size(); i++) {
//            Supplier supplier = new Supplier();
//            JSONObject temp = dataArr.getJSONObject(i);
//            supplier.setVfid("Wise_" + temp.get("FNumber"));
//            supplier.setVgsdm(InspurConstant.GSDM);
//            supplier.setVgysbm(temp.get("FNumber").toString());
//            supplier.setVgysmc(temp.get("FName").toString());
//            supplier.setVgysfldm(temp.get("FSupplyType").toString());
//            supplier.setVszss(temp.get("FProvinceID").toString());
//            supplier.setVszgj(temp.get("FCountry").toString());
//            supplier.setDcjrq(new Date());
//            supplier.setDxgrq(new Date());
//          //  System.out.println(supplier.toString());
//        }

        //System.out.println(HttpUtil.get(url1));
    }

    @org.junit.Test
    public void test1() {
        List<List<Object>> response = new ArrayList<List<Object>>();
//        String request = "{\"FormId\":\"BD_Supplier\",\"FieldKeys\":\"FSupplierId, FNumber,FName,FCountry.fnumber,FProvincial.fnumber,FSupplierClassify,FCreateDate,FModifyDate\",\"FilterString\":\"FUseOrgId.fnumber='2221'\",\"OrderString\":\"FNumber asc\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String request = "{\"FormId\":\"SAL_SaleOrder\",\"FieldKeys\":\"FSaleOrderEntry_FEntryID,FSaleOrderEntry_FSEQ,FBillNo,FMaterialId.fnumber,FQty,FBaseUnitId.fnumber,FAmount_LC,FAllAmount_LC,FCreateDate,FModifyDate,\",\"FilterString\":\" FSaleOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";

        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testDB() throws SQLException {
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = Db.use(dataSource).query("SELECT UUID, FInterID,FNAME FROM t_SubMessage  WHERE FTYPEID='501' ");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }

    }
}
