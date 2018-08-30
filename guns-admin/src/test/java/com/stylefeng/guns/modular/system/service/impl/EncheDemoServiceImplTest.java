package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IEncheDemoService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by zhengr on 2018/8/29.
 *
 * @Description todo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncheDemoServiceImplTest {
    @Autowired
    private IEncheDemoService encheDemoService;

    @Autowired
    private IUserService userService;

    @Test
    public void test() throws InterruptedException {
        System.out.println("第一次调用：" + encheDemoService.getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + encheDemoService.getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + encheDemoService.getTimestamp("param"));
    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println("第一次调用：" + encheDemoService.getUser("admin"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + encheDemoService.getUser("admin"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + encheDemoService.getUser("admin"));
    }

    @Test
    public void testClearCookie(){
        User user =new User();
        user .setId(3);
        encheDemoService.addUser(user);
    }
}