$(function() {
	var page = getUrlParam('page');

	$.ajax({
		url: "getMyPost.json",
		async: true,
		data: {
			"page": page
		},
		dataType : "json",
		error: function() {
			var txt = "获取帖子失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				//清空div内容
				$(".panel_left_top").empty();
				$(".panel_left_common").empty();

				var groupId = data.GroupId;
				var total = data.Total;
				var url = getUrl();

				if (total != 0) {
					if (page <= total && page > 1) {
						$(".pg").append("<a class='prev' href='" + url + "?page=" + (parseInt(page) - 1) + "'><em class='previcon'></em></a>");
					}
					for (var j = 1; j <= total; j++) {
						if (j == page) {
							$(".pg").append("<strong>" + j + "</strong>");
						} else {
							$(".pg").append("<a href='" + url + "?page=" + j + "'>" + j + "</a>");
						}
					}
					if (total > 1 && total != page) {
						$(".pg").append("<a class='next' href='" + url + "?page=" + (parseInt(page) + 1) + "'><em class='nxticon'></em></a>");
					}

					for (var i = 0, tagLen = data.postList.length; i < tagLen; i++) {

						var id = data.postList[i].id;
						var subject = data.postList[i].subject;
						var formatTime = data.postList[i].formatTime;
						var name = data.postList[i].name;
						var commentCount = data.postList[i].commentCount;
						var type = data.postList[i].type;

						//待审核
						if (type > 2) {
							$(".panel_left_top").append("<div><a href='./detail.html?id=" + id + "'>" +
								"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
						} else { //已审核
							$(".panel_left_common").append("<div><a href='./detail.html?id=" + id + "'>" +
								"<div class='subject'>" + subject + "</div></a><div class='name'>" + name + "</div></div>");
						}
					}

				} else {
					$(".panel_left_top").append("<div>暂无</div>");
					$(".panel_left_common").append("<div>暂无</div>");
				}
			}
		}
	});

});

// 获取完整url（除参数外）
function getUrl() {
	return window.location.origin + window.location.pathname;
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}