package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.shiro.check.RetryLimitCredentialsMatcher;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author zhengr
 * @since 2018-08-29
 */
@Service
@Transactional
@DependsOn("springContextHolder")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    public  static  IUserService me(){
        return  SpringContextHolder.getBean("userServiceImpl");
    }

    /**
     *CacheEvict 清理缓存
     * @param userId 账号的id
     * @param status 账号的状态， 1:启用，2：冻结， 3：删除（逻辑删除）
     * @return
     */
    @Override
    @CacheEvict(value = "passwordRetryCache",condition = "#status==1")
    public int setStatus(Integer userId, int status) {
     int key=   this.baseMapper.setStatus(userId, status);
     User user =new User();
     user.setId(userId);
     user=   baseMapper.selectOne(user);
     logger.info("正在清理账号{}的缓存",user.getAccount());
     return key;
    }

    @Override
    public int changePwd(Integer userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }

    @Override
    public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer deptid) {
        return this.baseMapper.selectUsers(dataScope, name, beginTime, endTime, deptid);
    }

    @Override
    public int setRoles(Integer userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    @Override
    public int updateByAccount(String account, int status) {
      EntityWrapper<User> ew=  new EntityWrapper <>();
        User user =new User();
        user.setStatus(status);
      int key=  baseMapper.update(user,ew.eq("account",account));

       return key;
    }


}
