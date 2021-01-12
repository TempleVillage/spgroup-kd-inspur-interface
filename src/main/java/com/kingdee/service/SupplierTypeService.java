package com.kingdee.service;


import com.kingdee.model.po.SupplierType;

public interface SupplierTypeService {

    void saveLocal(SupplierType supplierType) throws Exception;


    void saveInspur(SupplierType supplierType) throws Exception;

    boolean isExist(SupplierType supplierType) throws Exception;
}
