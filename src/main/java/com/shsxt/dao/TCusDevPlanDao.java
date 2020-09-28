package com.shsxt.dao;

import com.shsxt.po.TCusDevPlan;
import com.shsxt.query.CusDevPlanQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TCusDevPlan)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-22 22:20:11
 */
public interface TCusDevPlanDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TCusDevPlan queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TCusDevPlan> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tCusDevPlan 实例对象
     * @return 对象列表
     */
    List<TCusDevPlan> queryAll(TCusDevPlan tCusDevPlan);

    /**
     * 新增数据
     *
     * @param tCusDevPlan 实例对象
     * @return 影响行数
     */
    int insert(TCusDevPlan tCusDevPlan);

    /**
     * 修改数据
     *
     * @param tCusDevPlan 实例对象
     * @return 影响行数
     */
    int update(TCusDevPlan tCusDevPlan);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteByid(Integer id);

    List<TCusDevPlan> queryByParams(CusDevPlanQuery cusDevPlanQuery);

    List<TCusDevPlan> queryBySid(@Param(value = "id") Integer id);

}