package com.kingdee.service.impl;

import com.kingdee.dao.OrganizationInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Organization;
import com.kingdee.service.OrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Resource
    private OrganizationInspurMapper organizationInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Organization organization) throws Exception {
        if (organizationInspurMapper.selectByPrimaryKey(organization) == null) {
            organizationInspurMapper.insertSelective(organization);
        } else {
            organizationInspurMapper.updateByPrimaryKeySelective(organization);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Organization organization) throws Exception {
        if (organizationInspurMapper.selectByPrimaryKey(organization) == null) {
            organizationInspurMapper.insertSelective(organization);
        } else {
            organizationInspurMapper.updateByPrimaryKeySelective(organization);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Organization organization) throws Exception {
        int count = organizationInspurMapper.selectCount(organization);
        return count > 0 ? true : false;
    }
}
