$(function(){
	
	$.ajax({
		url : "checkLogin.json",
		async : false,
		success : function(data) {
			var jsonObj = eval("(" + data + ")");
			if (jsonObj.success) {
				if (!jsonObj.isAdmin) {
					alert("您还没有权限访问，请联系管理员！");
					history.go(-1);
				}
			} else {
				alert("请先登录！");
				history.go(-1);
			}
		}
	});
	
	$.ajax({
		url:"getAllPostByHold.json",
		async:true,
		error:function(request){
			alert("获取待审核帖子列表失败！");
		},
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.postVOList.length;i<tagLen;i++){
				
				var id = jsonObj.postVOList[i].id;
				var subject = jsonObj.postVOList[i].subject;
				var submitTime = formatDate(new Date(jsonObj.postVOList[i].submitTime.time));
				var content = jsonObj.postVOList[i].content;
				
				$(".list").append("<div><a href='javascript:void(0);'>#"+id+" 标题："+subject+" 时间："+submitTime+"</a><input type='hidden' value='"+content+"' />" +
				"<input type='button' value='通过' /><input type='hidden' value='"+id+"' />" +		
				"</div>");
				
			}
			
			$("a").click(function(){
				var content = $.trim($(this).next().val());
				$(".detail").empty();
				$(".detail").append("<div>"+content+"</div>");
			});
			
			$("input[type='button']").click(function(){
				var id = $(this).next().val();
				$.ajax({
					url:"passPost.json",
					data:{"id":id},
					error:function(request){alert("审核帖子失败！");},
					success:function(data){
						var jsonObj = eval("(" + data + ")");
						if (jsonObj.success) {
							alert("审核帖子成功！");
							location.reload();
						} else {
							alert("审核帖子失败！");
						}
					}
				});
				
			});
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