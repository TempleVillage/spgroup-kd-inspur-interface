package com.kingdee.service.impl;

import com.kingdee.dao.SupplierTypeInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.SupplierType;
import com.kingdee.service.SupplierTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SupplierTypeServiceImpl implements SupplierTypeService {


    @Resource
    private SupplierTypeInspurMapper supplierTypeInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(SupplierType supplierType) throws Exception {
        if (supplierTypeInspurMapper.selectByPrimaryKey(supplierType) == null) {
            supplierTypeInspurMapper.insertSelective(supplierType);
        } else {
            supplierTypeInspurMapper.updateByPrimaryKeySelective(supplierType);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(SupplierType supplierType) throws Exception {
        if (supplierTypeInspurMapper.selectByPrimaryKey(supplierType) == null) {
            supplierTypeInspurMapper.insertSelective(supplierType);
        } else {
            supplierTypeInspurMapper.updateByPrimaryKeySelective(supplierType);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(SupplierType supplierType) throws Exception {
        int count = supplierTypeInspurMapper.selectCount(supplierType);
        return count > 0 ? true : false;
    }
}
