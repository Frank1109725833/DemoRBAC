package com.shsxt.dao;

import com.shsxt.po.TModule;
import com.shsxt.po.TreeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TModule)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-26 11:17:18
 */
public interface TModuleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TModule queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TModule> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tModule 实例对象
     * @return 对象列表
     */
    List<TModule> queryAll(TModule tModule);

    /**
     * 新增数据
     *
     * @param tModule 实例对象
     * @return 影响行数
     */
    int insert(TModule tModule);

    /**
     * 修改数据
     *
     * @param tModule 实例对象
     * @return 影响行数
     */
    int update(TModule tModule);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<TreeDto> queryAllModules();

    TModule selectByMid(Integer id);
}