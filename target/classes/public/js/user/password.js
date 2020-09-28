layui.use(['form','layer','jquery','jquery_cookie'],function () {
    var  form =layui.form,
          layer =layui.layer,
            $    =layui.jquery;
            $    =layui.jquery_cookie($);

    form.on('submit(saveBtn)',function (data) {
        console.log(data.field);
        $.ajax({
            type:"put",
            url: ctx +"/user/userPassword",
            data:{
                "originalPwd" : data.field.old_password,
                "newPwd" : data.field.new_password,
                "repeatPwd" : data.field.again_password
            },
            success : function (result) {
                if (result.code==200)
                {
                    layer.msg("修改成功，系统将于2秒后自动退出",{icon:6},function () {
                        $.removeCookie("userIdB64",{domain:"sad",path:"/sb"});
                        $.removeCookie("userName",{domain:"sad",path:"/sb"});
                        $.removeCookie("trueName",{domain:"sad",path:"/sb"});
                        parent.location.href = ctx + "/index";
                    });
                }else {
                    layer.msg(result.msg,{icon:5});
                }
            }
        })
    });
})