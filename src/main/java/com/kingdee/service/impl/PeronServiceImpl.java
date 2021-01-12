package com.kingdee.service.impl;

import com.kingdee.dao.PersonInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Person;
import com.kingdee.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class PeronServiceImpl implements PersonService {
    @Resource
    private PersonInspurMapper personInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Person person) throws Exception {
        if (personInspurMapper.selectByPrimaryKey(person) == null) {
            personInspurMapper.insertSelective(person);
        } else {
            personInspurMapper.updateByPrimaryKeySelective(person);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Person person) throws Exception {
        if (personInspurMapper.selectByPrimaryKey(person) == null) {
            personInspurMapper.insertSelective(person);
        } else {
            personInspurMapper.updateByPrimaryKeySelective(person);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Person person) throws Exception {
        int count = personInspurMapper.selectCount(person);
        return count > 0 ? true : false;
    }
}
