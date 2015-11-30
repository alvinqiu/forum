$(function() {

	var $image = $(".filePanel img");
	$image.cropper({
		aspectRatio: 1,
		preview: ".preview"
	});

	var $inputImage = $("#pickfiles");
	if (window.FileReader) {
		$inputImage.change(function() {
			var fileReader = new FileReader(),
				files = this.files,
				file;
			if (!files.length) {
				return;
			}
			file = files[0];
			if (/^image\/\w+$/.test(file.type)) {
				fileReader.readAsDataURL(file);
				fileReader.onload = function() {
					$image.cropper("reset", true).cropper("replace", this.result);
					$inputImage.val("");
				};
			} else {
				var txt = "请选择图片类型！";
				window.wxc.xcConfirm(txt, "info");
			}
		});
	} else {
		$inputImage.parent().remove();
	}

	$("#js-headFile").bind("click", function() {
		$("#pickfiles").click();
	});

	$.ajax({
		url: "checkLogin.json",
		async: false,
		dataType: "json",
		success: function(data) {
			if (data.success) {
				if (!data.isAdmin) {
					var txt = "您还没有权限访问，请联系管理员！";
					window.wxc.xcConfirm(txt, "info", {
						onOk: function() {
							history.go(-1);
						},
						onClose: function() {
							history.go(-1);
						}
					});
				} else {
					getAllModule();
				}
			} else {
				var txt = "请先登录！";
				window.wxc.xcConfirm(txt, "info", {
					onOk: function() {
						location.href = "./login.html";
					},
					onClose: function() {
						location.href = "./login.html";
					}
				});
			}
		}
	});

	$("#js-addModule").click(addModule);

	function getAllModule() {
		$.ajax({
			url: "getAllModule.json",
			async: false,
			dataType: "json",
			error: function() {
				var txt = "获取数据失败！";
				window.wxc.xcConfirm(txt, "error");
			},
			success: function(data) {

				for (var i = 0, tagLen = data.moduleVOList.length; i < tagLen; i++) {

					var id = data.moduleVOList[i].id;
					var name = data.moduleVOList[i].name;
					var desc = data.moduleVOList[i].desc;
					var visible = data.moduleVOList[i].visible;
					var imgPath = data.moduleVOList[i].imgPath;

					$(".panel table").append("<tr><td>" + id + "</td><td>" + name + "</td><td>" + desc + "</td><td><img src='" + imgPath + "' /></td><td>" + visible + "</td>" +
						"<td><a href='javascript:void(0);' id='js-editModule" + id + "'>修改</a><a href='javascript:void(0);' id='js-delModule" + id + "'>删除</a></td></tr>");

				}
				$("a[id^='js-editModule']").click(editModule);
				$("a[id^='js-delModule']").click(delModule);
			}
		});
	}

	function addModule() {
		$("#moduleDialog").dialog({
			closeOnEscape : false,
			open : function(event, ui) {
				$(".ui-dialog-titlebar-close").hide();
			},
			buttons: {
				"取消": function() {
					$(this).dialog("close");
					$(this).dialog("destroy");
					location.reload();
				},
				"添加": function() {
					getDataURL4Head();

					$.ajax({
						type: "post",
						url: "addModule.json",
						data: {
							"id": 0,
							"name": $.trim($("#js-name").val()),
							"imgPath": $.trim($("#js-imgPath").val()),
							"desc": $.trim($("#js-desc").val()),
							"visible": $("#js-visible").prop("checked")
						},
						dataType: "json",
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
			width: 600,
			modal: true,
			zIndex: 11
		});
	}

	function editModule() {
		var id = $.trim($(this).parent().parent().find('td').eq(0).text());
		var name = $.trim($(this).parent().parent().find('td').eq(1).text());
		var desc = $.trim($(this).parent().parent().find('td').eq(2).text());
		var imgPath = $.trim($(this).parent().parent().find('td').eq(3).find("img").attr("src"));
		var visible = $.trim($(this).parent().parent().find('td').eq(4).text());

		$("#js-id").val(id);
		$("#js-id").attr("readonly", true);
		$("#js-name").val(name);
		$(".preview").html("<img src='" + imgPath + "' />");
		$("input[name='imgPath']").val(imgPath);
		$("#js-desc").val(desc);
		$("#js-visible").attr("checked", visible == "true" ? true : false);


		$("#moduleDialog").dialog({
			closeOnEscape : false,
			open : function(event, ui) {
				$(".ui-dialog-titlebar-close").hide();
			},
			buttons: {
				"取消": function() {
					$(this).dialog("close");
					$(this).dialog("destroy");
					location.reload();
				},
				"修改": function() {
					getDataURL4Head();

					$.ajax({
						type: "post",
						url: "editModule.json",
						data: {
							"id": id,
							"name": $.trim($("#js-name").val()),
							"imgPath": $.trim($("#js-imgPath").val()),
							"desc": $.trim($("#js-desc").val()),
							"visible": $("#js-visible").prop("checked")
						},
						dataType: "json",
						error: function() {
							var txt = "修改模块失败!";
							window.wxc.xcConfirm(txt, "error");
						},
						success: function(data) {
							if (data != null) {
								if (data.success) {
									var txt = "修改模块成功！";
									window.wxc.xcConfirm(txt, "success", {
										onOk: function() {
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
			width: 600,
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
			dataType: "json",
			error: function() {
				var txt = "删除模块失败!";
				window.wxc.xcConfirm(txt, "error");
			},
			success: function(data) {
				if (data != null) {
					if (data.success) {
						var txt = "删除模块成功！";
						window.wxc.xcConfirm(txt, "success", {
							onOk: function() {
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

	function getDataURL4Head() {
		if ($(".filePanel img").attr("src").trim() == "") {
			return;
		}
		result = $image.cropper("getCroppedCanvas", {
			width: 100,
			height: 100
		}, "undefined");
		$("input[name='imgPath']").val(result.toDataURL());
	}
});