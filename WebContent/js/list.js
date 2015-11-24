$(function() {
	var moduleId = getUrlParam('moduleId');
	var page = getUrlParam('page');

	$("#js-addPost").click(checkLogin);

	$.ajax({
		url: "getAllPost.json",
		data: {
			"page": page,
			"moduleId": moduleId
		},
		dataType : "json",
		async: true,
		success: function(data) {

			//清空div内容
			$(".panel_left_top").empty();
			$(".panel_left_common").empty();

			var groupId = data.GroupId;
			var total = data.Total;
			var url = getUrl();

			if (total != 0) {
				if (page <= total && page > 1) {
					$(".pg").append("<a class='prev' href='" + url + "?moduleId=" + moduleId + "&page=" + (parseInt(page) - 1) + "'><em class='previcon'></em></a>");
				}
				for (var j = 1; j <= total; j++) {
					if (j == page) {
						$(".pg").append("<strong>" + j + "</strong>");
					} else {
						$(".pg").append("<a href='" + url + "?moduleId=" + moduleId + "&page=" + j + "'>" + j + "</a>");
					}
				}
				if (total > 1 && total != page) {
					$(".pg").append("<a class='next' href='" + url + "?moduleId=" + moduleId + "&page=" + (parseInt(page) + 1) + "'><em class='nxticon'></em></a>");
				}


				for (var i = 0, tagLen = data.postList.length; i < tagLen; i++) {

					var id = data.postList[i].id;
					var subject = data.postList[i].subject;
					var formatTime = data.postList[i].formatTime;
					var name = data.postList[i].name;
					var commentCount = data.postList[i].commentCount;
					var highLight = data.postList[i].highLight;
					var top = data.postList[i].top;

					if (top != 0) {
						if (highLight != 0) {
							if (groupId < 3) {
								$(".panel_left_top").append("<div class='hightlight'><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div>" +
									"<div class='operate'>" +
									"<a href='javascript:void(0);' onclick='setHighLight(" + id + ")'>加精</a>" +
									"<a href='javascript:void(0);' onclick='setTop(" + id + ")'>置顶</a>" +
									"<a href='javascript:void(0);' onclick='del(" + id + ")'>删除</a></div></div>");
							} else {
								$(".panel_left_top").append("<div class='hightlight'><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
							}
						} else {
							if (groupId < 3) {
								$(".panel_left_top").append("<div><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div>" +
									"<div class='operate'>" +
									"<a href='javascript:void(0);' onclick='setHighLight(" + id + ")'>加精</a>" +
									"<a href='javascript:void(0);' onclick='setTop(" + id + ")'>置顶</a>" +
									"<a href='javascript:void(0);' onclick='del(" + id + ")'>删除</a></div></div>");
							} else {
								$(".panel_left_top").append("<div><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
							}
						}
					} else {
						if (highLight != 0) {
							if (groupId < 3) {
								$(".panel_left_common").append("<div class='hightlight'><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div>" +
									"<div class='operate'>" +
									"<a href='javascript:void(0);' onclick='setHighLight(" + id + ")'>加精</a>" +
									"<a href='javascript:void(0);' onclick='setTop(" + id + ")'>置顶</a>" +
									"<a href='javascript:void(0);' onclick='del(" + id + ")'>删除</a></div></div>");
							} else {
								$(".panel_left_common").append("<div class='hightlight'><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
							}
						} else {
							if (groupId < 3) {
								$(".panel_left_common").append("<div><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div>" +
									"<div class='operate'>" +
									"<a href='javascript:void(0);' onclick='setHighLight(" + id + ")'>加精</a>" +
									"<a href='javascript:void(0);' onclick='setTop(" + id + ")'>置顶</a>" +
									"<a href='javascript:void(0);' onclick='del(" + id + ")'>删除</a></div></div>");
							} else {
								$(".panel_left_common").append("<div><a href='./detail.html?id=" + id + "'>" +
									"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
							}
						}
					}
				}
			} else {
				$(".panel_left_top").append("<div>暂无</div>");
				$(".panel_left_common").append("<div>暂无</div>");
			}
		}
	});
});

//查询是否登录
function checkLogin() {
	$.ajax({
		async : false,
		url : "checkLogin.json",
		dataType:"json",
		success:function(data){
			if(data.success){
				location.href = "./addPost.html";
			}else{
				var txt = "请先登录,确定要跳转至登录页吗？";
				window.wxc.xcConfirm(txt, "confirm", {
					onOk : function() {
						window.location.replace("./login.html");
					}
				});
				return false;
			}
		}
	});
}

//设置高亮（加精）
function setHighLight(id) {
	$.ajax({
		url: "setHighLight.json",
		data: {
			"Id": id
		},
		dataType : "json",
		error: function() {
			var txt = "设置高亮失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				var txt;
				if (data.success) {
					txt = "设置高亮成功！";
					window.wxc.xcConfirm(txt, "success", {
						onOk : function() {
							location.reload();
						}
					});
				} else {
					txt = "设置高亮失败！";
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}

//设置置顶
function setTop(id) {
	$.ajax({
		url: "setTop.json",
		data: {
			"Id": id
		},
		dataType : "json",
		error: function() {
			var txt = "设置置顶失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				var txt;
				if (data.success) {

					txt = "设置置顶成功！";
					window.wxc.xcConfirm(txt, "success", {
						onOk : function() {
							location.reload();
						}
					});
				} else {
					txt = "设置置顶失败！";
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}

//删除帖子
function del(id) {
	$.ajax({
		url: "del.json",
		data: {
			"Id": id
		},
		dataType : "json",
		error: function() {
			var txt = "删除失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				var txt;
				if (data.success) {
					txt = "删除成功！";
					window.wxc.xcConfirm(txt, "success", {
						onOk : function() {
							location.reload();
						}
					});
				} else {
					txt = "删除失败！";
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}

//获取完整url（除参数外）
function getUrl() {
	return window.location.origin + window.location.pathname;
}

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return unescape(r[2]);
	return null; //返回参数值
}