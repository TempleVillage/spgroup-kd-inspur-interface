package com.kingdee.service.impl;

import com.kingdee.dao.CurrencyInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Currency;
import com.kingdee.service.CurrencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Resource
    private CurrencyInspurMapper currencyInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Currency currency) throws Exception {
        if (currencyInspurMapper.selectByPrimaryKey(currency) == null) {
            currencyInspurMapper.insertSelective(currency);
        } else {
            currencyInspurMapper.updateByPrimaryKeySelective(currency);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Currency currency) throws Exception {
        if (currencyInspurMapper.selectByPrimaryKey(currency) == null) {
            currencyInspurMapper.insertSelective(currency);
        } else {
            currencyInspurMapper.updateByPrimaryKeySelective(currency);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Currency currency) throws Exception {
        int count = currencyInspurMapper.selectCount(currency);
        return count > 0 ? true : false;
    }
}
