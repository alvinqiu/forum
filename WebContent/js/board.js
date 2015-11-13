$(function() {
	//获取所有版块
	$.ajax({
		url: "getAllModule.json",
		async: true,
		dataType : "json",
		error: function() {
			var txt = "获取版块失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var tagLen = data.moduleVOList.length;
			for (var i = 0; i < tagLen; i++) {
				var id = data.moduleVOList[i].id;
				var name = data.moduleVOList[i].name;
				var desc = data.moduleVOList[i].desc;

				$(".panel_detail_content").append("<div class='item'><a href='./list.html?moduleId=" + id + "&page=1'>" +
					"<div class='img'></div>" +
					"<div class='name'>" + name + "</div>" +
					"<div class='desc'>" + desc + "</div>" +
					"</a></div>");
				
				if ((i - 2) % 3 == 0) {
					$(".panel_detail_content").append("<div class='clearBoth'></div>");
				}
			}
			if (tagLen % 3 != 0) {
				for (var j = 0; j < (3 - (tagLen % 3)); j++) {
					$(".panel_detail_content").append("<div class='item'></div>");
				}
				$(".panel_detail_content").append("<div class='clearBoth'></div>");
			}
		}
	});
});