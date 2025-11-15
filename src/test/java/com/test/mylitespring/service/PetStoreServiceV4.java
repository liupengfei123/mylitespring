package com.test.mylitespring.service;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.dao.ItemDaoV4;
import org.mylitespring.beans.factory.annotation.Autowired;
import org.mylitespring.stereotype.Component;

@Component(value = "petStore")
public class PetStoreServiceV4 {
    @Autowired
    private AccountDaoV4 accountDao;

    @Autowired
    private ItemDaoV4 itemDao;



    public AccountDaoV4 getAccountDao() {
        return accountDao;
    }

    public ItemDaoV4 getItemDao() {
        return itemDao;
    }


}