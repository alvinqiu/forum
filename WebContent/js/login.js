$(function() {
	//初始化验证
	window.gt_captcha_obj = new window.Geetest({
		gt: "df6595b204a06069670b68b6e716ca45",
		product: "popup",
		https: false
	});
	gt_captcha_obj.appendTo("#js-GeetestDiv").bindOn('#js-submit');

	$("body").bind('keydown', function(event) {
		if (event.keyCode == 13) {
			$("#js-submit").trigger("click");
			//return false;
		}
	});
});

function checkLogin() {
	var flag = true;
	// 验证是否为空
	$("form input[type='text'],input[type='password']").each(function(i) {
		var _this = $(this);
		if ($.trim(_this.val()) == "") {
			var txt;
			if (_this.attr("type") == "text") {
				txt = "请输入邮箱地址！";
			} else {
				txt = "请输入密码！";
			}
			window.wxc.xcConfirm(txt, "info", {
				onOk : function() {
					_this.focus();
				}
			});
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
			var txt = "邮箱格式不正确！";
			window.wxc.xcConfirm(txt, "info", {
				onOk : function() {
					$("#js-mail").focus();
				}
			});

			gt_captcha_obj.disable();
			return false;
		}
	}
}

// 登录成功
function loginSuccess() {
	$.ajax({
		type: "Get",
		url: "login.json",
		data: $("form[name='loginForm']").serialize(),
		async: false,
		error: function(request) {
			var txt = "登录失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var jsonObj = eval("(" + data + ")");

			if (jsonObj.success) {
				window.location.href = "./index.html";
			} else {
				var txt = jsonObj.result;
				window.wxc.xcConfirm(txt, "info");
			}
		}
	});
	return false;
}