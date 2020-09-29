package com.shsxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.dao.TModuleDao;
import com.shsxt.dao.TPermissionDao;
import com.shsxt.dao.TRoleDao;
import com.shsxt.po.TPermission;
import com.shsxt.po.TRole;
import com.shsxt.query.RoleQuery;
import com.shsxt.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService {
    @Resource
    private TRoleDao roleDao;
    @Resource
    private TPermissionDao permissionDao;
    @Resource
    private TModuleDao moduleDao;
    /**
     * 角色查询
     * @param id
     * @return
     */
    public List<Map<String,Object>> queryByRole(Integer id){
        return roleDao.queryByRole(id);
    }

    /**
     * 角色查询列表
     * @param roleQuery
     * @return
     */
    public Map<String,Object> queryByLimit (RoleQuery roleQuery){
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<TRole> pageInfo=new PageInfo<>(roleDao.queryAllByLimit(roleQuery));
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 角色添加
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TRole role) {
        checkRole(role);
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        System.out.println(role.toString());
        AssertUtil.isTrue(roleDao.insert(role)!=1,"角色添加失败！");
    }

    /**
     * 角色更新
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(TRole role) {
        AssertUtil.isTrue(null==role.getId(),"角色不存在！");
        TRole temp = roleDao.queryById(role.getId());
        AssertUtil.isTrue(null==temp,"查找角色不存在！");
        checkRole(role);
        role.setUpdateDate(new Date());
        System.out.println(role.toString());
        AssertUtil.isTrue(roleDao.update(role)!=1,"角色更新失败！");
    }

    /**
     * 角色删除
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete (Integer id){
        AssertUtil.isTrue(null==id,"角色ID不存在！");
        TRole temp = roleDao.queryById(id);
        AssertUtil.isTrue(null==temp,"角色不存在！");
        AssertUtil.isTrue(roleDao.deleteById(id)!=1,"角色删除失败！");
    }

    /**
     * 角色检查
     * @param role
     */
    private void checkRole(TRole role) {
        AssertUtil.isTrue(null==role.getRoleName(),"角色名不能为空！");
        AssertUtil.isTrue(null==role.getRoleRemark(),"角色备注名不能为空！");
        TRole temp = roleDao.queryByName(role.getRoleName());
        if (null==role.getId()){
            AssertUtil.isTrue(null!=temp,"角色名已存在！");
        }else {
            AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(role.getId())),"角色名已存在！");
        }
    }

    /**
     * 授权添加、修改、删除
     * @param mids
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant (Integer[] mids ,Integer roleId){
        TRole temp = roleDao.queryById(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待授权用户不存在！");
        int count = permissionDao.countByRoleId(roleId);
        if (count>0){
            AssertUtil.isTrue(permissionDao.deleteById(roleId)!=count,"授权时删除旧记录失败！");
        }
        if (null!=mids&&mids.length>0){
            List<TPermission> list=new ArrayList<>();
            for (Integer mid:mids){
                TPermission permission=new TPermission();
                permission.setRoleId(roleId);
                permission.setModuleId(mid);
                permission.setAclValue(moduleDao.selectByMid(mid).getOptValue());
                list.add(permission);
            }
            AssertUtil.isTrue(permissionDao.insertBatch(list)!=list.size(),"用户授权失败！");
        }
    }
}
