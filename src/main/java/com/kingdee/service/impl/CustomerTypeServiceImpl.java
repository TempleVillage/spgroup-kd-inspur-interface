package com.kingdee.service.impl;

import com.kingdee.dao.CustomerTypeInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.CustomerType;
import com.kingdee.service.CustomerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {
    @Resource
    private CustomerTypeInspurMapper customerTypeInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(CustomerType customerType) throws Exception {
        if (customerTypeInspurMapper.selectByPrimaryKey(customerType) == null) {
            customerTypeInspurMapper.insertSelective(customerType);
        } else {
            customerTypeInspurMapper.updateByPrimaryKeySelective(customerType);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(CustomerType customerType) throws Exception {
        if (customerTypeInspurMapper.selectByPrimaryKey(customerType) == null) {
            customerTypeInspurMapper.insertSelective(customerType);
        } else {
            customerTypeInspurMapper.updateByPrimaryKeySelective(customerType);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(CustomerType customerType) throws Exception {
        int count = customerTypeInspurMapper.selectCount(customerType);
        return count > 0 ? true : false;
    }

}
