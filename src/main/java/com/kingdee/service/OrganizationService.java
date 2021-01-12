package com.kingdee.service;


import com.kingdee.model.po.Organization;

public interface OrganizationService {
    void saveLocal(Organization organization) throws Exception;


    void saveInspur(Organization organization) throws Exception;

    boolean isExist(Organization organization) throws Exception;
}
