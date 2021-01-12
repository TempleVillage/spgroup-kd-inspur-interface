package com.kingdee.service;


import com.kingdee.model.po.Area;

public interface AreaService {

    void saveLocal(Area area) throws Exception;

    void saveInspur(Area area) throws Exception;

    boolean isExist(Area area) throws Exception;

}
