layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on('submit(login)',function (data) {
        console.log(data.field);
        $.ajax({
            type:"post",
            url: ctx + "/user/userLogin",
            data: {
                "name" : data.field.username,
                "pwd" : data.field.password
            },
            success: function (result) {
                if (result.code==200)
                {
                    layer.msg("你现在的每一个决定都影响着未来！")
                    setTimeout(function () {
                        location.href = ctx + "/main";
                    },2000)
                        $.cookie("userIdB64",result.result.userIdb64);
                        $.cookie("userName",result.result.userName);
                        $.cookie("trueName",result.result.trueName);
                    if ($(":checkbox").prop("checked")){
                        $.cookie("userIdB64",result.result.userIdb64,{expires:7});
                        $.cookie("userName",result.result.userName,{expires: 7});
                        $.cookie("trueName",result.result.trueName,{expires: 7});
                        }
                }else {
                    layer.msg( result.msg , {icon:5});
                }
            }
        })
        return false;
    });
    
    
});