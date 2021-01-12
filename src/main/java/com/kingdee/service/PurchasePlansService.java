package com.kingdee.service;

import java.util.List;

import com.kingdee.model.po.PurchasePlans;



public interface PurchasePlansService {
	
    void saveLocal(PurchasePlans purchasePlans) throws Exception;

    void saveLocal(List<PurchasePlans> list_PurchasePlans) throws Exception;

    List<PurchasePlans> findLocal(PurchasePlans purchasePlans) throws Exception;

    void deltLocal(List<PurchasePlans> list_PurchasePlans) throws Exception;
    
    void clearLocal(PurchasePlans purchasePlans) throws Exception;
    
	
    //浪潮
    void saveInspurWC(PurchasePlans purchasePlans) throws Exception;

    void saveInspurWC(List<PurchasePlans> list_PurchasePlans) throws Exception;

    List<PurchasePlans> findInspurWC(PurchasePlans purchasePlans) throws Exception;

    PurchasePlans findOneInspurWC(PurchasePlans purchasePlans) throws Exception;

    void deltInspurWC(List<PurchasePlans> list_PurchasePlans) throws Exception;
    
    void clearInspurWC(PurchasePlans purchasePlans) throws Exception;

}
