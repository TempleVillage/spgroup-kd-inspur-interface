package com.kingdee.service.impl;

import com.kingdee.dao.SaleOrderInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.SaleOrder;
import com.kingdee.service.SaleOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {
    @Resource
    private SaleOrderInspurMapper saleOrderInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(SaleOrder saleOrder) throws Exception {
        if (saleOrderInspurMapper.selectByPrimaryKey(saleOrder) == null) {
            saleOrderInspurMapper.insertSelective(saleOrder);
        } else {
            saleOrderInspurMapper.updateByPrimaryKeySelective(saleOrder);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(SaleOrder saleOrder) throws Exception {
        if (saleOrderInspurMapper.selectByPrimaryKey(saleOrder) == null) {
            saleOrderInspurMapper.insertSelective(saleOrder);
        } else {
            saleOrderInspurMapper.updateByPrimaryKeySelective(saleOrder);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(SaleOrder saleOrder) throws Exception {
        int count = saleOrderInspurMapper.selectCount(saleOrder);
        return count > 0 ? true : false;
    }
}
