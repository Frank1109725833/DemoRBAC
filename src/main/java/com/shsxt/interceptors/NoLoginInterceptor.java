package com.shsxt.interceptors;

import com.shsxt.dao.TUserDao;
import com.shsxt.exceptions.NoLoginException;
import com.shsxt.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private TUserDao userDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer id= LoginUserUtil.releaseUserIdFromCookie(request);
        System.out.println("拦截器");
        if(id==null||userDao.queryById(id)==null)
        {
            throw new NoLoginException();
        }
        return true;
    }
}
