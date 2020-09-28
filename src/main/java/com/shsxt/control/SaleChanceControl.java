package com.shsxt.control;

import com.shsxt.annotation.RequirePermission;
import com.shsxt.control.base.BaseController;
import com.shsxt.control.base.ResultInfo;
import com.shsxt.dao.TSaleChanceDao;
import com.shsxt.po.TSaleChance;
import com.shsxt.query.SaleChanceQuery;
import com.shsxt.service.SaleChanceService;
import com.shsxt.utils.CookieUtil;
import com.shsxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceControl extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private TSaleChanceDao saleChanceDao;

    /**
     * 营销机会列表查询接口
     * @param saleChanceQuery
     * @param flag
     * @param request
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    public Map<String,Object> test(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest request){
        return saleChanceService.test1(saleChanceQuery,flag,request);
    }

    @PostMapping("save")
    @ResponseBody
    public ResultInfo test1 (HttpServletRequest request, TSaleChance saleChance){
        String userName = CookieUtil.getCookieValue(request, "userName");
        int id = LoginUserUtil.releaseUserIdFromCookie(request);
        saleChance.setCreateMan(userName);
        saleChanceService.insert(saleChance,id);
        return success("营销机会数据添加成功！");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo test2 (TSaleChance saleChance){
        saleChanceService.update(saleChance);
        return success("营销机会数据修改成功！");
    }


    @RequestMapping("index")
    public String index (){
        return "saleChance/sale_chance";
    }

    @RequestMapping("index2")
    public String index2 (HttpServletRequest request,Integer id){
        if (id!=null){
            TSaleChance saleChance = saleChanceDao.queryById(id);
            request.setAttribute("salechance",saleChance);
        }
        return "saleChance/add_update";
    }

    @RequestMapping("queryBySales")
    @ResponseBody
    public List<Map<String, Object>> test3 (){
        List<Map<String, Object>> maps = saleChanceService.queryByAllSales();
        return maps;
    }

    @PostMapping("updateDevResult")
    @ResponseBody
    public ResultInfo updateDevResult (Integer id,Integer devResult){
        saleChanceService.updateDevResult(id,devResult);
        return success();
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo index3 (Integer[] ids){
        saleChanceService.deleteById(ids);
        return success();
    }
}
