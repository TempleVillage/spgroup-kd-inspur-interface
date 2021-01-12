package com.kingdee.service;


import com.kingdee.model.po.Currency;

public interface CurrencyService {
    void saveLocal(Currency currency) throws Exception;

    void saveInspur(Currency currency) throws Exception;

    boolean isExist(Currency currency) throws Exception;
}
