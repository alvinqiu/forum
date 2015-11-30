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

	$(document).keydown(
		function(e) {
			if (e.keyCode == 32) {
				var tag = $.trim($("input[name='tag']").val());
				var tagList = $.trim($("input[name='tags']").val());
				if (tag != "") {
					$(".tagPanel").append("<label class='tagStyle'>" + tag + "<label class='delTag'><a href='javascript:void(0)' onclick='delTag(this)'>X</a></label>" + "<label style='display:none'>" + tag + "</label></label>");
					$("input[name='tags']").val(tagList + tag + ",");
					$("input[name='tag']").val("");
				}
			}
		});

	$("#js-headFile").bind("click", function() {
		$("#pickfiles").click();
	});

	var dateTimeNow = new Date();

	for (var i = dateTimeNow.getFullYear(); i >= 1900; i--) {
		$("#js-year").append("<option value=" + i + ">" + i + "</option>");
	}
	for (var i = 1; i <= 12; i++) {
		$("#js-month").append("<option value=" + i + ">" + i + "</option>");
	}
	for (var i = 1; i <= 31; i++) {
		$("#js-day").append("<option value=" + i + ">" + i + "</option>");
	}

	$(".birthday select").blur(function() {
		var str = "";
		$(".birthday select").each(function(i) {
			if (str.length > 0) {
				str += "-";
			}
			str += $(this).val();
		});
		$("input[name='birthday']").val(str);
	});

	$.ajax({
		url: "preview.json",
		type: "post",
		dataType : "json",
		async: false,
		error: function(request) {
			var txt = "获取数据失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				var key;
				if (data != "{}") {
					key = 0;
					$(".preview").html("<img src='" + data.result.head + "' />");
					$("input[name='head']").val(data.result.head);
					$("input[name='nickName']").val(data.result.nickName);
					$("input[name='mobile']").val(data.result.mobile);
					$("input[type=radio][value=" + data.result.gender + "]").attr("checked", 'checked');
					$("input[name='birthday']").val(data.result.birthday);
					if (data.result.birthday != null) {
						var birth = data.result.birthday.split('-', -1);
						$("#js-year").val(birth[0]);
						$("#js-month").val(parseInt(birth[1]));
						$("#js-day").val(parseInt(birth[2]));
					}
					$(".point").children().next().text(data.result.point);

					// 标签
					for (var i = 0, tagLen = data.result.tags.split(',').length; i < tagLen; i++) {
						var tagList = $.trim($("input[name='tags']").val());
						var tag = data.result.tags.split(',')[i];

						if (tag != "") {
							$(".tagPanel").append("<label class='tagStyle'>" + tag + "<label class='delTag'><a href='javascript:void(0)' onclick='delTag(this)'>X</a></label><label style='display:none'>" + tag + "</label></label>");
							$("input[name='tags']").val(tagList + tag + ",");
						}
					}

				} else {
					key = -1;
				}
				$("form[name='previewForm']").attr("action", "editPreview.json?key=" + key);
			}
		}
	});

	$("#js-submit").click(function() {

		getDataURL4Head();

		var nickname = $("input[name='nickName']").val();
		var formDom = $("form[name='previewForm']");
		if (nickname != "") {
			$.ajax({
				type: "post",
				url: formDom.attr("action"),
				data: formDom.serialize(),
				async: false,
				dataType : "json",
				error: function(request) {
					var txt = "修改个人信息失败！";
					window.wxc.xcConfirm(txt, "error");
				},
				success: function(data) {
					if (data != null) {
						var txt = data.result;
						window.wxc.xcConfirm(txt, "info", {
							onOk : function() {
								if (data.success) {
									window.location.href = "./index.html";
								}
							}
						});
					}
				}
			});
		} else {
			var txt = "昵称不能为空！";
			window.wxc.xcConfirm(txt, "info");
		}
		return false;
	});
	
	$("#js-cancel").click(function() {
		var txt = "是否放弃修改";
		window.wxc.xcConfirm(txt, "confirm", {
			onOk : function() {
				history.go(-1);
			}
		});
	});

	function getDataURL4Head() {
		if ($(".filePanel img").attr("src").trim() == "") {
			return;
		}
		result = $image.cropper("getCroppedCanvas", {
			width: 100,
			height: 100
		}, "undefined");
		$("input[name='head']").val(result.toDataURL());
	}
});

function delTag(data) {
	var tagList = $.trim($("input[name='tags']").val());
	$("input[name='tags']").val(
		tagList.replace($(data).parent().next().text() + ",", ""));
	$(data).parent().parent().remove();
}