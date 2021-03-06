$(function() {
	//设置banner轮换效果
	var time = window.setInterval(function() {
		nextJump();
	}, 5000);

	linum = $('.mainlist li').length; //图片数量
	w = linum * 840; //ul宽度
	$('.piclist').css('width', w + 'px'); //ul宽度
	$('.swaplist').html($('.mainlist').html()); //复制内容

	$('.og_next').click(function() {
		clearInterval(time);
		nextJump();
		time = window.setInterval(function() {
			nextJump();
		}, 5000);
	});

	$('.og_prev').click(function() {
		clearInterval(time);
		prevJump();
		time = window.setInterval(function() {
			nextJump();
		}, 5000);
	});

	function nextJump() {
		if ($('.swaplist,.mainlist').is(':animated')) {
			$('.swaplist,.mainlist').stop(true, true);
		}

		if ($('.mainlist li').length > 2) { //多于2张图片
			ml = parseInt($('.mainlist').css('left')); //默认图片ul位置
			sl = parseInt($('.swaplist').css('left')); //交换图片ul位置
			if (ml <= 0 && ml > w * -1) { //默认图片显示时
				$('.swaplist').css({
					left: '840px'
				}); //交换图片放在显示区域右侧
				$('.mainlist').animate({
					left: ml - 840 + 'px'
				}, 'slow'); //默认图片滚动				
				if (ml == (w - 840) * -1) { //默认图片最后一屏时
					$('.swaplist').animate({
						left: '0px'
					}, 'slow'); //交换图片滚动
				}
			} else { //交换图片显示时
				$('.mainlist').css({
						left: '840px'
					}) //默认图片放在显示区域右
				$('.swaplist').animate({
					left: sl - 840 + 'px'
				}, 'slow'); //交换图片滚动
				if (sl == (w - 840) * -1) { //交换图片最后一屏时
					$('.mainlist').animate({
						left: '0px'
					}, 'slow'); //默认图片滚动
				}
			}
		}
	}

	function prevJump() {
		if ($('.swaplist,.mainlist').is(':animated')) {
			$('.swaplist,.mainlist').stop(true, true);
		}

		if ($('.mainlist li').length > 2) {
			ml = parseInt($('.mainlist').css('left'));
			sl = parseInt($('.swaplist').css('left'));
			if (ml <= 0 && ml > w * -1) {
				$('.swaplist').css({
					left: w * -1 + 'px'
				});
				$('.mainlist').animate({
					left: ml + 840 + 'px'
				}, 'slow');
				if (ml == 0) {
					$('.swaplist').animate({
						left: (w - 840) * -1 + 'px'
					}, 'slow');
				}
			} else {
				$('.mainlist').css({
					left: (w - 840) * -1 + 'px'
				});
				$('.swaplist').animate({
					left: sl + 840 + 'px'
				}, 'slow');
				if (sl == 0) {
					$('.mainlist').animate({
						left: '0px'
					}, 'slow');
				}
			}
		}
	}

	//获取最新的5条帖子
	$.ajax({
		url: "getAllPost.json",
		async: true,
		data: {
			"page": 1
		}, //最新的帖子
		dataType : "json",
		success: function(data) {
			for (var i = 0, tagLen = data.postList.length; i < tagLen; i++) {

				var id = data.postList[i].id;
				var subject = data.postList[i].subject;
				var formatTime = data.postList[i].formatTime;
				var name = data.postList[i].name; //昵称
				var commentCount = data.postList[i].commentCount; //回复数
				var content = data.postList[i].content; //摘要
				var imgStr = data.postList[i].imgStr; //图片路径

				if (commentCount != 0) {
					$(".panel_left_list").append("<div class='postDetail'><div class='dLeft'><img " + imgStr + " onload='AutoResizeImage(300,200,this)' /></div>" +
						"<div class='dRight'>" +
						"<a href='./detail.html?id=" + id + "'><div class='postTitle'>" + subject + "</div></a><div class='postTime'>" + formatTime + "</div>" +
						"<div class='postNickName'>" + name + "</div><div class='postCommentCount'><label>" + commentCount + "<label></div><div class='postContent'>" + content + "</div>" +
						"</div></div>");
				} else {
					$(".panel_left_list").append("<div class='postDetail'><div class='dLeft'><img " + imgStr + " onload='AutoResizeImage(300,200,this)' /></div>" +
						"<div class='dRight'>" +
						"<a href='./detail.html?id=" + id + "'><div class='postTitle'>" + subject + "</div></a><div class='postTime'>" + formatTime + "</div>" +
						"<div class='postNickName'>" + name + "</div><div class='postContent'>" + content + "</div>" +
						"</div></div>");
				}
			}
		}
	});

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

			for (var i = 0, tagLen = data.moduleVOList.length; i < tagLen; i++) {
				var id = data.moduleVOList[i].id;
				var name = data.moduleVOList[i].name;
				var desc = data.moduleVOList[i].desc;
				var sort = data.moduleVOList[i].sort;

				$(".panel_right_module_content ul").append("<li><a href='./list.html?moduleId=" + id + "&page=1'>" + name + "</a></li>");
			}
		}
	});

});

function AutoResizeImage(maxWidth, maxHeight, objImg) {
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth == 0 && maxHeight == 0) {
		Ratio = 1;
	} else if (maxWidth == 0) { //
		if (hRatio < 1)
			Ratio = hRatio;
	} else if (maxHeight == 0) {
		if (wRatio < 1)
			Ratio = wRatio;
	} else if (wRatio < 1 || hRatio < 1) {
		Ratio = (wRatio <= hRatio ? wRatio : hRatio);
	}
	if (Ratio < 1) {
		w = w * Ratio;
		h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
}