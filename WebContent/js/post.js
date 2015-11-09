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
							history.go(-1);
						},
						onClose : function() {
							history.go(-1);
						}
					});
				} else {
					getAllPostByHold();
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

});

function getAllPostByHold() {
	$.ajax({
		url: "getAllPostByHold.json",
		async: true,
		dataType : "json",
		error: function(request) {
			var txt = "获取待审核帖子列表失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				for (var i = 0, tagLen = data.postVOList.length; i < tagLen; i++) {
					var id = data.postVOList[i].id;
					var subject = data.postVOList[i].subject;
					var submitTime = formatDate(new Date(data.postVOList[i].submitTime.time));
					var content = data.postVOList[i].content;

					$(".list").append("<div><a href='javascript:void(0);'>#" + id + " 标题：" + subject + " 时间：" + submitTime + "</a><input type='hidden' value='" + content + "' />" +
						"<input type='button' value='通过' /><input type='hidden' value='" + id + "' />" +
						"</div>");

				}

				$("a").click(function() {
					var content = $.trim($(this).next().val());
					$(".detail").empty();
					$(".detail").append("<div>" + content + "</div>");
				});

				$("input[type='button']").click(function() {
					var id = $(this).next().val();
					$.ajax({
						url: "passPost.json",
						data: {
							"id": id
						},
						dataType : "json",
						error: function(request) {
							var txt = "审核帖子失败！";
							window.wxc.xcConfirm(txt, "error");
						},
						success: function(data) {
							if (data != null) {
								var txt;
								if (data.success) {
									txt = "审核帖子成功！";
									window.wxc.xcConfirm(txt, "success", {
										onOk: function(){
											location.reload();
										}
									});
								} else {
									txt = "审核帖子失败！";
									window.wxc.xcConfirm(txt, "info");
								}
							}
						}
					});

				});
			}
		}
	});
}

function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":"
			+ second;
}