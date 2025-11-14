package com.test.mylitespring.service;


import com.test.mylitespring.dao.AccountDao;
import com.test.mylitespring.dao.ItemDao;

public class PetStoreServiceV3 {
    private final AccountDao accountDao;
    private final ItemDao itemDao;
    private final String owner;
    private final int version;


    public PetStoreServiceV3(AccountDao accountDao, ItemDao itemDao, String owner) {
        this(accountDao, itemDao, owner, -1);
    }

    public PetStoreServiceV3(AccountDao accountDao, ItemDao itemDao, String owner, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.owner = owner;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public String getOwner() {
        return owner;
    }

    public int getVersion() {
        return version;
    }

}