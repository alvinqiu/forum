$(function() {

	$.ajax({
		url: "checkLogin.json",
		async: false,
		dataType : "json",
		success: function(data) {
			if (data.success) {
				if (!data.isAdmin) {
					var txt = "您还没有权限访问，请联系管理员！";
					window.wxc.xcConfirm(txt, "info", {
						onOk : function() {
							history.go(-1);
						},
						onClose : function() {
							history.go(-1);
						}
					});
				} else {
					getAllModule();
				}
			} else {
				var txt = "请先登录！";
				window.wxc.xcConfirm(txt, "info", {
					onOk : function() {
						location.href = "./login.html";
					},
					onClose : function() {
						location.href = "./login.html";
					}
				});
			}
		}
	});

	$("#js-addModule").click(addModule);
});

function getAllModule() {
	$.ajax({
		url: "getAllModule.json",
		async: false,
		dataType : "json",
		error: function() {
			var txt = "获取数据失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {

			for (var i = 0, tagLen = data.moduleVOList.length; i < tagLen; i++) {

				var id = data.moduleVOList[i].id;
				var name = data.moduleVOList[i].name;
				var desc = data.moduleVOList[i].desc;
				var sort = data.moduleVOList[i].sort;
				var visible = data.moduleVOList[i].visible;

				$(".panel table").append("<tr><td>" + id + "</td><td>" + name + "</td><td>" + desc + "</td><td>" + sort + "</td><td>" + visible + "</td>" +
					"<td><a href='javascript:void(0);' id='js-editModule" + id + "'>修改</a><a href='javascript:void(0);' id='js-delModule" + id + "'>删除</a></td></tr>");

			}
			$("a[id^='js-editModule']").click(editModule);
			$("a[id^='js-delModule']").click(delModule);
		}
	});
}

function addModule() {
	$("#moduleDialog input,#moduleDialog textarea").attr("value", "");
	$("#js-id").val(0);
	$("#js-id").attr("readonly", true);
	$("#js-visible").attr("checked", false);

	$("#moduleDialog").dialog({
		buttons: {
			"取消": function() {
				$(this).dialog("close");
				$(this).dialog("destroy");
			},
			"添加": function() {
				$.ajax({
					url: "addModule.json",
					data: {
						"id": 0,
						"name": $.trim($("#js-name").val()),
						"desc": $.trim($("#js-desc").val()),
						"visible": $("#js-visible").prop("checked")
					},
					dataType : "json",
					error: function() {
						var txt = "添加模块失败!";
						window.wxc.xcConfirm(txt, "error");
					},
					success: function(data) {
						if (data != null) {
							var txt = data.result;
							window.wxc.xcConfirm(txt, "info", {
								onOk: function() {
									if (data.success) {
										location.reload();
									}
									if (data.redirect) {
										location.href = "./login.html";
									}
								}
							});
						}
					}
				});
			}
		},
		title: "添加模块",
		width: 400,
		modal: true,
		zIndex: 11
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
	$("#js-visible").attr("checked", visible == "true" ? true : false);

	$("#moduleDialog").dialog({
		buttons: {
			"取消": function() {
				$(this).dialog("close");
				$(this).dialog("destroy");
			},
			"修改": function() {
				$.ajax({
					url: "editModule.json",
					data: {
						"id": id,
						"name": $.trim($("#js-name").val()),
						"desc": $.trim($("#js-desc").val()),
						"visible": $("#js-visible").prop("checked")
					},
					dataType : "json",
					error: function() {
						var txt = "修改模块失败!";
						window.wxc.xcConfirm(txt, "error");
					},
					success: function(data) {
						if (data != null) {
							if (data.success) {
								var txt = "修改模块成功！";
								window.wxc.xcConfirm(txt, "success", {
									onOk : function() {
										location.reload();
									}
								});

							} else {
								var txt = "修改模块失败！";
								window.wxc.xcConfirm(txt, "info");
							}
						}
					}
				});
			}
		},
		title: "修改模块",
		width: 400,
		modal: true,
		zIndex: 11
	});
}

function delModule() {
	var id = $.trim($(this).parent().parent().find('td').eq(0).text());

	$.ajax({
		url: "delModule.json",
		data: {
			"id": id
		},
		dataType : "json",
		error: function() {
			var txt = "删除模块失败!";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				if (data.success) {
					var txt = "删除模块成功！";
					window.wxc.xcConfirm(txt, "success", {
						onOk : function() {
							location.reload();
						}
					});
				} else {
					var txt = "删除模块失败！";
					window.wxc.xcConfirm(txt, "info");
				}
			}
		}
	});
}

// 上移操作
function prevMoveTrOpra(obj) {
	var $jqObj = jQuery(obj).parent().parent(); // 获得本身tr的信息
	var $trOObjt = jQuery("#hide_tr_id").append($jqObj.html()); // 把本身tr放入临时信息
	var $jqSublObj = jQuery(obj).parent().parent().prev(); // 获得上一个tr的信息

	$jqSublObj.find(".td_num").text(
			Number($jqSublObj.find(".td_num").text()) + 1); // 把上一个tr序号加1
	$jqObj.html("").append($jqSublObj.html()); // 把本身tr清空并插入上一个信息

	$trOObjt.find(".td_num").text(Number($trOObjt.find(".td_num").text()) - 1); // 把本身tr序号减1
	$jqSublObj.html("").append($trOObjt.html()); // 把上一个tr清空并插入临时保存的tr信息
	jQuery("#hide_tr_id").html(""); // 清空临时tr信息
}