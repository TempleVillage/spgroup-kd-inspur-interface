package com.kingdee.service;


import com.kingdee.model.po.Country;

public interface CountryService {

    void saveLocal(Country country) throws Exception;


    void saveInspur(Country country) throws Exception;

    Boolean isExist(Country country) throws Exception;

}
