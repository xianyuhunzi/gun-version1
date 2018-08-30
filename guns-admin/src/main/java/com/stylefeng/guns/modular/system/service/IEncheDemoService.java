package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.User;

/**

/**
 *@Author zhengr
 *@date 2018/8/29 11:22
 *@Description todo
 *@Version 1.0
 */
public interface IEncheDemoService {
    public String getTimestamp(String param);

    public User  getUser(String account);

    public boolean addUser(User user);
}
