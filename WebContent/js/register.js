// 验证成功  注册
function registerSuccess() {
	$.ajax({
		type: "Get",
		url: "register.json",
		data: $("form[name='registerForm']").serialize(),
		async: false,
		dataType : "json",
		error: function(request) {
			var txt = "注册失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var txt = data.result;
			window.wxc.xcConfirm(txt, "info", {
				onOk : function() {
					location.href = "./login.html";
				}
			});
		}
	});
}

//按钮触发  进行简单验证
$("#js-submit").click(function() {
	var flag = true;
	// 验证是否为空
	$("form :input").each(function(i) {
		if ($.trim($(this).val()) == "") {
			var txt = "表单不能为空！";
			window.wxc.xcConfirm(txt, "info");
			$(this).focus();
			flag = false;
			return false;
		}

	});

	if (flag) {
		// 验证mail地址
		var mail = $.trim($("#js-mail").val());
		var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+(com|cn)$/);

		if (patten.test(mail)) {
			// 验证密码是否一致
			var pass = $.trim($("#js-password").val());
			var passConfirm = $.trim($("#js-password_confirm").val());
			if (pass == passConfirm) {
				// 注册成功
				registerSuccess();
			} else {
				var txt = "密码不一致！";
				window.wxc.xcConfirm(txt, "info");

				$("#js-password_confirm").focus();
			}

		} else {
			var txt = "邮箱格式不正确！";
			window.wxc.xcConfirm(txt, "info");
			$("#js-mail").focus();
		}
	}
	return false;
});