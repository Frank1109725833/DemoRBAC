layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $("#closeBtn").click(function () {
        // 先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        // 再执行关闭
        parent.layer.close(index);
    });

    form.on('submit(addOrUpdateCusDevPlan)',function (data) {
        var index = layer.msg("数据提交中,请稍后...",{
            icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        // 请求的地址
        var url = ctx + "/cus_dev_plan/insert";

        if ($("[name='id']").val()){
            url= ctx + "/cus_dev_plan/update";
        }
        // 请求的参数
        var param = data.field;
        // 发送ajax请求
        $.post(url, param, function (result) {
            // 判断结果
            if (result.code == 200) {
                // 提示用户
                layer.msg("操作成功！",{icon:6});
                // 关闭加载层
                layer.close(index);
                // 关闭所有的iframe层
                layer.closeAll("iframe");
                // 刷新页面
                parent.location.reload();
            } else {
                layer.msg(result.msg, {icon:5});
            }
        });
        return false; // 阻止表单提交
    });
});