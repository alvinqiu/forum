$(function() {
	//实例化UEditor编辑器
	var ue = UE.getEditor('editor', {
		toolbars: [
 			[ 'undo', 'cleardoc' ]
		],
		initialFrameWidth: 790,
		initialFrameHeight: 200
	});

	// 初始化验证
	window.gt_captcha_obj = new window.Geetest({
		gt: "df6595b204a06069670b68b6e716ca45",
		product: "popup",
		https: false
	});
	gt_captcha_obj.appendTo("#js-GeetestDiv").bindOn('#js-submit');

	var id = getUrlParam('id');
	
	// 获取帖子
	getPost(id);

	// 获取评论
	getComment(id);
	
});

var getPost = function(id){
	$.ajax({
		url: "getPostById.json",
		data: {
			"id": id
		},
		async: true,
		dataType : "json",
		success: function(data) {
			var obj = $(".panel_left_main");
			var id, subject, formatTime, content, groupId, name, commentCount, praise;

			id = data.PostVO.id;
			subject = data.PostVO.subject;
			formatTime = data.PostVO.formatTime;
			content = data.PostVO.content;
			groupId = data.GroupId;
			name = data.PostVO.name; // 昵称
			commentCount = data.PostVO.commentCount; // 回复数
			praise = data.PostVO.praise; // 点赞数
			checkPraise = data.PostVO.checkPraise; // 是否已点赞

			obj.append("<div class='subject'>" + subject + "</div>");
			obj.append("<div class='name'>" + name + "</div>");
			obj.append("<div class='submitTime'>" + formatTime + "</div>");
			if (commentCount != 0) {
				obj.append("<div class='commentCount'><label>" + commentCount + "</label></div>");
			}
			obj.append("<div class='content'>" + content + "</div>");

			//点赞
			if (checkPraise) {
				obj.append("<div class='praiseAnother'><label>" + praise + "</label></div>");
			} else {
				obj.append("<div class='praise' onclick='praise(" + id + ")'><label>" + praise + "</label></div>");
			}

			if (groupId < 3) {
				//删除按钮
				$(".panel_left_main").append("<div class='delBtn'><a href='javascript:void(0);'onclick='del(" + id + ")'>删除</a></div>");
			}

			//右侧用户名称
			$(".panel_right_user_name").html(name);
		}
	});
}

var getComment = function(id){
	$.ajax({
		url: "getComment.json",
		data: {
			"id": id
		},
		dataType : "json",
		async: true,
		success: function(data) {
			var id, subject, formatTime, content, parentContentSummary, name, floor;
			$(".panel_left_comment").empty();
			for (var i = 0, tagLen = data.postVOList.length; i < tagLen; i++) {

				id = data.postVOList[i].id;
				subject = data.postVOList[i].subject;
				formatTime = data.postVOList[i].formatTime;
				content = data.postVOList[i].content;
				parentContentSummary = data.postVOList[i].parentContentSummary;
				name = data.postVOList[i].name;

				//评论楼层
				floor = ((i + 1) == 1) ? "沙发" : "" + (i + 1) + "楼";

				$(".panel_left_comment").append("<div id='" + id + "anchor' name='" + id + "anchor'>" +
					"<div class='commentName'>" + name + "</div>" +
					"<div class='commentSubmitTime'>" + formatTime + "</div>" +
					"<div class='commentFloor'>" + floor + "</div>" +
					//"<div class='commentSummary'>\"" + parentContentSummary + "...\"</div>" +
					"<div class='commentContent'>" + content + "</div>" +
					"</div>");
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
						onOk: function() {
							history.go(-1);
							location.reload();
						},
						onClose: function() {
							history.go(-1);
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

//点赞   （局部刷新+1）
function praise(id) {
	$.ajax({
		url: "praise.json",
		data: {
			"Id": id
		},
		dataType : "json",
		error: function() {
			var txt = "点赞失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				if (data.success) {
					var count = $(".praise label").text();
					$(".praise label").text(parseInt(count) + 1);
					$(".praise").attr("class", "praiseAnother").removeAttr("onclick");
				} else {
					var txt = "点赞失败！";
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return unescape(r[2]);
	return null; //返回参数值
}

//查询是否登录
function checkLogin() {
	$.ajax({
		async : false,
		url : "checkLogin.json",
		dataType:"json",
		success:function(data){
			if(data.success){
				checkComment();
			}else{
				var txt = "请先登录,确定要跳转至登录页吗？";
				window.wxc.xcConfirm(txt, "confirm", {
					onOk : function() {
						window.location.replace("./login.html");
					}
				});
				gt_captcha_obj.disable();
				return false;
			}
		}
	});
}


//check评论是否为空
function checkComment(){
	var comments = $.trim(UE.getEditor('editor').getContentTxt());

	if (comments != "") {
		gt_captcha_obj.enable();
		gt_captcha_obj.onSuccess(function() {
			addComment(comments);
		});
		return true;
	} else {
		var txt = "评论不能为空！";
		window.wxc.xcConfirm(txt, "info");
		gt_captcha_obj.disable();
		return false;
	}
}

//添加评论
function addComment(comments) {
	var id = getUrlParam('id');

	$.ajax({
		type: "post",
		url: "addComment.json",
		data: {
			"content": comments,
			"id": id
		},
		dataType : "json",
		async: true,
		success: function(data) {
			if (data != null) {
				var txt = data.Msg;
				if (data.success) {
					window.wxc.xcConfirm(txt, "success", {
						onOk: function() {
							// 刷新评论区域
							getComment(id);
						},
						onClose: function() {
							// 刷新评论区域
							getComment(id);
						}
					});
				} else {
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}