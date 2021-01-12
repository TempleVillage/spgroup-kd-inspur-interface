package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Material;
import com.kingdee.service.MaterialService;
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
 * @description 物料
 * @date 2020/11/12 17:35
 */
@RequestMapping
@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @RequestMapping("/synMaterial")
    public void synMaterial() {
        System.out.println(new Date() + " 物料后台事务开始");
        synMaterialFromCloud();
        synMaterialFromWise();
    }

    private void synMaterialFromCloud() {
        String request = "{\"FormId\":\"BD_MATERIAL\",\"FieldKeys\":\"FMATERIALID, FNumber,FName,FSpecification,FBaseProperty,FBaseUnitId.fnumber,FCreateDate,FModifyDate\",\"FilterString\":\"FDocumentStatus='C' AND FForbidStatus='A' AND FUseOrgId.FNUMBER='2221' and to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') \",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
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
            Material material = new Material();
            try {
                List<Object> oneInfo = response.get(i);
                material.setVfid("Cloud_" + oneInfo.get(0).toString());
                material.setVgsdm(InspurConstant.LMGSDM);
                material.setVwlbm(oneInfo.get(1).toString());
                material.setVwlmc(oneInfo.get(2).toString());
                material.setVgg(oneInfo.get(3).toString());
                if (oneInfo.get(4) != null) {
                    material.setVwlfl(oneInfo.get(4).toString());
                } else {
                    material.setVwlfl("");
                }
                material.setVjldw(oneInfo.get(5).toString());
                material.setDcjrq(DateUtil.parse(oneInfo.get(6).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                material.setDxgrq(DateUtil.parse(oneInfo.get(7).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                materialService.saveInspur(material);
                materialService.saveLocal(material);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //wise
    private void synMaterialFromWise() {

        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为504 物料分类的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query(" SELECT ic.FNumber,ic.FName,ic.FModel,ic.FTypeID,un.fnumber AS MNUMBER from t_ICItem ic LEFT JOIN t_MeasureUnit un on un.FItemID =ic.FUnitID");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Material material = new Material();
                material.setVgsdm(InspurConstant.GDZYGSDM);
                material.setVfid("Wise_" + result.get(i).get("FNumber"));
                material.setVwlbm(result.get(i).get("FNumber").toString());
                material.setVwlmc(result.get(i).get("FName").toString());
                if (result.get(i).get("FModel") == null) {
                    material.setVgg("");
                } else {
                    material.setVgg(result.get(i).get("FModel").toString());
                }
                material.setVwlfl(result.get(i).get("FTypeID").toString());
                material.setVjldw(result.get(i).get("MNUMBER").toString());
                material.setDcjrq(new Date());
                material.setDxgrq(new Date());
                materialService.saveInspur(material);
                materialService.saveLocal(material);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
