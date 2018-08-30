package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.system.model.OperationLog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.Food;
import com.stylefeng.guns.modular.system.service.IFoodService;

/**
 * food控制器
 *
 * @author fengshuonan
 * @Date 2018-08-15 14:53:22
 */
@Controller
@RequestMapping("/food")
public class FoodController extends BaseController {

    private String PREFIX = "/system/food/";

    @Autowired
    private IFoodService foodService;

    /**
     * 跳转到food首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "food.html";
    }

    /**
     * 跳转到添加food
     */
    @RequestMapping("/food_add")
    public String foodAdd() {
        return PREFIX + "food_add.html";
    }

    /**
     * 跳转到修改food
     */
    @ApiOperation("食品")
    @RequestMapping("/food_update/{foodId}")
    public String foodUpdate(@PathVariable Integer foodId, Model model) {
        Food food = foodService.selectById(foodId);
        model.addAttribute("item",food);
        LogObjectHolder.me().set(food);
        return PREFIX + "food_edit.html";
    }

    /**
     * 获取food列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Food food =new Food();
        Page<Food> page = new PageFactory<Food>().defaultPage();
        EntityWrapper wrapper=new EntityWrapper();
        wrapper.like("name",condition, SqlLike.DEFAULT);
        Page<Food> page1= foodService.selectFoodList(wrapper,page.getCurrent(),page.getSize());
        System.out.println(super.packForBT(page1).toString());
        return super.packForBT(page1);
    }

    /**
     * 新增food
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Food food) {
        foodService.insert(food);
        return SUCCESS_TIP;
    }

    /**
     * 删除food
     */
    @ApiOperation("根据id删除食物信息")
    @ApiImplicitParam(name = "foodId",value = "食品id" ,required = true)
    @PostMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long foodId) {
        foodService.deleteById(foodId);
        return SUCCESS_TIP;
    }

    /**
     * 修改food
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Food food) {
        foodService.updateById(food);
        return SUCCESS_TIP;
    }

    /**
     * food详情
     */
    @RequestMapping(value = "/detail/{foodId}")
    @ResponseBody
    public Object detail(@PathVariable("foodId") Integer foodId) {
        return foodService.selectById(foodId);
    }
}
