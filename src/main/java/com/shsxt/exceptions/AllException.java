package com.shsxt.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.control.base.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AllException implements HandlerExceptionResolver {
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv=new ModelAndView();
        if(e instanceof NoLoginException)
        {
            mv.setViewName("redirect:index");
            return mv;
        }
        mv.setViewName("error");
        mv.addObject("code",404);
        mv.addObject("msg","出现意料之外的异常，等待程序员修复！");
        if (o instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod) o;
            //获取方法上的ResponseBody注解
            ResponseBody responseBody=handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (null==responseBody){
                //注解不存在返回视图
                if (e instanceof ParamsException){
                    ParamsException pe=(ParamsException) e;
                    mv.addObject("code",pe.getCode());
                    mv.addObject("msg",pe.getMsg());
                }else if (e instanceof NoLoginException){
                    NoLoginException pe=(NoLoginException) e;
                    mv.addObject("code",pe.getCode());
                    mv.addObject("msg",pe.getMsg());
                }
                return mv;
            }else {
                //存在返回Json
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(404);
                resultInfo.setMsg("返回JSON对象时，出现意料之外的异常，等待程序员修复！");
                if (e instanceof ParamsException){
                    ParamsException pe=(ParamsException) e;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter pw=null;
                try {
                    pw=httpServletResponse.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }finally {
                    if (pw!=null){
                        pw.close();
                    }
                }
                return null;
            }
        }
        return mv;
    }
}
