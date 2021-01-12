package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Customer;
import com.kingdee.service.CustomerService;
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
 * @description 客户同步
 * @date 2020/11/12 14:53
 */
@RequestMapping
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/synCustomer")
    public void synCustomer() {
        System.out.println(new Date() + " 客户同步后台事务开始");
        synCustomerFromCloud();
        synCustomerFromWise();
    }

    //星空
    private void synCustomerFromCloud() {
        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BD_Customer\",\"FieldKeys\":\"FCUSTID,fnumber,fname,FCOUNTRY.fnumber,FPROVINCIAL.fnumber,FCustTypeId.fnumber,FCreateDate,FModifyDate\",\"FilterString\":\"to_char(FModifyDate,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') and FUseOrgId.fnumber='2221'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Customer customer = new Customer();
            try {
                List<Object> oneInfo = response.get(i);
                customer.setVfid("Cloud_" + oneInfo.get(0).toString());
                customer.setVgsdm(InspurConstant.LMGSDM);
                customer.setVkhbm(oneInfo.get(1).toString());
                customer.setVkhmc(oneInfo.get(2).toString());
                if (null != oneInfo.get(3)) {
                    customer.setVszgj(oneInfo.get(3).toString());
                } else {
                    customer.setVszgj("");
                }
                if (null != oneInfo.get(4)) {
                    customer.setVszss(oneInfo.get(4).toString());
                } else {
                    customer.setVszss("");
                }
                if (null != oneInfo.get(5)) {
                    customer.setVkhfldm(oneInfo.get(5).toString());
                } else {
                    customer.setVkhfldm("");
                }
                customer.setDcjrq(DateUtil.parse(oneInfo.get(6).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                customer.setDxgrq(DateUtil.parse(oneInfo.get(7).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                customerService.saveInspur(customer);
                customerService.saveLocal(customer);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //wise
    private void synCustomerFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为26 区域的数据
        List<Entity> result = null;
        try {
            //TODO 添加条件为修改日期=当前时间
            result = Db.use(dataSource).query("SELECT fnumber,fname,FRegionID,FTypeID ,fregdate,FLastModifyDate FROM t_Organization   where FDeleted =0 ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            Customer customer = new Customer();
            try {
                customer.setVgsdm(InspurConstant.GDZYGSDM);
                customer.setVfid("Wise_" + result.get(i).get("fnumber"));
                customer.setVkhbm(result.get(i).get("fnumber").toString());
                customer.setVkhmc(result.get(i).get("fname").toString());
                customer.setVszgj(result.get(i).get("FRegionID").toString());
                customer.setVszss(result.get(i).get("FRegionID").toString());
                customer.setVkhfldm(result.get(i).get("FTypeID").toString());
                customer.setDcjrq(DateUtil.parse(result.get(i).get("fregdate").toString(), "yyyy-MM-dd hh:mm:ss"));
                if (result.get(i).get("FLastModifyDate") == null) {
                    customer.setDxgrq(new Date());
                } else {
                    customer.setDxgrq(DateUtil.parse(result.get(i).get("FLastModifyDate").toString(), "yyyy-MM-dd hh:mm:ss"));
                }

                customerService.saveInspur(customer);
                customerService.saveLocal(customer);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
