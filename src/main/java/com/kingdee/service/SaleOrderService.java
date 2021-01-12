package com.kingdee.service;


import com.kingdee.model.po.SaleOrder;

public interface SaleOrderService {
    void saveLocal(SaleOrder saleOrder) throws Exception;


    void saveInspur(SaleOrder saleOrder) throws Exception;

    boolean isExist(SaleOrder saleOrder) throws Exception;
}
