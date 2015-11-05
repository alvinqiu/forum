$(function() {
	$.ajax({
		url: "checkLogin.json",
		async: false,
		success: function(data) {
			var jsonObj = eval("(" + data + ")");
			if (jsonObj.success) {
				if (!jsonObj.isAdmin) {
					var txt = "您还没有权限访问，请联系管理员！";
					window.wxc.xcConfirm(txt, "info", {
						onOk : function() {
							history.go(-1)
						},
						onClose : function() {
							history.go(-1)
						}
					});

				} else {
					getData();
				}
			} else {
				var txt = "请先登录！";
				window.wxc.xcConfirm(txt, "info", {
					onOk : function() {
						location.href = "./login.html"
					},
					onClose : function() {
						location.href = "./login.html"
					}
				});
			}
		}
	});

	$("#js-setGroup").click(function() {
		var name = $.trim($("#js-username").val());
		if (name != "") {
			$.ajax({
				url: "setGroup.json",
				data: {
					"username": name,
					"groupid": $("#js-group").val()
				},
				async: true,
				error: function() {
					var txt = "设置身份类型失败！";
					window.wxc.xcConfirm(txt, "error");
				},
				success: function(data) {
					if (data != "") {
						var jsonObj = eval("(" + data + ")");
						var txt;
						if (jsonObj.result < 0) {
							txt = "此用户名不存在！";
							window.wxc.xcConfirm(txt, "info");
						} else if (jsonObj.result > 0) {
							txt = "设置成功！";
							window.wxc.xcConfirm(txt, "success", {
								onOk : function() {
									location.reload()
								}
							});
						} else {
							txt = "设置失败！";
							window.wxc.xcConfirm(txt, "info");
						}
					}
				}
			});
		} else {
			var txt = "请输入用户名！";
			window.wxc.xcConfirm(txt, "info");
		}
	});
});

function getData() {
	//获取所有非普通用户
	$.ajax({
		url: "getAllGroupExceptUser.json",
		async: true,
		error: function() {
			var txt = "获取非普通用户身份类型失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != "") {
				var jsonObj = eval("(" + data + ")");

				for (var i = 0, tagLen = jsonObj.UserList.length; i < tagLen; i++) {

					var username = jsonObj.UserList[i].mail;
					var groupName = jsonObj.UserList[i].groupName;

					$(".list").append("<div>" + username + " --- " + groupName + "</div>");
				}
			}
		}
	});

	//获取身份类型
	$.ajax({
		url: "getAllGroup.json",
		async: true,
		error: function() {
			var txt = "获取身份类型失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != "") {
				var jsonObj = eval("(" + data + ")");

				for (var i = 0, tagLen = jsonObj.GroupList.length; i < tagLen; i++) {

					var id = jsonObj.GroupList[i].id;
					var code = jsonObj.GroupList[i].code;
					var name = jsonObj.GroupList[i].name;

					$("#js-group").append("<option value='" + id + "'>" + name + "</option>");
				}
			}
		}
	});
}