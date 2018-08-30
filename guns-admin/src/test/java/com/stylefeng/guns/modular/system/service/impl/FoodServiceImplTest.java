package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.system.model.Food;
import com.stylefeng.guns.modular.system.service.IFoodService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by zhengr on 2018/8/27.
 *
 * @Description todo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodServiceImplTest {
    @Autowired
    private IFoodService foodService;


    // 测试事务是否起作用
    @Test
    public  void testAdd(){
        Food food =new Food();
        food.setName("xianyu");
        foodService.insert(food);
    }

    // 模糊查询加分页
    @Test
    public void testList(){
        EntityWrapper ew=new EntityWrapper(new Food());
        ew.like("name","咸", SqlLike.DEFAULT);
        com.baomidou.mybatisplus.plugins.Page <Food> page=foodService.selectFoodList(ew,0,2);
        System.out.println(page.getRecords());
    }
}