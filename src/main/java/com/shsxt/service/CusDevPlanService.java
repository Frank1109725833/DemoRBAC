package com.shsxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.dao.TCusDevPlanDao;
import com.shsxt.po.TCusDevPlan;
import com.shsxt.query.CusDevPlanQuery;
import com.shsxt.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CusDevPlanService {
    @Resource
    private TCusDevPlanDao cusDevPlanDao;

    public Map<String,Object> queryById (CusDevPlanQuery cusDevPlanQuery){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        PageInfo<TCusDevPlan> pageInfo=new PageInfo<>(cusDevPlanDao.queryByParams(cusDevPlanQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert (TCusDevPlan cusDevPlan) {
        checkParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanDao.insert(cusDevPlan)!=1,"计划项内容添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update (TCusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null==cusDevPlan.getId(),"计划项内容ID不存在！");
        AssertUtil.isTrue(null==cusDevPlanDao.queryById(cusDevPlan.getId()),"计划项内容ID不存在！");
        checkParams(cusDevPlan);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanDao.update(cusDevPlan)!=1,"计划项内容添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById (Integer id){
        TCusDevPlan cusDevPlan = cusDevPlanDao.queryById(id);
        AssertUtil.isTrue(null==id||null==cusDevPlan,"计划项内容ID不存在！");
        AssertUtil.isTrue(cusDevPlanDao.deleteByid(id)!=1,"计划项内容删除失败！");
    }

    private void checkParams(TCusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null==cusDevPlan.getSaleChanceId(),"营销机会ID不存在！");
        AssertUtil.isTrue(null==cusDevPlanDao.queryBySid(cusDevPlan.getSaleChanceId()),"营销机会ID不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容为空！");
        AssertUtil.isTrue(null==cusDevPlan.getPlanDate(),"计划项时间为空！");
    }
}
