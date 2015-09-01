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
	
	//获取所有非普通用户
	$.ajax({
		url:"getAllGroupExceptUser.json",
		async:true,
		error:function(){alert("获取非普通用户身份类型失败！");},
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.UserList.length;i<tagLen;i++){
				
				var username = jsonObj.UserList[i].mail;
				var groupName = jsonObj.UserList[i].groupName;
				
				$(".list").append("<div>"+username+" --- "+groupName+"</div>");
			}
		}
	});
	
	//获取身份类型
	$.ajax({
		url:"getAllGroup.json",
		async:true,
		error:function(){alert("获取身份类型失败！");},
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.GroupList.length;i<tagLen;i++){
				
				var id = jsonObj.GroupList[i].id;
				var code = jsonObj.GroupList[i].code;
				var name = jsonObj.GroupList[i].name;
				
				$("#js-group").append("<option value='"+id+"'>"+name+"</option>");
			}
		}
	});
	
	$("#js-setGroup").click(function(){
		var name = $.trim($("#js-username").val());
		if(name!=""){
			$.ajax({
				url:"setGroup.json",
				data:{
					"username":name,
					"groupid":$("#js-group").val()
					},
				async:true,
				error:function(){alert("设置身份类型失败！");},
				success:function(data){
					var jsonObj = eval("("+data+")");
					if(jsonObj.result<0){
						alert("此用户名不存在！");
					}
					else if(jsonObj.result>0){
						alert("设置成功！");
						location.reload();
					}
					else{
						alert("设置失败！");
					}
				}
			});
		}
	});
});