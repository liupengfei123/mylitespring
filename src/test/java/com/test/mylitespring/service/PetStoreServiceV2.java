package com.test.mylitespring.service;


import com.test.mylitespring.dao.AccountDao;
import com.test.mylitespring.dao.ItemDao;

public class PetStoreServiceV2 {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;
    private int version;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}