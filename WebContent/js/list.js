$(function(){
	var moduleId = getUrlParam('moduleId');
	var page = getUrlParam('page');
	
	$("#js-addPost").click(function(){
		location.href="/forum/addPost.html";
	});
	
	$.ajax({
		url:"getAllPost.json",
		data:{
			"page":page,
			"moduleId":moduleId
			},
		async:true,
		success:function(data){
			
			//清空div内容
			$(".panel_left_top").empty();
			$(".panel_left_common").empty();
			
			var jsonObj=eval("("+data+")");
			
			var groupId = jsonObj.GroupId;
			var total = jsonObj.Total;
			var url = getUrl();
			
			if(page<=total && page>1){
				$(".pg").append("<a class='prev' href='" + url + "?moduleId="+moduleId+"&page=" + (parseInt(page) - 1)+ "'><em class='previcon'></em></a>");
			}
			for(var j=1;j<=total;j++){
				if(j==page){
					$(".pg").append("<strong>"+j+"</strong>");
				}
				else{
					$(".pg").append("<a href='" + url + "?moduleId="+moduleId+"&page=" + j + "'>"+ j + "</a>");
				}
			}
			if(total>1 && total!=page){
				$(".pg").append("<a class='next' href='" + url + "?moduleId="+moduleId+"&page=" + (parseInt(page) + 1)+ "'><em class='nxticon'></em></a>");
			}
			
			
			for(var i=0,tagLen = jsonObj.postList.length;i<tagLen;i++){
				
				var id = jsonObj.postList[i].id;
				var subject = jsonObj.postList[i].subject;
				var submitTime = formatDate(new Date(jsonObj.postList[i].submitTime.time));
				var name = jsonObj.postList[i].name;
				var commentCount = jsonObj.postList[i].commentCount;
				var highLight = jsonObj.postList[i].highLight;
				var top = jsonObj.postList[i].top;
				
				if(top!=0){
					if(highLight!=0){
						if(groupId<3){
							$(".panel_left_top").append("<div class='hightlight'><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div>" +
											"<div class='operate'>" +
											"<a href='javascript:void(0);' onclick='setHighLight("+id+")'>加精</a>" +
											"<a href='javascript:void(0);' onclick='setTop("+id+")'>置顶</a>" +
											"<a href='javascript:void(0);' onclick='del("+id+")'>删除</a></div></div>");
						}
						else{
							$(".panel_left_top").append("<div class='hightlight'><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div></div>");
						}
					}
					else{
						if(groupId<3){
							$(".panel_left_top").append("<div><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div>" +
											"<div class='operate'>" +
											"<a href='javascript:void(0);' onclick='setHighLight("+id+")'>加精</a>" +
											"<a href='javascript:void(0);' onclick='setTop("+id+")'>置顶</a>" +
											"<a href='javascript:void(0);' onclick='del("+id+")'>删除</a></div></div>");
						}
						else{
							$(".panel_left_top").append("<div><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div></div>");
						}
					}
				}
				else{
					if(highLight!=0){
						if(groupId<3){
							$(".panel_left_common").append("<div class='hightlight'><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div>" +
											"<div class='operate'>" +
											"<a href='javascript:void(0);' onclick='setHighLight("+id+")'>加精</a>" +
											"<a href='javascript:void(0);' onclick='setTop("+id+")'>置顶</a>" +
											"<a href='javascript:void(0);' onclick='del("+id+")'>删除</a></div></div>");
						}
						else{
							$(".panel_left_common").append("<div class='hightlight'><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div></div>");
						}
					}
					else{
						if(groupId<3){
							$(".panel_left_common").append("<div><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div>" +
											"<div class='operate'>" +
											"<a href='javascript:void(0);' onclick='setHighLight("+id+")'>加精</a>" +
											"<a href='javascript:void(0);' onclick='setTop("+id+")'>置顶</a>" +
											"<a href='javascript:void(0);' onclick='del("+id+")'>删除</a></div></div>");
						}
						else{
							$(".panel_left_common").append("<div><a href='/forum/detail.html?id="+id+"'>" +
									"<div class='subject'>"+subject+"</div></a><div class='name'>"+name+"</div></div>");
						}
					}
				}
			}
		}
	});
});

//设置高亮（加精）
function setHighLight(id){
	$.ajax({
		url:"setHighLight.json",
		data:{"Id":id},
		error:function(){alert("设置高亮失败！");},
		success:function(data){
			var jsonObj = eval("("+data+")");
			if(jsonObj.success){
				alert("设置高亮成功！");
				location.reload();
			}
			else{
				alert("设置高亮失败！");
			}
		}
	});
}

//设置置顶
function setTop(id){
	$.ajax({
		url:"setTop.json",
		data:{"Id":id},
		error:function(){alert("设置置顶失败！");},
		success:function(data){
			var jsonObj = eval("("+data+")");
			if(jsonObj.success){
				alert("设置置顶成功！");
				location.reload();
			}
			else{
				alert("设置置顶失败！");
			}
		}
	});
}

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
				location.reload();
			}
			else{
				alert("删除失败！");
			}
		}
	});
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