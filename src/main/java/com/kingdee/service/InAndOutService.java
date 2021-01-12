package com.kingdee.service;


import com.kingdee.model.po.InAndOut;

public interface InAndOutService {
    void saveLocal(InAndOut inAndOut) throws Exception;

    void saveInspur(InAndOut inAndOut) throws Exception;

    boolean isExist(InAndOut inAndOut) throws Exception;
}
