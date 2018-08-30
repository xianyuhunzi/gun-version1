package com.stylefeng.guns.core.shiro.filter;
import com.stylefeng.guns.core.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import static com.stylefeng.guns.core.common.constant.cache.CacheKey.ACCOUNT_NAME;

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
 *   思路：
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 *  将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 * 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 * @Date :  2018/8/2714:43
 */

public class KickoutSessionControlFilter extends AccessControlFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String kickoutUrl="/kickout"; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;


    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_catch");
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("=====进入在线人数校验=====");
        //当前的认证主体
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        Session session = subject.getSession();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        String account = user.getAccount();

        //当前的sessionId
        Serializable sessionId = session.getId();

        //读取缓存   没有就存入
        Deque<Serializable> deque = cache.get(account);
        if(deque==null){
            deque=new LinkedList <Serializable>();
        }
        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(account, deque);
            logger.info("当前账号{}在线最大数为：{}",account,deque.size());
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        Session kickoutSession=null; //保存踢出去的session对象
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            logger.info("此时的队列为：{}",deque);
            if(kickoutAfter) { //如果踢出后者
                logger.info("踢出后者....");
                kickoutSessionId = deque.removeLast();
                logger.info("去除后者后的队列为：{}",deque);
            } else { //否则踢出前者
                logger.info("踢出前者....");
                kickoutSessionId = deque.removeFirst();
                logger.info("去除前者后的队列为：{}",deque);
            }
            cache.put(account,deque);
            logger.info("踢出的sessionId为：{}",kickoutSessionId);

            try {
                //获取被踢出的sessionId的session对象
                kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                    session=kickoutSession;
//                    session.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }
        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout")!=null) {
            //会话被踢出了
            try {
                //根据提出对象创建主体
//                Subject requestSubject = new Subject.Builder().sessionId(session.getId()).buildSubject();
                //退出登录
                subject.logout();

            } catch (Exception e) { //ignore
                e.printStackTrace();
            }
            saveRequest(request);
//            //重定向
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }
        return true;
    }
}
