$(function(){
	//设置banner轮换效果
	
	time = window.setInterval(function(){
		$('.og_next').click();	
	},5000);
	
	linum = $('.mainlist li').length;//图片数量
	w = linum * 686;//ul宽度
	$('.piclist').css('width', w + 'px');//ul宽度
	$('.swaplist').html($('.mainlist').html());//复制内容
	
	$('.og_next').click(function(){
		
		if($('.swaplist,.mainlist').is(':animated')){
			$('.swaplist,.mainlist').stop(true,true);
		}
		
		if($('.mainlist li').length>2){//多于2张图片
			ml = parseInt($('.mainlist').css('left'));//默认图片ul位置
			sl = parseInt($('.swaplist').css('left'));//交换图片ul位置
			if(ml<=0 && ml>w*-1){//默认图片显示时
				$('.swaplist').css({left: '686px'});//交换图片放在显示区域右侧
				$('.mainlist').animate({left: ml - 686 + 'px'},'slow');//默认图片滚动				
				if(ml==(w-686)*-1){//默认图片最后一屏时
					$('.swaplist').animate({left: '0px'},'slow');//交换图片滚动
				}
			}else{//交换图片显示时
				$('.mainlist').css({left: '686px'})//默认图片放在显示区域右
				$('.swaplist').animate({left: sl - 686 + 'px'},'slow');//交换图片滚动
				if(sl==(w-686)*-1){//交换图片最后一屏时
					$('.mainlist').animate({left: '0px'},'slow');//默认图片滚动
				}
			}
		}
	});
	$('.og_prev').click(function(){
		
		if($('.swaplist,.mainlist').is(':animated')){
			$('.swaplist,.mainlist').stop(true,true);
		}
		
		if($('.mainlist li').length>2){
			ml = parseInt($('.mainlist').css('left'));
			sl = parseInt($('.swaplist').css('left'));
			if(ml<=0 && ml>w*-1){
				$('.swaplist').css({left: w * -1 + 'px'});
				$('.mainlist').animate({left: ml + 686 + 'px'},'slow');				
				if(ml==0){
					$('.swaplist').animate({left: (w - 686) * -1 + 'px'},'slow');
				}
			}else{
				$('.mainlist').css({left: (w - 686) * -1 + 'px'});
				$('.swaplist').animate({left: sl + 686 + 'px'},'slow');
				if(sl==0){
					$('.mainlist').animate({left: '0px'},'slow');
				}
			}
		}
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