package com.kingdee.service;


import com.kingdee.model.po.InvBal;

public interface InvBalService {

    void saveLocal(InvBal invBal) throws Exception;

    void saveInspur(InvBal invBal) throws Exception;

    void eraseByPeriod_Local(InvBal invBal) throws Exception;

    void eraseByPeriod_Inspur(InvBal invBal) throws Exception;

    boolean isExist(InvBal invBal) throws Exception;

}
