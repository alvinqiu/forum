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
		url:"getAllModule.json",
		error:function(){alert("获取数据失败！");},
		success:function(data){
			var jsonObj=eval("("+data+")");
			
			for(var i=0,tagLen = jsonObj.moduleVOList.length;i<tagLen;i++){
				
				var id = jsonObj.moduleVOList[i].id;
				var name = jsonObj.moduleVOList[i].name;
				var desc = jsonObj.moduleVOList[i].desc;
				var sort = jsonObj.moduleVOList[i].sort;
				var visible = jsonObj.moduleVOList[i].visible;
				
				$(".panel table").append("<tr><td>"+id+"</td><td>"+name+"</td><td>"+desc+"</td><td>"+sort+"</td><td>"+visible+"</td>" +
						"<td><a href='javascript:void(0);' id='js-editModule"+id+"'>修改</a><a href='javascript:void(0);' id='js-delModule"+id+"'>删除</a></td></tr>");
				
			}
			$("a[id^='js-editModule']").click(editModule);
			$("a[id^='js-delModule']").click(delModule);
		}
	});
	
	
	$("#js-addModule").click(addModule);
});

function addModule() {
	$("#moduleDialog input,#moduleDialog textarea").attr("value","");
	$("#js-id").val(0);
	$("#js-id").attr("readonly", true);
	$("#js-visible").attr("checked",false);

	$("#moduleDialog").dialog({
		buttons : {
			"取消" : function() {
				$(this).dialog("close");
				$(this).dialog("destroy");
			},
			"添加" : function() {
				$.ajax({
					url : "addModule.json",
					data : {
						"id" : 0,
						"name" : $.trim($("#js-name").val()),
						"desc" : $.trim($("#js-desc").val()),
						"visible" : $("#js-visible").prop("checked")
					},
					error : function() {
						alert("添加模块失败2!");
					},
					success : function(data) {
						var jsonObj = eval("(" + data + ")");
						alert(jsonObj.result);
						if (jsonObj.success) {
							location.reload();
						}
						if(jsonObj.redirect){
							location.href="/forum/login.html";
						}
					}
				});
			}
		},
		title : "添加模块",
		width : 400,
		modal : true,
		zIndex : 11
	});
}

function editModule() {
	var id = $.trim($(this).parent().parent().find('td').eq(0).text());
	var name = $.trim($(this).parent().parent().find('td').eq(1).text());
	var desc = $.trim($(this).parent().parent().find('td').eq(2).text());
	var sort = $.trim($(this).parent().parent().find('td').eq(3).text());
	var visible = $.trim($(this).parent().parent().find('td').eq(4).text());

	$("#js-id").val(id);
	$("#js-id").attr("readonly", true);
	$("#js-name").val(name);
	$("#js-desc").val(desc);
	$("#js-visible").attr("checked",visible=="true"?true:false);

	$("#moduleDialog").dialog({
		buttons : {
			"取消" : function() {
				$(this).dialog("close");
				$(this).dialog("destroy");
			},
			"修改" : function() {
				$.ajax({
					url : "editModule.json",
					data : {
						"id" : id,
						"name" : $.trim($("#js-name").val()),
						"desc" : $.trim($("#js-desc").val()),
						"visible" : $("#js-visible").prop("checked")
					},
					error : function() {
						alert("修改模块失败!");
					},
					success : function(data) {
						var jsonObj = eval("(" + data + ")");
						if (jsonObj.success) {
							alert("修改模块成功！");
							location.reload();
						} else {
							alert("修改模块失败！");
						}
					}
				});
			}
		},
		title : "修改模块",
		width : 400,
		modal : true,
		zIndex : 11
	});
}

function delModule() {
	var id = $.trim($(this).parent().parent().find('td').eq(0).text());

	$.ajax({
		url : "delModule.json",
		data : {
			"id" : id
		},
		error : function() {
			alert("删除模块失败!");
		},
		success : function(data) {
			var jsonObj = eval("(" + data + ")");
			if (jsonObj.success) {
				alert("删除模块成功！");
				location.reload();
			} else {
				alert("删除模块失败！");
			}
		}
	});
}

//上移操作
function prevMoveTrOpra(obj) {
	var $jqObj = jQuery(obj).parent().parent(); //获得本身tr的信息
	var $trOObjt = jQuery("#hide_tr_id").append($jqObj.html()); //把本身tr放入临时信息
	var $jqSublObj = jQuery(obj).parent().parent().prev(); //获得上一个tr的信息
	$jqSublObj.find(".td_num").text(
			Number($jqSublObj.find(".td_num").text()) + 1); //把上一个tr序号加1
	$jqObj.html("").append($jqSublObj.html()); //把本身tr清空并插入上一个信息

	$trOObjt.find(".td_num").text(Number($trOObjt.find(".td_num").text()) - 1); //把本身tr序号减1
	$jqSublObj.html("").append($trOObjt.html()); //把上一个tr清空并插入临时保存的tr信息
	jQuery("#hide_tr_id").html(""); //清空临时tr信息
}