package com.kingdee.service;


import com.kingdee.model.po.Customer;

public interface CustomerService {

    void saveLocal(Customer customer) throws Exception;

    void saveInspur(Customer customer) throws Exception;

    boolean isExist(Customer customer) throws Exception;

}
