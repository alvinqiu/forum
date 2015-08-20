//注册成功
function registerSuccess(){
	$.ajax({
		type: "Get",	
        url:"register.json",
        data:$("form[name='registerForm']").serialize(),
        async: false,
        error: function(request) {
        	alert("注册失败！"+request);
        },
        success: function(data) {
        	var jsonObj=eval("("+data+")");
        	alert(jsonObj.result);
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
				 
				 //验证密码是否一致
				 var pass = $.trim($("#js-password").val());
				 var passConfirm = $.trim($("#js-password_confirm").val());
				 if(pass==passConfirm){
					 //注册成功
					 registerSuccess();
				 }
				 else{
					 alert("密码不一致！");
				 }
				 
			 }
			 else{
				 alert("邮箱格式不正确！");
			 }
		}
		
	}
}