$(function(){
	//实例化UEditor编辑器
	var ue = UE.getEditor('editor', {
		toolbars: [
					['fontsize','fontfamily','bold','undo','cleardoc','forecolor']
				],
		initialFrameWidth:790,
		initialFrameHeight:200
	});
	
	var id = getUrlParam('id');
	
	$.ajax({
		url: "getPostById.json",
		data: {
			"id": id
		},
		async: true,
		success: function(data) {
			var jsonObj = eval("(" + data + ")");

			var id = jsonObj.PostVO.id;
			var subject = jsonObj.PostVO.subject;
			var submitTime = formatDate(new Date(jsonObj.PostVO.submitTime.time));
			var content = jsonObj.PostVO.content;
			var groupId = jsonObj.GroupId;
			var name = jsonObj.PostVO.name;
			var commentCount = jsonObj.PostVO.commentCount;

			$(".panel_left_main").append(
				"<div class='subject'>" + subject + "</div>");
			$(".panel_left_main").append(
				"<div class='name'>" + name + "</div>");
			$(".panel_left_main").append(
				"<div class='submitTime'>" + submitTime + "</div>");
			if(commentCount!=0){
				$(".panel_left_main").append(
					"<div class='commentCount'><label>" + commentCount + "</label></div>");
			}
			$(".panel_left_main").append(
				"<div class='content'>" + content + "</div>");
			if (groupId < 3) {
				$(".panel_left_main").append(
					"<div class='delBtn'><a href='javascript:void(0);'onclick='del(" + id + ")'>删除</a></div>");
			}

			$(".panel_right_user_name").html(name);

		}
	});
	
	$.ajax({
		url:"getComment.json",
		data:{"id":id},
		async:false,
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.postVOList.length;i<tagLen;i++){
				
				var id = jsonObj.postVOList[i].id;
				var subject = jsonObj.postVOList[i].subject;
				var submitTime = formatDate(new Date(jsonObj.postVOList[i].submitTime.time));
				var content = jsonObj.postVOList[i].content;
				var parentContentSummary = jsonObj.postVOList[i].parentContentSummary;
				var name = jsonObj.postVOList[i].name;
				
				$(".panel_left_comment").append("<div id='"+id+"anchor' name='"+id+"anchor'>" +
										"<div class='commentName'>"+name+"</div>" +
										"<div class='commentSubmitTime'>"+submitTime+"</div>" +
										"<div class='commentFloor'>"+(i+1)+"楼</div>" +
										"<div class='commentSummary'>"+parentContentSummary+"</div>" +
										"<div class='commentContent'>"+content+"</div>" +
										"</div>");
			}
		}
	});
	
});

//删除帖子
function del(id){
	$.ajax({
		url:"del.json",
		data:{"Id":id},
		error:function(){alert("删除失败！");},
		success:function(data){
			var jsonObj = eval("("+data+")");
			if(jsonObj.success){
				alert("删除成功！");
				history.go(-1);
				location.reload();
			}
			else{
				alert("删除失败！");
			}
		}
	});
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
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

function gt_custom_ajax(result, selector, message) {
	var ue = UE.getEditor('editor');
	var id = getUrlParam('id');
	
	if (result) {
		var challenge = selector(".geetest_challenge").value;
		var validate = selector(".geetest_validate").value;
		var seccode = selector(".geetest_seccode").value;
		
		var comments = $.trim(UE.getEditor('editor').getPlainTxt());
		if(comments!=""){
			$.ajax({
				type:"post",
				url:"addComment.json",
				data:{
					"content":comments,
					"id":id
					},
				async:false,
				success : function(data,XHR, TS) {
					if (data != "") {
						var jsonObj = eval("(" + data + ")");
						if (jsonObj.success) {
							alert("评论成功！");
							location.reload();
						} else {
							alert("评论失败！");
						}
					}
				}
			});
		}
		else{
			alert("不能为空！");
		}
		
	}
}