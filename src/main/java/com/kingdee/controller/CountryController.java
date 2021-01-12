package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Country;
import com.kingdee.service.CountryService;
import com.kingdee.util.K3CloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jambin
 * @description 同步国家
 * @date 2020/11/12 14:22
 */
@RequestMapping
@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    /**
     * @return void
     * @description: 同步国家
     * @date: 2020/11/12 14:26
     * @author: Jambin
     */
    @RequestMapping("/synCountry")
    public void synCountry() {
        System.out.println(new Date() + " 国家同步后台事务开始");
        synCountryFromCloud();
        synCountryFromWise();
    }

    /**
     * @return void
     * @description: 星空同步国家
     * @date: 2020/11/20 10:04
     * @author: Jambin
     */
    private void synCountryFromCloud() {
        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BOS_ASSISTANTDATA_DETAIL\",\"FieldKeys\":\"FEntryID,FNumber,FDataValue,FCreateDate,FModifyDate\",\"FilterString\":\"FId='8a6e30f0-2c26-4639-aff5-76749daa355e'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Country country = new Country();
            try {
                List<Object> oneInfo = response.get(i);
                country.setVfid("Cloud_" + oneInfo.get(0).toString());
                country.setVgsdm(InspurConstant.LMGSDM);
                country.setVdm(oneInfo.get(1).toString());
                country.setVmc(oneInfo.get(2).toString());
                country.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                country.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                countryService.saveLocal(country);
                countryService.saveInspur(country);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise同步国家
     * @date: 2020/11/20 10:04
     * @author: Jambin
     */
    private void synCountryFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为26 区域的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("  SELECT UUID, FInterID,FNAME FROM t_SubMessage   WHERE FTYPEID='26' ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Country country = new Country();
                country.setVgsdm(InspurConstant.GDZYGSDM);
                country.setVfid("Wise_" + result.get(i).get("UUID"));
                country.setVdm(result.get(i).get("FInterID").toString());
                country.setVmc(result.get(i).get("FNAME").toString());
                country.setDcjrq(new Date());
                country.setDxgrq(new Date());

                countryService.saveLocal(country);
                countryService.saveInspur(country);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
