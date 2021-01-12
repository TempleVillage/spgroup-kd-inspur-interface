package com.kingdee.controller;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.MaterialType;
import com.kingdee.service.MaterialTypeService;
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
 * @description 物料分组(产品分类)同步
 * @date 2020/11/12 17:41
 */
@RequestMapping
@RestController
public class MaterialTypeController {
    @Autowired
    private MaterialTypeService materialTypeService;

    @RequestMapping("/synMaterialType")
    public void synMaterialType() {
        System.out.println(new Date() + " 物料分组后台事务开始");
        synMaterialTypeFromCloud();
        synMaterialTypeFromWise();
    }

    private void synMaterialTypeFromCloud() {
        String request = "{\"FormId\":\"SAL_MATERIALGROUP\",\"FieldKeys\":\"FID,Fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
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
            MaterialType materialType = new MaterialType();
            try {
                List<Object> oneInfo = response.get(i);
                materialType.setVfid("Cloud_" + oneInfo.get(0).toString());
                materialType.setVgsdm(InspurConstant.LMGSDM);
                materialType.setVdm(oneInfo.get(1).toString());
                materialType.setVmc(oneInfo.get(2).toString());
                materialType.setDcjrq(new Date());
                materialType.setDxgrq(new Date());
                materialTypeService.saveInspur(materialType);
                materialTypeService.saveLocal(materialType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //wise
    private void synMaterialTypeFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为504 物料分类的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT UUID, FInterID,FNAME FROM t_SubMessage   WHERE FTYPEID='504'");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                MaterialType materialType = new MaterialType();
                materialType.setVgsdm(InspurConstant.GDZYGSDM);
                materialType.setVfid("Wise_" + result.get(i).get("UUID"));
                materialType.setVdm(result.get(i).get("FInterID").toString());
                materialType.setVmc(result.get(i).get("FNAME").toString());
                materialType.setDcjrq(new Date());
                materialType.setDxgrq(new Date());
                materialTypeService.saveInspur(materialType);
                materialTypeService.saveLocal(materialType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
