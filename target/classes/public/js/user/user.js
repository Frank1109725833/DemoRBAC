layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * 用户列表展示
     */
    var  tableIns = table.render({
        elem: '#userList',
        url : ctx + '/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '用户电话', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    $(".search_btn").click(function () {
        table.reload('userListTable', {
            where: { //设定异步数据接⼝的额外参数，任意设
                userName: $("[name='userName']").val(),
                phone:$("[name='phone']").val(),
                email:$("[name='email']").val()
            }
            ,page: {
                curr: 1 // 重新从第 1 ⻚开始
            }
        }); // 只重载数据
    });

    table.on('toolbar(users)', function(obj){
        var check=table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                // 点击添加按钮，打开添加营销机会的对话框
                openDialog();
                break;
            case 'del':
                deleteByIds(check.data);
                break;
        };
    });

    table.on('tool(users)', function(obj){
        switch(obj.event){
            case 'edit':
                // 点击添加按钮，打开添加营销机会的对话框
                openDialog(obj.data.id);
                break;
            case 'del':
                deleteById(obj.data.id);
                break;
        };
    });

    function openDialog(id) {
        var title = "<h2>用户管理 - 用户添加</h2>";
        var url = ctx + "/user/index2";
        if (id!=null&&id!=''){
            var title = "<h2>用户管理 - 用户修改</h2>";
            url += "?id=" + id;
        }
        layui.layer.open({
            title:title,
            type:2,
            content: url,
            area:["650px","400px"],
            maxmin:true
        });
    }

    function deleteByIds(list01){
        if (list01.length<1){
            layer.msg("请选择要删除的记录数！",{icon:5});
        }else {
            layer.confirm("您确定要删除选中的记录吗？",{
                btn:["确认","取消"],
            },function (index) {
                layer.close(index);
                var ids="ids=";
                for (var i=0; i<list01.length ; i++){
                    var user = list01[i];
                    //     // 判断是否是最后一个元素
                    if (i < list01.length - 1) {
                        ids += user.id + '&ids=';
                    } else {
                        ids += user.id;
                    }
                }
                $.ajax({
                    type:"post",
                    url: ctx + "/user/delete",
                    data: ids,
                    success:function (result) {
                        if (result.code==200){
                            layer.msg("批量删除成功！",{icon:6});
                            tableIns.reload();
                        }else {
                            layer.msg(result.msg,{icon:5});
                        }
                    }
                })
            });
        }
    }

    function deleteById(id) {
        layer.confirm("确定要删除这条记录吗？", {icon: 3, title:"用户数据管理"}, function (index){
            layer.close(index);
            $.post({
                url: ctx + "/user/delete",
                data: {ids: id},
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


});