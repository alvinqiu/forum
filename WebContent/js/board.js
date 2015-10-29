$(function(){
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
				
				$(".panel_detail_content").append("<div class='item'><a href='./list.html?moduleId="+id+"&page=1'>" +
						"<div class='img'></div>" +
						"<div class='name'>"+name+"</div>" +
						"<div class='desc'>"+desc+"</div>" +
						"</a></div>");
			}
		}
	});
});