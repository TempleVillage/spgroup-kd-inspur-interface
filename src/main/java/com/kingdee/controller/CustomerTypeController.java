package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.CustomerType;
import com.kingdee.service.CustomerTypeService;
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
 * @description 客户分类同步
 * @date 2020/11/12 17:07
 */
@RequestMapping
@RestController
public class CustomerTypeController {
    @Autowired
    private CustomerTypeService customerTypeService;

    @RequestMapping("/synCustomerType")
    public void synCustomerType() {
        System.out.println(new Date() + " 客户分类同步后台事务开始");
        synCustomerTypeFromCloud();
        synCustomerTypeFromWise();
    }

    /**
     * @return void
     * @description: 星空
     * @date: 2020/11/25 14:56
     * @author: Jambin
     */
    private void synCustomerTypeFromCloud() {
        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BOS_ASSISTANTDATA_DETAIL\",\"FieldKeys\":\"FEntryID,fnumber,fdatavalue,FCreateDate,FModifyDate\",\"FilterString\":\"fid='02679e312589445f858747479a238cc7'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            CustomerType customerType = new CustomerType();
            try {
                List<Object> oneInfo = response.get(i);
                customerType.setVfid("Cloud_" + oneInfo.get(0).toString());
                customerType.setVgsdm(InspurConstant.LMGSDM);
                customerType.setVdm(oneInfo.get(1).toString());
                customerType.setVmc(oneInfo.get(2).toString());
                customerType.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                customerType.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                customerTypeService.saveLocal(customerType);
                customerTypeService.saveInspur(customerType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise
     * @date: 2020/11/25 14:56
     * @author: Jambin
     */
    private void synCustomerTypeFromWise() {
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为501 客户分类的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query("SELECT UUID, FInterID,FNAME FROM t_SubMessage  WHERE FTYPEID='501' ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
            try {
                CustomerType customerType = new CustomerType();
                customerType.setVgsdm(InspurConstant.GDZYGSDM);
                customerType.setVfid("Wise_" + result.get(i).get("UUID"));
                customerType.setVdm(result.get(i).get("FInterID").toString());
                customerType.setVmc(result.get(i).get("FNAME").toString());
                customerType.setDcjrq(new Date());
                customerType.setDxgrq(new Date());

                customerTypeService.saveLocal(customerType);
                customerTypeService.saveInspur(customerType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
