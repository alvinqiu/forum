$(function(){
	//设置banner轮换效果
	$(".banner_inbox").corner('8px');
	
	$("#js-banner-box>ul").roundabout({
		minOpacity:1,
		btnNext: ".next",
		duration: 1000,
		reflect: true,
		btnPrev: '.prev',
		autoplay:true,
		autoplayDuration:5000,
		tilt:0,
		shape: 'figure8'
	});
	
	//获取最新的5条帖子
	$.ajax({
		url:"getAllPost.json",
		async:true,
		data:{"page":1},//最新的帖子
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.postList.length;i<tagLen;i++){
				
				var id = jsonObj.postList[i].id;
				var subject = jsonObj.postList[i].subject;
				var submitTime = formatDate(new Date(jsonObj.postList[i].submitTime.time));
				var name = jsonObj.postList[i].name;//昵称
				var commentCount = jsonObj.postList[i].commentCount;//回复数
				var content = jsonObj.postList[i].content;//摘要
				
				$(".panel_left_list").append("<div class='postDetail'><div class='dLeft'><img src='./img/testImg.jpg' /></div>" +
						"<div class='dRight'>" +
								"<a href='./detail.html?id="+id+"'><div class='postTitle'>"+subject+"</div></a><div class='postTime'>"+submitTime+"</div>" +
								"<div class='postNickName'>"+name+"</div><div class='postCommentCount'>"+commentCount+"</div><div class='postContent'>"+content+"</div>"+
										"</div></div>");
			}
		}
	});
	
	//获取所有版块
	$.ajax({
		url:"getAllModule.json",
		async:true,
		error:function(){alert("获取版块失败！");},
		success:function(data){
			var jsonObj = eval("("+data+")");
			
			for(var i = 0,tagLen = jsonObj.moduleVOList.length;i<tagLen;i++){
				var id = jsonObj.moduleVOList[i].id;
				var name = jsonObj.moduleVOList[i].name;
				var desc = jsonObj.moduleVOList[i].desc;
				var sort = jsonObj.moduleVOList[i].sort;
				
				$(".panel_right_module_content ul").append("<li><a href='./list.html?moduleId="+id+"&page=1'>"+name+"</a></li>");
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