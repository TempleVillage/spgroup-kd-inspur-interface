package com.kingdee.service.impl;

import com.kingdee.dao.CustomerInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Customer;
import com.kingdee.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerInspurMapper customerInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Customer customer) throws Exception {
        if (customerInspurMapper.selectByPrimaryKey(customer) == null) {
            customerInspurMapper.insertSelective(customer);
        } else {
            customerInspurMapper.updateByPrimaryKeySelective(customer);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Customer customer) throws Exception {
        if (customerInspurMapper.selectByPrimaryKey(customer) == null) {
            customerInspurMapper.insertSelective(customer);
        } else {
            customerInspurMapper.updateByPrimaryKeySelective(customer);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Customer customer) throws Exception {
        int count = customerInspurMapper.selectCount(customer);
        return count > 0 ? true : false;
    }
}
