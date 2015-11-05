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
		async: false,
		error: function(request) {
			var txt = "获取数据失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != "") {
				var key;
				if (data != "{}") {
					key = 0;
					var jsonObj = eval("(" + data + ")");
					$(".preview").html("<img src='" + jsonObj.result.head + "' />");
					$("input[name='head']").val(jsonObj.result.head);
					$("input[name='nickName']").val(jsonObj.result.nickName);
					$("input[name='mobile']").val(jsonObj.result.mobile);
					$("input[type=radio][value=" + jsonObj.result.gender + "]").attr("checked", 'checked');
					$("input[name='birthday']").val(jsonObj.result.birthday);
					if (jsonObj.result.birthday != null) {
						var birth = jsonObj.result.birthday.split('-', -1);
						$("#js-year").val(birth[0]);
						$("#js-month").val(parseInt(birth[1]));
						$("#js-day").val(parseInt(birth[2]));
					}
					$(".point").children().next().text(
						jsonObj.result.point);

					// 标签
					for (var i = 0, tagLen = jsonObj.result.tags.split(',').length; i < tagLen; i++) {
						var tagList = $.trim($("input[name='tags']").val());
						var tag = jsonObj.result.tags.split(',')[i];

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
				error: function(request) {
					var txt = "修改个人信息失败！";
					window.wxc.xcConfirm(txt, "error");
				},
				success: function(data) {
					var jsonObj = eval("(" + data + ")");
					var txt = jsonObj.result;
					window.wxc.xcConfirm(txt, "info", {
						onOk : function() {
							if (jsonObj.success) {
								window.location.href = "./index.html";
							}
						}
					});

				}
			});
		} else {
			var txt = "昵称不能为空！";
			window.wxc.xcConfirm(txt, "info");
		}
		return false;
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

function AutoResizeImage(maxWidth, maxHeight, objImg) {
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth == 0 && maxHeight == 0) {
		Ratio = 1;
	} else if (maxWidth == 0) { //
		if (hRatio < 1)
			Ratio = hRatio;
	} else if (maxHeight == 0) {
		if (wRatio < 1)
			Ratio = wRatio;
	} else if (wRatio < 1 || hRatio < 1) {
		Ratio = (wRatio <= hRatio ? wRatio : hRatio);
	}
	if (Ratio < 1) {
		w = w * Ratio;
		h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
}