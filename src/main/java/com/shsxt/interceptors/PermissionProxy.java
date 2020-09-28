package com.shsxt.interceptors;

import com.shsxt.annotation.RequirePermission;
import com.shsxt.exceptions.NoLoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Aspect
@Component
public class PermissionProxy {
    @Resource
    private HttpSession session;

    @Around(value = "@annotation(com.shsxt.annotation.RequirePermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if (null==permissions||permissions.size()==0){
            throw new NoLoginException();
        }

        Object result=null;
        MethodSignature ms= (MethodSignature) pjp.getSignature();
        RequirePermission requirePermission = ms.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if (!permissions.contains(requirePermission.code())){
            throw new NoLoginException();
        }
        result=pjp.proceed();
        return result;
    }

}
