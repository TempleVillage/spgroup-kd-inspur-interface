package com.kingdee.service;


import com.kingdee.model.po.CustomerType;

public interface CustomerTypeService {
    void saveLocal(CustomerType customerType) throws Exception;


    void saveInspur(CustomerType customerType) throws Exception;

    boolean isExist(CustomerType customerType) throws Exception;
}
