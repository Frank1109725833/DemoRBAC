package com.shsxt.control;

import com.shsxt.control.base.BaseController;
import com.shsxt.control.base.ResultInfo;
import com.shsxt.dao.TUserDao;
import com.shsxt.po.TUser;
import com.shsxt.po.UserModel;
import com.shsxt.query.UserQuery;
import com.shsxt.service.UserService;
import com.shsxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
public class UserControl extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private TUserDao userDao;

    /**
     * 用户登录接口
     * @param name
     * @param pwd
     * @return
     */
    @PostMapping("user/userLogin")
    @ResponseBody
    public ResultInfo userLogin(String name, String pwd) {
        UserModel model = userService.userLogin(name, pwd);
        return success("登录成功！",model);
    }

    /**
     * 用户修改密码接口
     * @param originalPwd
     * @param newPwd
     * @param repeatPwd
     * @param request
     * @return
     */
    @PutMapping("user/userPassword")
    @ResponseBody
    public ResultInfo userPassword(String originalPwd, String newPwd, String repeatPwd, HttpServletRequest request) {
        int id = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.userPassword(id, originalPwd, newPwd, repeatPwd);
        return success();
    }

    /**
     * 用户多条件查询接口
     * @param userQuery
     * @return
     */
    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> list (UserQuery userQuery){
        Map<String, Object> map = userService.queryByParams(userQuery);
        return map;
    }

    /**
     * 用户管理资源
     * @return
     */
    @RequestMapping("user/index")
    public String index(){
        return "user/user";
    }

    /**
     * 用户添加修改资源
     * 附加作用：用户信息传递中介
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("user/index2")
    public String index2 (Integer id,HttpServletRequest request){
        if (null!=id){
            TUser user = userDao.queryById(id);
            request.setAttribute("user",user);
        }
        return "user/add_update";
    }

    /**
     * 用户添加修改接口
     * @param user
     * @return
     */
    @PostMapping("user/index3")
    @ResponseBody
    public ResultInfo index3 (TUser user,String roleIds){
        System.out.println(roleIds);
        if (null==user.getId()){
            //添加用户
            userService.insert(user,roleIds);
        }else {
            //更新用户
            userService.update(user,roleIds);
        }
        return success();
    }

    /**
     * 用户删除接口
     * @param id
     * @return
     */
    @PostMapping("user/delete")
    @ResponseBody
    public ResultInfo delete (Integer[] ids){
        userService.delete(ids);
        return success();
    }
}
