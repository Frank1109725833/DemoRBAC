package com.shsxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.control.base.ResultInfo;
import com.shsxt.dao.TUserDao;
import com.shsxt.dao.TUserRoleDao;
import com.shsxt.po.TUser;
import com.shsxt.po.TUserRole;
import com.shsxt.po.UserModel;
import com.shsxt.query.UserQuery;
import com.shsxt.utils.AssertUtil;
import com.shsxt.utils.Md5Util;
import com.shsxt.utils.PhoneUtil;
import com.shsxt.utils.UserIDBase64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService {
    @Resource
    private TUserDao tUserDao;
    @Resource
    private TUserRoleDao userRoleDao;

    private static ResultInfo resultInfo;

    /**
     * 用户登录
     * @param name
     * @param pwd
     * @return
     */
    public UserModel userLogin(String name, String pwd) {
        AssertUtil.isTrue(StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd), "用户名或密码不能为空！");
        TUser tUser = tUserDao.queryByName(name);
        AssertUtil.isTrue(StringUtils.isEmpty(tUser), "用户名不存在！");
        String encode = Md5Util.encode(pwd);
        AssertUtil.isTrue(!encode.equals(tUser.getUserPwd()), "密码错误！");
        return userModel(tUser);
    }

    /**
     * 修改密码
     * @param id
     * @param originalPwd
     * @param newPwd
     * @param repeatPwd
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void userPassword(Integer id, String originalPwd, String newPwd, String repeatPwd) {
        AssertUtil.isTrue(id == null, "用户不存在！");
        TUser tUser = tUserDao.queryById(id);
        AssertUtil.isTrue(tUser == null, "用户不存在！");
        AssertUtil.isTrue(StringUtils.isEmpty(originalPwd), "密码不能为空！");
        AssertUtil.isTrue(StringUtils.isEmpty(newPwd), "新密码不能为空！");
        AssertUtil.isTrue(StringUtils.isEmpty(repeatPwd), "重复密码不能为空！");
        String encode = Md5Util.encode(originalPwd);
        AssertUtil.isTrue(!encode.equals(tUser.getUserPwd()), "原密码不正确！");
        String encodeNewPwd = Md5Util.encode(newPwd);
        AssertUtil.isTrue(encodeNewPwd.equals(tUser.getUserPwd()), "新密码不能与原密码相同！");
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "重复密码与新密码不相同！");
        TUser user = new TUser(id,encodeNewPwd);
        AssertUtil.isTrue(tUserDao.update(user) != 1, "修改密码失败！");
    }

    /**
     * 多条件查询
     * @param userQuery
     * @return
     */
    public Map<String,Object> queryByParams(UserQuery userQuery){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<TUser> pageInfo=new PageInfo<>(tUserDao.queryByParams(userQuery));
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 将相关值存入cookie中以便使用
     * @param tUser
     * @return
     */
    public UserModel userModel(TUser tUser) {
        UserModel model = new UserModel();
        String s = UserIDBase64.encoderUserID(tUser.getId());
        model.setUserIdb64(s);
        model.setUserName(tUser.getUserName());
        model.setTrueName(tUser.getTrueName());
        return model;
    }

    /**
     * 添加用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert (TUser user,String roleIds){
        check(user);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(tUserDao.insert(user)!=1,"用户添加失败！");
        relaionUserRole(user.getId(),roleIds);
    }

    /**
     * 更新用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update (TUser user,String roleIds){
        AssertUtil.isTrue(null==user.getId(),"用户ID不存在！");
        AssertUtil.isTrue(null==tUserDao.queryById(user.getId()),"此ID用户不存在！");
        check(user);
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(tUserDao.update(user)!=1,"用户添加失败！");
        relaionUserRole(user.getId(),roleIds);
    }

    /**
     * 删除用户
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete (Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length<1,"请选择要删除的用户！");
        AssertUtil.isTrue(tUserDao.deleteById1(ids)!=ids.length,"用户删除失败！");
        userRoleDao.delete2(ids);
    }

    /**
     * 用户检查
     * @param user
     */
    private void check(TUser user) {
        AssertUtil.isTrue(StringUtils.isEmpty(user.getUserName()),"用户名不能为空！");
        TUser temp = tUserDao.queryByName(user.getUserName());
        if (null!=user.getId()){
            //修改用户
            AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(user.getId())),"用户名已经存在！");
        }else {
            //添加用户
            AssertUtil.isTrue(null!= temp,"用户名已经存在");
        }
        AssertUtil.isTrue(null==user.getEmail(), "邮箱地址不能为为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone())||null==user.getPhone(), "⼿机号码为空或格式不正确！");
    }

    public void relaionUserRole (int userId,String roleIds){
        int count = userRoleDao.countById(userId);
        if (count>0){
            AssertUtil.isTrue(userRoleDao.deleteById(userId)!=count,"用户角色分配失败！");
        }
        //以下为修改角色
        if (org.apache.commons.lang3.StringUtils.isBlank(roleIds)){
            return;
        }
        List<TUserRole> list=new ArrayList<>();
        for (String s : roleIds.split(",")){
            TUserRole userRole =new TUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Integer.parseInt(s));
            list.add(userRole);
        }
        AssertUtil.isTrue(userRoleDao.insert2(list)!=list.size(),"用户角色绑定失败！");
    }
}
