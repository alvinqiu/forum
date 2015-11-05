$(function() {

	$.ajax({
		url : "checkLogin.json",
		async : false,
		success : function(data) {
			var jsonObj = eval("(" + data + ")");
			if (jsonObj.success) {
				if (!jsonObj.isAdmin) {
					var txt = "您还没有权限访问，请联系管理员！";
					window.wxc.xcConfirm(txt, "info", {
						onOk : function() {
							history.go(-1);
						},
						onClose : function() {
							history.go(-1);
						}
					});
				}
			} else {
				var txt = "请先登录！";
				window.wxc.xcConfirm(txt, "info", {
					onOk : function() {
						location.href = "./login.html";
					},
					onClose : function() {
						location.href = "./login.html";
					}
				});
			}
		}
	});

	$("#js-module").click(function() {
		location.href = "module.html";
	});

	$("#js-post").click(function() {
		location.href = "post.html";
	});

	$("#js-group").click(function() {
		location.href = "group.html";
	});
});