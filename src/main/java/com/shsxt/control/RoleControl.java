package com.shsxt.control;

import com.shsxt.control.base.BaseController;
import com.shsxt.control.base.ResultInfo;
import com.shsxt.dao.TRoleDao;
import com.shsxt.po.TRole;
import com.shsxt.query.RoleQuery;
import com.shsxt.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleControl extends BaseController {
    @Resource
    private RoleService roleService;

    @Resource
    private TRoleDao roleDao;
    /**
     * 查询角色接口
     * @param id
     * @return
     */
    @RequestMapping("select")
    @ResponseBody
    public List<Map<String, Object>> queryByRole (Integer id){
        System.out.println(id);
        return roleService.queryByRole(id);
    }

    /**
     * 角色查询列表接口
     * @param roleQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list (RoleQuery roleQuery){
        return roleService.queryByLimit(roleQuery);
    }

    /**
     * 角色首页
     * @return
     */
    @RequestMapping("index")
    public String index (){
        return "role/role";
    }

    /**
     * 角色添加修改主页
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("index2")
    public String index2 (Integer id, HttpServletRequest request){
        if (null!=id){
            TRole role = roleDao.queryById(id);
            request.setAttribute("role",role);
        }
        return "role/add_update";
    }

    /**
     * 角色添加修改接口
     * @param role
     * @return
     */
    @RequestMapping("index3")
    @ResponseBody
    public ResultInfo index3 (TRole role){
        if (null==role.getId()){
            //添加
            roleService.insert(role);
        }else {
            //修改
            roleService.update(role);
        }
        return success();
    }

    /**
     * 角色删除接口
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete (Integer id){
        roleService.delete(id);
        return success();
    }

    /**
     * 角色授权主页
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String index9 (Integer roleId,HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 角色授权接口
     * @param mids
     * @param roleId
     * @return
     */
    @RequestMapping("addGrant")
    public ResultInfo addGrant (Integer[] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        return success();
    }
}
