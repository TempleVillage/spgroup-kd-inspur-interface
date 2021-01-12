package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Organization;
import com.kingdee.service.OrganizationService;
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
 * @description 组织同步
 * @date 2020/11/12 17:43
 */
@RequestMapping
@RestController
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/synOrganization")
    public void synOrganization() {
        System.out.println(new Date() + " 组织机构后台事务开始");
        synOrganizationCloud();
        synOrganizationWise();
    }

    /**
     * @param
     * @return void
     * @description: cloud组织同步
     * @date: 2020/11/18 9:34
     * @author: Jambin
     */
    private void synOrganizationCloud() {

//        String request = "{\"FormId\":\"ORG_Organizations\",\"FieldKeys\":\"FOrgID,FNumber,FName,FCreateDate,FModifyDate\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        String request = "{\"FormId\":\"BD_Department\",\"FieldKeys\":\"FDEPTID,FNUMBER,FNAME,FCreateDate,FModifyDate\",\"FilterString\":\"FUseOrgId.fnumber='2221'and FForbidStatus='A'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        List<List<Object>> response = new ArrayList<List<Object>>();

        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            response = client.executeBillQuery(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Organization org = new Organization();
            try {
                List<Object> oneInfo = response.get(i);
                org.setVgsdm(InspurConstant.LMGSDM);
                org.setVfid("Cloud_" + oneInfo.get(0).toString());
                org.setVdm(oneInfo.get(1).toString());
                org.setVmc(oneInfo.get(2).toString());
                org.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                org.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                organizationService.saveInspur(org);
                organizationService.saveLocal(org);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @param
     * @return void
     * @description: wise组织同步
     * @date: 2020/11/18 9:34
     * @author: Jambin
     */
    private void synOrganizationWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT FItemID,FNumber,FName FROM t_Department");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Organization org = new Organization();
                org.setVgsdm(InspurConstant.GDZYGSDM);
                org.setVfid("Wise_GDZY" + result.get(i).get("FItemID"));
                org.setVdm(result.get(i).get("FNumber").toString());
                org.setVmc(result.get(i).get("FName").toString());
                org.setDcjrq(new Date());
                org.setDxgrq(new Date());

                organizationService.saveInspur(org);
                organizationService.saveLocal(org);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}
