$(function() {
	$("body").bind('keydown', function(event) {
		if (event.keyCode == 13 && $(".xcConfirm").length == 0) {
			checkRegister(event);
		}
	});
});

// 验证
function checkRegister(event) {
	disableBtn();
	
	if(event) {
		event.stopPropagation();
	}
	
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
				onOk: function() {
					_this.focus();
				}
			});
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
				//  验证成功
				registerSuccess();
			} else {
				var txt = "密码不一致！";
				window.wxc.xcConfirm(txt, "info", {
					onOk: function() {
						$("#js-password_confirm").focus();
					}
				});
				flag = false;
			}
		} else {
			var txt = "邮箱格式不正确！";
			window.wxc.xcConfirm(txt, "info", {
				onOk: function() {
					$("#js-mail").focus();
				}
			});
			flag = false;
		}
	}
	
	if (!flag) { enableBtn(); }
	
	return flag;
}

// 验证成功  注册
function registerSuccess() {
	$.ajax({
		type: "Get",
		url: "register.json",
		data: $("form[name='registerForm']").serialize(),
		async: false,
		dataType: "json",
		error: function(request) {
			var txt = "注册失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var txt = data.result;
			window.wxc.xcConfirm(txt, "info", {
				onOk: function() {
					if(data.success){
						location.href = "./login.html";
					}
				}
			});
		},
		complete: function() {
			enableBtn();
		}
	});
}

var disableBtn = function(){
	$("#js-submit").attr({
		"disabled" : true,
		"class" : "submit_disabled"
	});
}

var enableBtn = function(){
	$("#js-submit").attr({
		"disabled" : false,
		"class" : "submit"
	});
}