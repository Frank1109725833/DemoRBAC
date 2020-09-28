package com.shsxt.service;

import com.shsxt.dao.TPermissionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService {
    @Resource
    private TPermissionDao permissionDao;
    
    
    public List<String> queryPermissions(Integer id){
        return permissionDao.queryUserRolePerssiom(id);
    }
}
