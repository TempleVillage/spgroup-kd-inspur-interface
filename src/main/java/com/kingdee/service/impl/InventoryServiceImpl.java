package com.kingdee.service.impl;

import com.kingdee.dao.InventoryMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Inventory;
import com.kingdee.service.InventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class InventoryServiceImpl implements InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Inventory inventory) throws Exception {
        if (inventoryMapper.selectByPrimaryKey(inventory) == null) {
            inventoryMapper.insertSelective(inventory);
        } else {
            inventoryMapper.updateByPrimaryKeySelective(inventory);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Inventory inventory) throws Exception {
        if (inventoryMapper.selectByPrimaryKey(inventory) == null) {
            inventoryMapper.insertSelective(inventory);
        } else {
            inventoryMapper.updateByPrimaryKeySelective(inventory);
        }
    }

    @DataSource("first")
    @Override
    public void eraseByPeriod_Local(Inventory inventory) throws Exception {
        if (null != inventory.getVqj() && !"".equals(inventory.getVqj())) {
            inventoryMapper.delete(inventory);
        }
    }

    @DataSource("second")
    @Override
    public void eraseByPeriod_Inspur(Inventory inventory) throws Exception {
        if (null != inventory.getVqj() && !"".equals(inventory.getVqj())) {
            inventoryMapper.delete(inventory);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Inventory inventory) throws Exception {
        int count = inventoryMapper.selectCount(inventory);
        return count > 0 ? true : false;
    }
}
