$(function() {
	//初始化验证
	window.gt_captcha_obj = new window.Geetest({
		gt : "df6595b204a06069670b68b6e716ca45",
		product : "popup",
		https : false
	});
	gt_captcha_obj.appendTo("#js-GeetestDiv").bindOn('#js-submit');

	$("body").bind('keydown', function(event) {
		if (event.keyCode == 13) {
			$("#js-submit").trigger("click");
			//return false;
		}
	});
});

function checkLogin(){
	var flag = true;
	// 验证是否为空
	$("form input[type='text'],input[type='password']").each(function(i) {
		if ($.trim($(this).val()) == "") {
			if($(this).attr("type")=="text"){
				alert("请输入邮箱地址！");
			}else{
				alert("请输入密码！");
			}
			$(this).focus();
			flag = false;
			gt_captcha_obj.disable();
			return false;
		}

	});

	if (flag) {
		// 验证mail地址
		var mail = $.trim($("#js-mail").val());
		var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+(com|cn)$/);

		if (patten.test(mail)) {
			gt_captcha_obj.enable();
			
			gt_captcha_obj.onSuccess(function() {
				loginSuccess();
			});
			return true;
			
		} else {
			alert("邮箱格式不正确！");
			gt_captcha_obj.disable();
			return false;
		}
	}
}

// 登录成功
function loginSuccess() {
	$.ajax({
		type : "Get",
		url : "login.json",
		data : $("form[name='loginForm']").serialize(),
		async : false,
		error : function(request) {
			alert("登录失败！" + request);
		},
		success : function(data) {
			var jsonObj = eval("(" + data + ")");

			if (jsonObj.success) {
				window.location.href = "./index.html";
			} else {
				alert(jsonObj.result);
			}
		}
	});
	return false;
}