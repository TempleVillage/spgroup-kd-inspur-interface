package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Supplier;
import com.kingdee.service.SupplierService;
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
 * @description 供应商同步
 * @date 2020/11/12 17:56
 */
@RequestMapping
@RestController
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/synSupplier")
    public void synSupplier() {
        synSupplierFromK3Cloud();
        synSupplierFromK3Wise();
    }

    /**
     * @return void
     * @description: 星空供应商同步
     * @date: 2020/11/16 14:57
     * @author: Jambin
     */
    private void synSupplierFromK3Cloud() {
        System.out.println(new Date() + " 供应商后台事务开始");
        String request = "{\"FormId\":\"BD_Supplier\",\"FieldKeys\":\"FSupplierId, FNumber,FName,FCountry.fnumber,FProvincial.fnumber,FSupplierClassify,FCreateDate,FModifyDate\",\"FilterString\":\" FUseOrgId.fnumber='2221'\",\"OrderString\":\"FNumber asc\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        List<List<Object>> response = new ArrayList<List<Object>>();

        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Supplier supplier = new Supplier();
            try {
                List<Object> oneInfo = response.get(i);
                supplier.setVfid("Cloud_" + oneInfo.get(0).toString());
                supplier.setVgsdm(InspurConstant.LMGSDM);
                supplier.setVgysbm(oneInfo.get(1).toString());
                supplier.setVgysmc(oneInfo.get(2).toString());
                if (oneInfo.get(3) != null) {
                    supplier.setVszgj(oneInfo.get(3).toString());
                } else {
                    supplier.setVszgj("");
                }
                if (oneInfo.get(4) != null) {
                    supplier.setVszss(oneInfo.get(4).toString());
                } else {
                    supplier.setVszss("");
                }
                if (oneInfo.get(5) != null) {
                    supplier.setVgysfldm(oneInfo.get(5).toString());
                } else {
                    supplier.setVgysfldm("");
                }
                supplier.setDcjrq(DateUtil.parse(oneInfo.get(6).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                supplier.setDxgrq(DateUtil.parse(oneInfo.get(7).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                supplierService.saveInspur(supplier);
                supplierService.saveLocal(supplier);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise 供应商同步
     * @date: 2020/11/16 14:58
     * @author: Jambin
     */
    private void synSupplierFromK3Wise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT FNumber , FName,FRegionID, FTypeID,FBillDate FROM t_Supplier ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Supplier supplier = new Supplier();
                supplier.setVgsdm(InspurConstant.GDZYGSDM);
                supplier.setVfid("Wise_" + result.get(i).get("FNumber"));
                supplier.setVgysbm(result.get(i).get("FNumber").toString());
                supplier.setVgysmc(result.get(i).get("FName").toString());
                if (result.get(i).get("FRegionID") != null) {
                    supplier.setVszgj(result.get(i).get("FRegionID").toString());
                } else {
                    supplier.setVszgj("");
                }
                if (result.get(i).get("FRegionID") != null) {
                    supplier.setVszss(result.get(i).get("FRegionID").toString());
                } else {
                    supplier.setVszss("");
                }
                if (result.get(i).get("FTypeID") != null) {
                    supplier.setVgysfldm(result.get(i).get("FTypeID").toString());
                } else {
                    supplier.setVgysfldm("");
                }
                supplier.setDcjrq(DateUtil.parse(result.get(i).get("FBillDate").toString(), "yyyy-MM-dd hh:mm:ss"));
                supplier.setDxgrq(new Date());

                supplierService.saveInspur(supplier);
                supplierService.saveLocal(supplier);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
