$(function() {
	$.ajax({
		url: "checkLogin.json",
		async: false,
		dataType : "json",
		success: function(data) {
			if (data.success) {
				if (!data.isAdmin) {
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
				dataType : "json",
				async: true,
				error: function() {
					var txt = "设置身份类型失败！";
					window.wxc.xcConfirm(txt, "error");
				},
				success: function(data) {
					if (data != null) {
						var txt;
						if (data.result < 0) {
							txt = "此用户名不存在！";
							window.wxc.xcConfirm(txt, "info");
						} else if (data.result > 0) {
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
		dataType : "json",
		error: function() {
			var txt = "获取非普通用户身份类型失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				for (var i = 0, tagLen = data.UserList.length; i < tagLen; i++) {

					var username = data.UserList[i].mail;
					var groupName = data.UserList[i].groupName;

					$(".list").append("<div>" + username + " --- " + groupName + "</div>");
				}
			}
		}
	});

	//获取身份类型
	$.ajax({
		url: "getAllGroup.json",
		async: true,
		dataType : "json",
		error: function() {
			var txt = "获取身份类型失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				for (var i = 0, tagLen = data.GroupList.length; i < tagLen; i++) {

					var id = data.GroupList[i].id;
					var code = data.GroupList[i].code;
					var name = data.GroupList[i].name;

					$("#js-group").append("<option value='" + id + "'>" + name + "</option>");
				}
			}
		}
	});
}