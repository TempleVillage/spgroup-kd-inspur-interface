package com.kingdee.service;


import com.kingdee.model.po.MaterialType;

public interface MaterialTypeService {
    void saveLocal(MaterialType materialType) throws Exception;

    void saveInspur(MaterialType materialType) throws Exception;

    boolean isExist(MaterialType materialType) throws Exception;
}
