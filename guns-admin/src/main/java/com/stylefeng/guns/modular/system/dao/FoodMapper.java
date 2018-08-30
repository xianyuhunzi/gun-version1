package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.system.model.Food;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 食品表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-08-15
 */
public interface FoodMapper extends BaseMapper<Food> {
    List<Food> selectFood(RowBounds rowBounds, @Param("ew") Wrapper<?> wrapper);
}
