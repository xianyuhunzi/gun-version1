package com.stylefeng.guns.system;/**
 * Created by zhengr on 2018/8/24.
 *
 * @Description todo
 */

import com.stylefeng.guns.core.common.constant.factory.ITestFactory;
import com.stylefeng.guns.core.common.constant.factory.TestFactory;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.nutz.ssdb4j.SSDBs;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Deque;
import java.util.LinkedList;

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
 * @Date :  2018/8/2417:48
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringContextHolderTest {
    @Test
    public  void test1(){
        TestFactory.me().say();
    }
    @Test
    public  void test(){
        System.out.println("hello");
    }

    @Test
    public void testAdd(){
      int num=  TestFactory.me().add(1,3);
        System.out.println(num);
    }

    @Test
    public void testLinkList() {
        Deque <Integer>deque = new LinkedList();
        deque.offer(1);
        deque.offer(2);
        deque.offer(3);
        System.out.println("init deque :" + deque);
       int i= deque.poll();

        System.out.println("after execte poll:"+deque);
        System.out.println("after execte peek:"+ deque.peek());
//        deque.removeFirst();

    }
}
