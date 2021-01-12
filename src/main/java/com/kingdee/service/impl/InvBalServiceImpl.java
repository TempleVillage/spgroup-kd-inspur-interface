package com.kingdee.service.impl;

import com.kingdee.dao.InvBalMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.InvBal;
import com.kingdee.service.InvBalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InvBalServiceImpl implements InvBalService {
    @Resource
    private InvBalMapper invBalMapper;

    @DataSource("first")
    @Override
    public void saveLocal(InvBal invBal) throws Exception {
        if (invBalMapper.selectByPrimaryKey(invBal) == null) {
            invBalMapper.insertSelective(invBal);
        } else {
            invBalMapper.updateByPrimaryKeySelective(invBal);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(InvBal invBal) throws Exception {
        if (invBalMapper.selectByPrimaryKey(invBal) == null) {
            invBalMapper.insertSelective(invBal);
        } else {
            invBalMapper.updateByPrimaryKeySelective(invBal);
        }
    }

    @DataSource("first")
    @Override
    public void eraseByPeriod_Local(InvBal invBal) throws Exception {
        if (null != invBal.getVqj() && !"".equals(invBal.getVqj())) {
            invBalMapper.delete(invBal);
        }
    }

    @DataSource("second")
    @Override
    public void eraseByPeriod_Inspur(InvBal invBal) throws Exception {
        if (null != invBal.getVqj() && !"".equals(invBal.getVqj())) {
            invBalMapper.delete(invBal);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(InvBal invBal) throws Exception {
        int count = invBalMapper.selectCount(invBal);

        return count > 0 ? true : false;
    }


}
