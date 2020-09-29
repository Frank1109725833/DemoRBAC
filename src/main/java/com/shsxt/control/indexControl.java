package com.shsxt.control;

import com.shsxt.control.base.BaseController;
import com.shsxt.dao.TPermissionDao;
import com.shsxt.dao.TUserDao;
import com.shsxt.po.TUser;
import com.shsxt.service.PermissionService;
import com.shsxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexControl extends BaseController {
    @Resource
    private TUserDao userDao;
    @Resource
    private PermissionService permissionService;

    @RequestMapping("index")
    public String  index(){
        return "index";
    }

    /**
     * 主视图接口
     * @param request
     * @return
     */
    @RequestMapping("main")
    public String  main(HttpServletRequest request){
        Integer id= LoginUserUtil.releaseUserIdFromCookie(request);
        TUser tUser = userDao.queryById(id);
        request.setAttribute("user",tUser);
        List<String> permissions =permissionService.queryPermissions(id);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }

    @RequestMapping("user/password")
    public String index2(){
        return "user/password";
    }

    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }
}