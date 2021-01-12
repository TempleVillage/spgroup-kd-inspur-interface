package com.kingdee.service;


import com.kingdee.model.po.Person;

public interface PersonService {
    void saveLocal(Person person) throws Exception;


    void saveInspur(Person person) throws Exception;

    boolean isExist(Person person) throws Exception;
}
