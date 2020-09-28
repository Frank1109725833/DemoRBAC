package com.shsxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.dao.TSaleChanceDao;
import com.shsxt.dao.TUserDao;
import com.shsxt.enums.DevResult;
import com.shsxt.enums.StateStatus;
import com.shsxt.po.TSaleChance;
import com.shsxt.po.TUser;
import com.shsxt.query.SaleChanceQuery;
import com.shsxt.utils.AssertUtil;
import com.shsxt.utils.LoginUserUtil;
import com.shsxt.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {
    @Resource
    private TSaleChanceDao saleChanceDao;
    @Resource
    private TUserDao userDao;

    /**
     * 角色列表
     * @param query
     * @param flag
     * @param request
     * @return
     */
    public Map<String, Object> test1(SaleChanceQuery query, Integer flag, HttpServletRequest request) {
        //存在flag且为1,则为客户开发计划
        if (null!=flag && "1".equals(flag)){
            Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(id);
        }
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<TSaleChance> pageInfo = new PageInfo<>(saleChanceDao.queryByTest1(query));
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TSaleChance saleChance, Integer id) {
        AssertUtil.isTrue(id == null, "用户不存在");
        TUser user = userDao.queryById(id);
        AssertUtil.isTrue(null == user, "用户不存在");
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceDao.insert(saleChance) != 1, "添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(TSaleChance saleChance) {
        AssertUtil.isTrue(saleChance.getId() == null, "记录不存在");
        TSaleChance temp = saleChanceDao.queryById(saleChance.getId());
        AssertUtil.isTrue(null == temp, "记录不存在");
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        //修改前为空,修改后不为空
        if (StringUtils.isNotBlank(saleChance.getAssignMan()) && StringUtils.isBlank(temp.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        } else if (StringUtils.isBlank(saleChance.getAssignMan()) && StringUtils.isNotBlank(temp.getAssignMan())) {
            //修改前不为空，修改后为空
            saleChance.setAssignMan(null);
            saleChance.setAssignTime(null);
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        } else {
            //修改前后都不为空
            //修改前后不为同一人
            if (!(saleChance.getAssignMan().equals(temp.getAssignMan()))) {
                saleChance.setAssignTime(new Date());
            }else {
                //修改前后为同一人
                saleChance.setAssignTime(temp.getAssignTime());
            }
            saleChance.setUpdateDate(new Date());
        }
        AssertUtil.isTrue(saleChanceDao.update(saleChance)!=1,"修改操作失败！");
    }

    public List<Map<String, Object>> queryByAllSales ()
    {
        return userDao.queryByAllSales();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById (Integer[] ids) {
        AssertUtil.isTrue(ids==null||ids.length<1,"请选择要删除的数据!");
        AssertUtil.isTrue(saleChanceDao.deleteById(ids)!=ids.length,"删除操作失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDevResult (Integer id,Integer devResult){
        AssertUtil.isTrue(null==id,"ID不存在！");
        TSaleChance saleChance = saleChanceDao.queryById(id);
        AssertUtil.isTrue(null==saleChance,"ID不存在！");
        AssertUtil.isTrue(null==devResult,"状态异常！");
        saleChance.setDevResult(devResult);
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceDao.update(saleChance)!=1,"更改状态失败！");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "客户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "联系人不能为空！");
        AssertUtil.isTrue(linkPhone == null, "手机号不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "手机号不合法！");
    }
}
