package com.kingdee.service.impl;

import com.kingdee.dao.CountryInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Country;
import com.kingdee.service.CountryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CountryServiceImpl implements CountryService {


    @Resource
    private CountryInspurMapper countryInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Country country) throws Exception {
        if (countryInspurMapper.selectByPrimaryKey(country) == null) {
            countryInspurMapper.insertSelective(country);
        } else {
            countryInspurMapper.updateByPrimaryKeySelective(country);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Country country) throws Exception {
        if (countryInspurMapper.selectByPrimaryKey(country) == null) {
            countryInspurMapper.insertSelective(country);
        } else {
            countryInspurMapper.updateByPrimaryKeySelective(country);
        }
    }

    @DataSource("second")
    @Override
    public Boolean isExist(Country country) throws Exception {
        int count = countryInspurMapper.selectCount(country);
        return count > 0 ? true : false;
    }
}
