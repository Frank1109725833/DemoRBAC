package com.shsxt.control;

import com.shsxt.control.base.BaseController;
import com.shsxt.control.base.ResultInfo;
import com.shsxt.dao.TCusDevPlanDao;
import com.shsxt.dao.TSaleChanceDao;
import com.shsxt.po.TCusDevPlan;
import com.shsxt.po.TSaleChance;
import com.shsxt.query.CusDevPlanQuery;
import com.shsxt.service.CusDevPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanControl extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;

    @Resource
    private TSaleChanceDao saleChanceDao;

    @Resource
    private TCusDevPlanDao cusDevPlanDao;

    @RequestMapping("index")
    public String  index(){
        return "cusDevPlan/cus_dev_plan";
    }

    @RequestMapping("index2")
    public String index2 (Integer sid, HttpServletRequest request){
        TSaleChance saleChance = saleChanceDao.queryById(sid);
        request.setAttribute("saleChance", saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    @RequestMapping("index3")
    public String index3 (Integer sid,Integer id, HttpServletRequest request){
        TCusDevPlan cusDevPlan = cusDevPlanDao.queryById(id);
        request.setAttribute("cusDevPlan",cusDevPlan);
        request.setAttribute("sid",sid);
        return "cusDevPlan/add_update";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CusDevPlanQuery cus){
        Map<String, Object> map = cusDevPlanService.queryById(cus);
        return map;
    }

    @RequestMapping("insert")
    @ResponseBody
    public ResultInfo insert(TCusDevPlan cusDevPlan){
        System.out.println(cusDevPlan);
        cusDevPlanService.insert(cusDevPlan);
        return success();
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(TCusDevPlan cusDevPlan){
        cusDevPlanService.update(cusDevPlan);
        return success();
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        cusDevPlanService.deleteById(id);
        return success();
    }
}
