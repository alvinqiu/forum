$(function(){
	//实例化UEditor编辑器
	var ue = UE.getEditor('editor', {
		toolbars : [ [ 'fontsize', 'fontfamily', 'bold', 'undo', 'cleardoc',
				'forecolor','simpleupload','insertimage' ] ],
		initialFrameWidth : 790,
		initialFrameHeight : 200,
		scaleEnabled:false,
        minFrameWidth:790,
        minFrameHeight:200 
	});

	//获取所有版块
	$.ajax({
		url:"getAllModule.json",
		error:function(){alert("获取数据失败！");},
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.moduleVOList.length;i<tagLen;i++){
				
				var id = jsonObj.moduleVOList[i].id;
				var name = jsonObj.moduleVOList[i].name;
				
				$(".module select").append("<option value='"+id+"'>"+name+"</option>");
				
			}
		}
	});
	
//	$("#editor").blur(function(){
//		$("input[name='content']").val($.trim(UE.getEditor('editor').getPlainTxt()));
//	});
	
	$("input[type='checkbox']").click(function(){
		if($(this).is(":checked")){
			$(this).next().val("1");
		}
		else{
			$(this).next().val("0");
		}
	});
	
//	$("#js-submit").click(function(){
//		
//		
//		return false;
//	});
});

function checkForm(){
	var subject = $.trim($("input[name='subject']").val());
	var moduleId = $.trim($("select[name='moduleId']").val());
	var type = $.trim($("select[name='type']").val());
	$("input[name='content']").val($.trim(UE.getEditor('editor').getPlainTxt()));
	var content = $.trim($("input[name='content']").val());
	
	if(subject==""){
		alert("标题不能为空！");
		return false;
	}
	else if(moduleId=="0"){
		alert("请选择版块！");
		return false;
	}
	else if(type=="0"){
		alert("请选择类型！");
		return false;
	}
	else if(content==""){
		alert("请输入内容！");
		return false;
	}
	else{
		return true;
	}
}

function gt_custom_ajax(result, selector, message) {
	
	if (result) {
		var challenge = selector(".geetest_challenge").value;
		var validate = selector(".geetest_validate").value;
		var seccode = selector(".geetest_seccode").value;
		
		var formDom = $("form[name='postForm']");
		if(checkForm()){
			$.ajax({
				type: "get",
				url:"addPost.json",
				data:formDom.serialize(),
				async: true,
		        error: function(request) {
		        	alert("发布帖子失败！"+request);
		        },
		        success: function(data) {
		        	var jsonObj=eval("("+data+")");
		        	//alert(jsonObj.result);
		        	if(jsonObj.success){
		        		alert("发布成功！");
		        		window.location.href="/forum/index.html";
		        	}
		        }
			});
		}
		
	}
}