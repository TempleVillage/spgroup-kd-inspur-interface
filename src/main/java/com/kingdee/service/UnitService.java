package com.kingdee.service;


import com.kingdee.model.po.Unit;

public interface UnitService {
    void saveLocal(Unit unit) throws Exception;

    void saveInspur(Unit unit) throws Exception;

    boolean isExist(Unit unit) throws Exception;
}
