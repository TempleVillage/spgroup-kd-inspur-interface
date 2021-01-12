package com.kingdee.service;


import com.kingdee.model.po.Inventory;

public interface InventoryService {

    void saveLocal(Inventory inventory) throws Exception;

    void saveInspur(Inventory inventory) throws Exception;

    void eraseByPeriod_Local(Inventory inventory) throws Exception;

    void eraseByPeriod_Inspur(Inventory inventory) throws Exception;

    boolean isExist(Inventory inventory) throws Exception;

}
