$(function() {

	// 初始化验证
	window.gt_captcha_obj = new window.Geetest({
		gt: "df6595b204a06069670b68b6e716ca45",
		product: "popup",
		https: false
	});
	gt_captcha_obj.appendTo("#js-GeetestDiv").bindOn('#js-submit');

	// 实例化UEditor编辑器
	var ue = UE.getEditor('editor', {
		toolbars: [
    			[ 
    			'fontsize', 'fontfamily', 'bold', 'undo', 'cleardoc',
				'forecolor', 'simpleupload', 'underline', 'strikethrough',
				'justifyleft', 'justifyright', 'justifycenter',
				'justifyjustify', 'imagecenter', 'lineheight' 
				]
		],
		initialFrameWidth: 790,
		initialFrameHeight: 500,
		autoSyncData: false
	});

	// 获取所有类型
	$.ajax({
		url: "checkLogin.json",
		async: true,
		dataType : "json",
		error: function() {
			var txt = "获取类型失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data.success) {
				if (data.isAdmin) {
					var node = "<div class='type'>" +
							"<label>类型：</label>" +
							"<label><select name='type'>" +
							"<option value='1'>公告</option>" +
							"<option value='2' selected='selected'>普通帖</option>" +
							"</select></label></div>";
					$(node).insertBefore($(".content"));
				}
			}
		}
	});

	// 获取所有版块
	$.ajax({
		url: "getAllModule.json",
		async: true,
		dataType : "json",
		error: function() {
			var txt = "获取版块失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			for (var i = 0, tagLen = data.moduleVOList.length; i < tagLen; i++) {

				var id = data.moduleVOList[i].id;
				var name = data.moduleVOList[i].name;

				$(".module select").append("<option value='" + id + "'>" + name + "</option>");
			}
		}
	});

	$("input[type='checkbox']").click(function() {
		if ($(this).is(":checked")) {
			$(this).next().val("1");
		} else {
			$(this).next().val("0");
		}
	});

});

function checkForm() {
	var flag = false;
	var subject = $.trim($("input[name='subject']").val());
	var moduleId = $.trim($("select[name='moduleId']").val());
	var type = $.trim($("select[name='type']").val());

	$("input[name='content']").val($.trim(UE.getEditor('editor').getContent()));
	$("input[name='contentText']").val($.trim(UE.getEditor('editor').getContentTxt()));

	var content = $.trim($("input[name='content']").val());
	var contentText = $.trim($("input[name='contentText']").val());
	var txt;

	if (subject == "") {
		txt = "标题不能为空！";
	} else if (moduleId == "0") {
		txt = "请选择版块！";
	} else if (type == "0") {
		txt = "请选择类型！";
	} else if (content == "" || contentText == "") {
		txt = "请输入内容！";
	} else {
		flag = true;
	}

	if (flag) {
		gt_captcha_obj.enable();
		gt_captcha_obj.onSuccess(function() {
			addPost();
		});
		return true;
	} else {
		gt_captcha_obj.disable();
		window.wxc.xcConfirm(txt, "info");
		return false;
	}
}

//添加帖子
function addPost() {
	var formDom = $("form[name='postForm']");
	$.ajax({
		type: "post",
		url: "addPost.json",
		dataType : "json",
		data: formDom.serialize(),
		async: true,
		error: function(request) {
			var txt = "发布帖子失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != null) {
				var txt = data.result;
				window.wxc.xcConfirm(txt, "success", {
					onOk: function() {
						if (data.success) {
							window.location.href = "./index.html";
						}
					}
				});
			}
		}
	});
}