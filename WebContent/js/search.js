$(function(){
	var kw = getUrlParam('kw');
	var page = getUrlParam('page');
	$("#js_search_content").val(kw);
	
	$.ajax({
		url: "search.json",
		async: true,
		data : {
			"kw" : kw,
			"page" : page
		},
		error: function() {
			alert("查询帖子失败！");
		},
		success: function(data) {
			if (data != "") {
				//清空div内容
				$(".content").empty();

				var jsonObj = eval("(" + data + ")");

				var total = jsonObj.Total;
				var url = getUrl();

				if (total != 0) {
					if (page <= total && page > 1) {
						$(".pg").append("<a class='prev' href='" + url + "?kw=" + kw + "&page=" + (parseInt(page) - 1) + "'><em class='previcon'></em></a>");
					}
					for (var j = 1; j <= total; j++) {
						if (j == page) {
							$(".pg").append("<strong>" + j + "</strong>");
						} else {
							$(".pg").append("<a href='" + url + "?kw=" + kw + "&page=" + j + "'>" + j + "</a>");
						}
					}
					if (total > 1 && total != page) {
						$(".pg").append("<a class='next' href='" + url + "?kw=" + kw + "&page=" + (parseInt(page) + 1) + "'><em class='nxticon'></em></a>");
					}

					for (var i = 0, tagLen = jsonObj.postList.length; i < tagLen; i++) {

						var id = jsonObj.postList[i].id;
						var subject = jsonObj.postList[i].subject;
						var submitTime = formatDate(new Date(jsonObj.postList[i].submitTime.time));
						var name = jsonObj.postList[i].name;
						var commentCount = jsonObj.postList[i].commentCount;
						var moduleName = jsonObj.postList[i].moduleName;

						$(".content").append("<div><a href='./detail.html?id=" + id + "'>" +
									"<label class='subject'>" + subject + "</label></a>" +
									"<label class='module'>" + moduleName + "</label>" +
									"<label class='author'>" + name + "</label>" +
									"<label class='time'>" + submitTime + "</label>" +
									"<label class='comments'>" + commentCount + "</label></div>");	
					}

				} else {
					$(".content").append("<div>对不起，没有找到匹配结果。</div>");
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

//获取完整url（除参数外）
function getUrl(){
	return window.location.origin+window.location.pathname;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}