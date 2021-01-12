package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Unit;
import com.kingdee.service.UnitService;
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
 * @description 计量单位
 * @date 2020/11/12 18:02
 */
@RequestMapping
@RestController
public class UnitController {
    @Autowired
    private UnitService unitService;

    @RequestMapping("/synUnit")
    public void synUnit() {
        System.out.println(new Date() + " 计量单位后台事务开始");
        synUnitFromCloud();
        synUnitFromWise();
    }

    private void synUnitFromCloud() {
        String request = "{\"FormId\":\"BD_UNIT\",\"FieldKeys\":\"FUNITID,FNumber,FName,FCreateDate,FModifyDate\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
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
            Unit unit = new Unit();
            try {
                List<Object> oneInfo = response.get(i);
                unit.setVfid("Cloud_" + oneInfo.get(0).toString());
                unit.setVgsdm(InspurConstant.LMGSDM);
                unit.setVdm(oneInfo.get(1).toString());
                unit.setVmc(oneInfo.get(2).toString());
                if (oneInfo.get(3) == null) {
                    unit.setDcjrq(new Date());
                } else {
                    unit.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                }
                if (oneInfo.get(4) == null) {
                    unit.setDxgrq(new Date());
                } else {
                    unit.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                }
                unitService.saveInspur(unit);
                unitService.saveLocal(unit);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }
    }

    private void synUnitFromWise() {

        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query(" SELECT UUID,FNumber,FName FROM t_MeasureUnit");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Unit unit = new Unit();
                unit.setVgsdm(InspurConstant.GDZYGSDM);
                unit.setVfid("Wise_" + result.get(i).get("UUID"));
                unit.setVdm(result.get(i).get("FNumber").toString());
                unit.setVmc(result.get(i).get("FName").toString());
                unit.setDcjrq(new Date());
                unit.setDxgrq(new Date());

                unitService.saveInspur(unit);
                unitService.saveLocal(unit);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
