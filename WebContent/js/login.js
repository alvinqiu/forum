$(function() {
	$("input").bind('keydown',function(event) {
		if (event.keyCode == 13) {
			$("#js-submit").trigger("click");
		}
	});
});

// 登录成功
function loginSuccess(){
	$.ajax({
		type: "Get",	
        url:"login.json",
        data:$("form[name='loginForm']").serialize(),
        async: false,
        error: function(request) {
        	alert("登录失败！"+request);
        },
        success: function(data) {
        	var jsonObj=eval("("+data+")");
        	
        	if(jsonObj.success){
        		window.location.href="/forum/index.html";
        	}
        	else{
        		alert(jsonObj.result);        		
        	}
        }
     });
}

function gt_custom_ajax(result, selector, message) {
	if (result) {
		var challenge = selector(".geetest_challenge").value;
		var validate = selector(".geetest_validate").value;
		var seccode = selector(".geetest_seccode").value;
		
		var flag = true;
		//验证是否为空
		$("form :input").each(function(i){
			if($.trim($(this).val())==""){
				alert("表单不能为空！");
				$(this).focus();
				flag = false;
				return false;
			}
			
		});
		
		if(flag){
			//验证mail地址
			var mail = $.trim($("#js-mail").val());
			var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+(com|cn)$/);
			
			 if(patten.test(mail)) {
				loginSuccess();
			 }
			 else{
				 alert("邮箱格式不正确！");
			 }
			 return false;
		}
		
		/*$.ajax({
			url : "www.baidu.com",
			type : "post",
			data : {
				geetest_challenge : challenge,
				geetest_validate : validate,
				geetest_seccode : seccode
			},
			success : function() {
				alert(123);
			}
		})*/
		//当验证成功时，获取相应input的值，并做ajax验证请求
	}
}