package com.shsxt.dao;

import com.shsxt.po.TUser;
import com.shsxt.query.UserQuery;
import org.apache.ibatis.annotations.Param;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * (TUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-18 17:13:25
 */
public interface TUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUser queryById(Integer id);


    TUser queryByName(String name);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUser 实例对象
     * @return 对象列表
     */
    List<TUser> queryAll(TUser tUser);

    /**
     * 新增数据
     *
     * @param tUser 实例对象
     * @return 影响行数
     */
    int insert(TUser tUser);

    /**
     * 修改数据
     *
     * @param tUser 实例对象
     * @return 影响行数
     */
    int update(TUser tUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Map<String, Object>> queryByAllSales();

    List<TUser> queryByParams(UserQuery userQuery);

    int deleteById1(Integer[] ids);

}