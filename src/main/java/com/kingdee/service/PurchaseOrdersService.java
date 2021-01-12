package com.kingdee.service;

import java.util.List;
import java.util.Map;

import com.kingdee.model.po.PurchaseOrders;
import com.kingdee.model.po.PurorderItem;



public interface PurchaseOrdersService {

    //1 本地库操作
    void saveLocal(PurchaseOrders purchaseOrders) throws Exception;

    void saveItemLocal(PurorderItem purorderItem) throws Exception;

    void updateOrderLocal(PurchaseOrders purchaseOrders) throws Exception;

    void updateItemLocal(PurorderItem purorderItem) throws Exception;

    void saveLocal(List<PurchaseOrders> list_PurchaseOrders) throws Exception;
	
    PurchaseOrders findOneLocal(PurchaseOrders purchaseOrders) throws Exception;

    List<PurchaseOrders> findLocal(PurchaseOrders purchaseOrders) throws Exception;

    List<PurorderItem> findItemLocal(PurchaseOrders purchaseOrders) throws Exception;

    void deltLocal(List<PurchaseOrders> list_PurchaseOrders) throws Exception;
    
    void clearLocal(PurchaseOrders PurchaseOrders) throws Exception;


    //2 电采王朝库
    void saveInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    void updateOrderInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    void updateItemInspurWC(PurorderItem purorderItem) throws Exception;

    void saveInspurWC(List<PurchaseOrders> list_PurchaseOrders) throws Exception;

    PurchaseOrders findOneInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    List<PurchaseOrders> findInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    List<PurorderItem> findItemByOrderIdInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    Map<String, List<PurorderItem>> findSplitOrgItemByOrderIdInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    void deltInspurWC(List<PurchaseOrders> list_PurchaseOrders) throws Exception;

    void clearInspurWC(PurchaseOrders purchaseOrders) throws Exception;

    //3 电采利民库
    void saveInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    void updateOrderInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    void updateItemInspurLM(PurorderItem purorderItem) throws Exception;

    void saveInspurLM(List<PurchaseOrders> list_PurchaseOrders) throws Exception;

    PurchaseOrders findOneInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    List<PurchaseOrders> findInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    List<PurorderItem> findItemByOrderIdInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    Map<String, List<PurorderItem>> findSplitOrgItemByOrderIdInspurLM(PurchaseOrders purchaseOrders) throws Exception;

    void deltInspurLM(List<PurchaseOrders> list_PurchaseOrders) throws Exception;

    void clearInspurLM(PurchaseOrders purchaseOrders) throws Exception;

}
