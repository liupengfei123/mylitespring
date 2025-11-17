package com.test.mylitespring.service;

import com.test.mylitespring.dao.AccountDaoV4;
import com.test.mylitespring.dao.ItemDaoV4;
import org.mylitespring.beans.factory.annotation.Autowired;
import org.mylitespring.stereotype.Component;
import org.mylitespring.utils.MessageTracker;

@Component(value = "petStore")
public class PetStoreServiceV5 {
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


    public void placeOrder() {
        MessageTracker.addMessage("place order");
        System.out.println("place order");
    }
}