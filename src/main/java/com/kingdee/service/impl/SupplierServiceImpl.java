package com.kingdee.service.impl;

import com.kingdee.dao.SupplierInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Supplier;
import com.kingdee.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jambin
 * @description
 * @date 2020/11/6 11:44
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private SupplierInspurMapper supplierInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Supplier supplier) throws Exception {
        if (supplierInspurMapper.selectByPrimaryKey(supplier) == null) {
            supplierInspurMapper.insertSelective(supplier);
        } else {
            supplierInspurMapper.updateByPrimaryKeySelective(supplier);
        }

    }

    @DataSource("second")
    @Override
    public void saveInspur(Supplier supplier) throws Exception {
        if (supplierInspurMapper.selectByPrimaryKey(supplier) == null) {
            supplierInspurMapper.insertSelective(supplier);
        } else {
            supplierInspurMapper.updateByPrimaryKeySelective(supplier);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Supplier supplier) throws Exception {
        int count = supplierInspurMapper.selectCount(supplier);
        return count > 0 ? true : false;
    }
}
