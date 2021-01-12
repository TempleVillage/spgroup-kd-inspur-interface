package com.kingdee.service;


import com.kingdee.model.po.Supplier;

public interface SupplierService {
    void saveLocal(Supplier supplier) throws Exception;

    void saveInspur(Supplier supplier) throws Exception;

    boolean isExist(Supplier supplier) throws Exception;
}
