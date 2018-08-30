package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.system.model.Food;
import com.baomidou.mybatisplus.service.IService;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 食品表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-08-15
 */
public interface IFoodService extends IService<Food> {
    //查询分页
    Page<Food> selectFoodList(EntityWrapper ew,int pageNo,int pageSize);
}
