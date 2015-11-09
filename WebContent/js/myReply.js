$(function() {
	var page = getUrlParam('page');

	$.ajax({
		url: "getMyReply.json",
		async: true,
		data: {
			"page": page
		},
		dataType : "json",
		error: function() {
			var txt = "获取评论失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data !=null) {
				//清空div内容
				$(".panel_left_top").empty();

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

						var id = data.postList[i].id; //评论ID
						var parentId = data.postList[i].parentId; //帖子ID
						var content = "我的回复： " + data.postList[i].content; //回复摘要
						var parentContentSummary = data.postList[i].parentContentSummary; //帖子摘要
						var subject = data.postList[i].subject; //帖子标题
						var submitTime = formatDate(new Date(data.postList[i].submitTime.time)); //回复时间

						$(".panel_left_top").append("<div><a href='./detail.html?id=" + parentId + "#" + id + "anchor'>" +
							"<div class='subject'>" + subject + "</div>" +
							"<div class='submitTime'>" + submitTime + "</div>" +
							"<div class='parentContentSummary'>\"" + parentContentSummary + "...\"</div>" +
							"<div class='content'>" + content + "</div></a></div>");
					}

				} else {
					$(".panel_left_top").append("<div>暂无</div>");
				}
			}
		}
	});

});

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