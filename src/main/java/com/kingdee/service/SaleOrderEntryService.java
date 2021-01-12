package com.kingdee.service;


import com.kingdee.model.po.SaleOrderEntry;

public interface SaleOrderEntryService {
    void saveLocal(SaleOrderEntry saleOrderEntry) throws Exception;


    void saveInspur(SaleOrderEntry saleOrderEntry) throws Exception;

    boolean isExist(SaleOrderEntry saleOrderEntry) throws Exception;
}
