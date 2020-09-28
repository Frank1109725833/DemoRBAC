package com.shsxt.service;

import com.shsxt.dao.TModuleDao;
import com.shsxt.dao.TPermissionDao;
import com.shsxt.po.TreeDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleService {
    @Resource
    private TModuleDao moduleDao;
    @Resource
    private TPermissionDao permissionDao;

    /**
     * 查询所有资源
     * @return
     */
    public List<TreeDto> queryAllModules (){
        return moduleDao.queryAllModules();
    }

    /**
     * 查询角色所拥有的资源
     * @param roleId
     * @return
     */
    public List<TreeDto> queryAllModules2 (Integer roleId){
        List<TreeDto> treeDtos = moduleDao.queryAllModules();
        List<Integer> list = permissionDao.queryByroleId(roleId);
        if (null!=list&&list.size()>0){
            treeDtos.forEach(treeDto -> {
                if (list.contains(treeDto.getId())){
                    treeDto.setChecked(true);
                    treeDto.setOpen(true);
                }
            });
        }
        return treeDtos;
    }
}
