package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Person;
import com.kingdee.service.PersonService;
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
 * @description 业务员
 * @date 2020/11/12 17:46
 */
@RequestMapping
@RestController
public class PeronController {
    @Autowired
    private PersonService personService;

    @RequestMapping("/synPeron")
    public void synPeron() {
        System.out.println(new Date() + " 业务员后台事务开始");
        synPeronFromCloud();
        synPeronFromWise();
    }

    private void synPeronFromCloud() {
        String request = "{\"FormId\":\"SEC_User\",\"FieldKeys\":\"FUserID,FUserAccount,FName,FCreateDate\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
//        String request = "{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"FID,FNumber,FName,FCreateDate,FModifyDate\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        List<List<Object>> response = new ArrayList<List<Object>>();

        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Person person = new Person();
            try {
                List<Object> oneInfo = response.get(i);
                person.setVgsdm(InspurConstant.LMGSDM);
                person.setVfid("Cloud_" + oneInfo.get(0).toString());
                person.setVdm(oneInfo.get(1).toString());
                person.setVmc(oneInfo.get(2).toString());
                if (oneInfo.get(3) == null) {
                    person.setDcjrq(null);
                } else {
                    person.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                }
                person.setDxgrq(new Date());

                personService.saveInspur(person);
                personService.saveLocal(person);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //wise
    private void synPeronFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT FNumber,FName FROM t_Base_Emp");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Person person = new Person();
                person.setVgsdm(InspurConstant.GDZYGSDM);
                person.setVfid("Wise_" + result.get(i).get("FNumber"));
                person.setVdm(result.get(i).get("FNumber").toString());
                person.setVmc(result.get(i).get("FName").toString());
                person.setDcjrq(new Date());
                person.setDxgrq(new Date());

                personService.saveInspur(person);
                personService.saveLocal(person);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
