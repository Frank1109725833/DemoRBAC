package com.shsxt.dao;

import com.shsxt.po.TUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-25 19:20:52
 */
public interface TUserRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUserRole queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TUserRole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUserRole 实例对象
     * @return 对象列表
     */
    List<TUserRole> queryAll(TUserRole tUserRole);

    /**
     * 新增数据
     *
     * @param tUserRole 实例对象
     * @return 影响行数
     */
    int insert(TUserRole tUserRole);

    /**
     * 修改数据
     *
     * @param tUserRole 实例对象
     * @return 影响行数
     */
    int update(TUserRole tUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

    int countById(Integer id);

    int insert2(List<TUserRole> list);

    int delete2(Integer[] id);
}