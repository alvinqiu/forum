$(function() {
	//获取所有版块
	$.ajax({
		url: "getAllModule.json",
		async: true,
		error: function() {
			var txt = "获取版块失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var jsonObj = eval("(" + data + ")");
			var tagLen = jsonObj.moduleVOList.length;
			for (var i = 0; i < tagLen; i++) {
				var id = jsonObj.moduleVOList[i].id;
				var name = jsonObj.moduleVOList[i].name;
				var desc = jsonObj.moduleVOList[i].desc;

				$(".panel_detail_content").append("<div class='item'><a href='./list.html?moduleId=" + id + "&page=1'>" +
					"<div class='img'></div>" +
					"<div class='name'>" + name + "</div>" +
					"<div class='desc'>" + desc + "</div>" +
					"</a></div>");
			}
			if (tagLen % 3 != 0) {
				for (var j = 0; j < (3 - (tagLen % 3)); j++) {
					$(".panel_detail_content").append("<div class='item'></div>");
				}
			}
		}
	});
});