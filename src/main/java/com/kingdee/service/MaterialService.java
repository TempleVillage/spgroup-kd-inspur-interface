package com.kingdee.service;


import com.kingdee.model.po.Material;

public interface MaterialService {
    void saveLocal(Material marerial) throws Exception;


    void saveInspur(Material marerial) throws Exception;

    boolean isExist(Material material)throws  Exception;
}
