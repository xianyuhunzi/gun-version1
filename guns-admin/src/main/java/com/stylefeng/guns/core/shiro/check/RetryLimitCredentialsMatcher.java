package com.stylefeng.guns.core.shiro.check;

import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.system.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.atomic.AtomicInteger;

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
 * @Date :  2018/8/2815:59
 */

public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;
    private   String  hashAlgorithmName;
    private int hashIterations;

    public RetryLimitCredentialsMatcher(CacheManager cacheShiroManager, String hashAlgorithmName, int hashIterations) {
        passwordRetryCache = cacheShiroManager.getCache("passwordRetryCache");
        this.hashAlgorithmName=hashAlgorithmName;
        this.hashIterations=hashIterations;
    }

    @Override
    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    @Override
    public int getHashIterations() {
        return hashIterations;
    }

    @Override
    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    @Override
    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName =  ShiroKit.hashAlgorithmName;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        logger.info("进入密码次数.....");
        ShiroKit.getRandomSalt(5);
        String username = (String)token.getPrincipal();
        //current account retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            // 设置账号冻结
            UserServiceImpl.me().updateByAccount(username,2);
            logger.warn("username: " + username + " tried to login more than 5 times in period");
            throw new ExcessiveAttemptsException("username: " + username + " tried to login more than 5 times in period"
            );
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear current retry data
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
