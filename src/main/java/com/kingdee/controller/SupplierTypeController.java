package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.SupplierType;
import com.kingdee.service.SupplierTypeService;
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
 * @description 供应商分类同步
 * @date 2020/11/12 17:59
 */
@RequestMapping
@RestController
public class SupplierTypeController {
    @Autowired
    private SupplierTypeService supplierTypeService;

    @RequestMapping("/synSupplierType")
    public void synSupplierType() {
        System.out.println(new Date() + " 供应商分类后台事务开始");
        synSupplierTypeFromCloud();
        synSupplierTypeFromWise();
    }

    /**
     * @param
     * @return void
     * @description: 星空供应商分类同步
     * @date: 2020/11/18 9:02
     * @author: Jambin
     */
    private void synSupplierTypeFromCloud() {

        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BOS_ASSISTANTDATA_DETAIL\",\"FieldKeys\":\"FEntryID,fnumber,fdatavalue,FCreateDate,FModifyDate\",\"FilterString\":\"fid='442339bbee6c4f05b7f249e6d83ac9e2'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            SupplierType supplierType = new SupplierType();
            try {
                List<Object> oneInfo = response.get(i);
                supplierType.setVfid("Cloud_" + oneInfo.get(0).toString());
                supplierType.setVgsdm(InspurConstant.LMGSDM);
                supplierType.setVdm(oneInfo.get(1).toString());
                supplierType.setVmc(oneInfo.get(2).toString());
                supplierType.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                supplierType.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));

                supplierTypeService.saveInspur(supplierType);
                supplierTypeService.saveLocal(supplierType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @param
     * @return void
     * @description: wise供应商分类同步
     * @date: 2020/11/18 9:02
     * @author: Jambin
     */
    private void synSupplierTypeFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为27 供应商分类的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("  SELECT UUID, FInterID,FNAME FROM t_SubMessage   WHERE FTYPEID='27' ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                SupplierType supplierType = new SupplierType();
                supplierType.setVgsdm(InspurConstant.GDZYGSDM);
                supplierType.setVfid("Wise_" + result.get(i).get("UUID"));
                supplierType.setVdm(result.get(i).get("FInterID").toString());
                supplierType.setVmc(result.get(i).get("FNAME").toString());
                supplierType.setDcjrq(new Date());
                supplierType.setDxgrq(new Date());

                supplierTypeService.saveInspur(supplierType);
                supplierTypeService.saveLocal(supplierType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
