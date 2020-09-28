layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 计划项数据展示
     */
    var  tableIns = table.render({
        elem: '#cusDevPlanList',
        url : ctx+'/cus_dev_plan/list?sid='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "cusDevPlanListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'planItem', title: '计划项',align:"center"},
            {field: 'exeAffect', title: '执行效果',align:"center"},
            {field: 'planDate', title: '执行时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#cusDevPlanListBar"}
        ]]
    });

    table.on('toolbar(cusDevPlans)',function (obj) {
        switch (obj.event) {
            case "add":
                test1();
                break;
            case "success":
                test2(2);
                break;
            case "failed":
                test2(3);
                break;
        }
    })

    function test2(devResult){
        var id=$("[name='id']").val();
        layer.confirm("确认执⾏当前操作？", {icon:3, title:"计划项维护"}, function (index){
            $.post({
                url: ctx + "/sale_chance/updateDevResult",
                data: {
                    "id" : id,
                    "devResult" : devResult
                },
                success:function (result) {
                    if (result.code==200){
                        layer.msg("修改成功!",{icon:6});
                        layer.close(index);
                        parent.location.reload();
                    }else {
                        layer.msg(result.msg,{icon:5});
                    }
                }
            })
        })
    }

    table.on("tool(cusDevPlans)", function(obj){
        var layEvent = obj.event;
        // 监听编辑事件
        if(layEvent === "edit") {
            test1(obj.data.id);
        }else if (layEvent === "del"){
            deleteById(obj.data.id);
        }
    });

    function deleteById(id) {
        layer.confirm("确定要删除这条记录吗？", {icon: 3, title:"计划项数据管理"}, function (index){
            layer.close(index);
            $.post({
                url: ctx + "/cus_dev_plan/delete",
                data: {id : id},
                success:function (result) {
                    if (result.code==200){
                        layer.msg("删除成功",{icon:6});
                        tableIns.reload();
                    }else {
                        layer.msg(result.msg,{icon:5});
                    }
                }
            })
        });
    }

    function test1(id) {
        var url = ctx +"/cus_dev_plan/index3?sid="+$("[name='id']").val();
        var title="计划项管理-添加计划项";
        if (id){
            url += "&id="+id;
            var title="计划项管理-更新计划项";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["500px","300px"],
            maxmin:true,
            content : url
        });
    }
});
