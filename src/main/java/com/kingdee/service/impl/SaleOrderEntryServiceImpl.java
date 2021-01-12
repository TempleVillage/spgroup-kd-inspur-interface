package com.kingdee.service.impl;

import com.kingdee.dao.SaleOrderEntryInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.SaleOrderEntry;
import com.kingdee.service.SaleOrderEntryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SaleOrderEntryServiceImpl implements SaleOrderEntryService {
    @Resource
    private SaleOrderEntryInspurMapper saleOrderEntryInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(SaleOrderEntry saleOrderEntry) throws Exception {
        if (saleOrderEntryInspurMapper.selectByPrimaryKey(saleOrderEntry) == null) {
            saleOrderEntryInspurMapper.insertSelective(saleOrderEntry);
        } else {
            saleOrderEntryInspurMapper.updateByPrimaryKeySelective(saleOrderEntry);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(SaleOrderEntry saleOrderEntry) throws Exception {
        if (saleOrderEntryInspurMapper.selectByPrimaryKey(saleOrderEntry) == null) {
            saleOrderEntryInspurMapper.insertSelective(saleOrderEntry);
        } else {
            saleOrderEntryInspurMapper.updateByPrimaryKeySelective(saleOrderEntry);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(SaleOrderEntry saleOrderEntry) throws Exception {
        int count = saleOrderEntryInspurMapper.selectCount(saleOrderEntry);
        return count > 0 ? true : false;
    }
}
