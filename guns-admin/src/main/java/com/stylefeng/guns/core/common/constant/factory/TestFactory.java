package com.stylefeng.guns.core.common.constant.factory;/**
 * Created by zhengr on 2018/8/24.
 *
 * @Description todo
 */

import com.stylefeng.guns.core.util.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 * @Description
 * ---------------------------------
 * @Author : zr
 * @Date :  2018/8/2417:44
 */
@Component("myFactory")
@DependsOn("springContextHolder")
public class TestFactory implements  ITestFactory {
    @Override
    public void say() {
        System.out.println("hello!world");
    }

    @Override
    public int add(int num1, int num2) {
        return num1+num2;
    }

    public static ITestFactory me() {
        return SpringContextHolder.getBean("myFactory");
    }

}
