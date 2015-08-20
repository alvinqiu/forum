$(function(){

	$.ajax({
		url:"checkLogin4Admin.json",
		async:false,
		success:function(data){
			var jsonObj=eval("("+data+")");
			if(!jsonObj.success){
				alert("您还没有权限访问，请联系管理员！");
				history.go(-1);
			}
		}
	});
	
	$("#js-module").click(function(){
		location.href="module.html";
	});
	
	$("#js-post").click(function(){
		location.href="post.html";
	});
	
	$("#js-group").click(function(){
		location.href="group.html";
	});
});