$(function() {

	// 初始化验证
	var gt_captcha_obj = new window.Geetest({
		gt : "df6595b204a06069670b68b6e716ca45",
		product : "popup",
		https : false
	});
	gt_captcha_obj.appendTo("#js-GeetestDiv").bindOn('#js-submit');

	// 实例化UEditor编辑器
	var ue = UE.getEditor('editor', {
		toolbars : [ [ 'fontsize', 'fontfamily', 'bold', 'undo', 'cleardoc',
				'forecolor', 'simpleupload' ] ],
		initialFrameWidth : 790,
		initialFrameHeight : 500,
		autoSyncData : false
	});

	// 获取所有版块
	$.ajax({
				url : "getAllModule.json",
				error : function() {
					alert("获取数据失败！");
				},
				success : function(data) {
					var jsonObj = eval("(" + data + ")");

					for (var i = 0, tagLen = jsonObj.moduleVOList.length; i < tagLen; i++) {

						var id = jsonObj.moduleVOList[i].id;
						var name = jsonObj.moduleVOList[i].name;

						$(".module select").append(
								"<option value='" + id + "'>" + name
										+ "</option>");

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

	gt_captcha_obj.onSuccess(function() {
		var formDom = $("form[name='postForm']");
		if (checkForm()) {
			$.ajax({
				type : "get",
				url : "addPost.json",
				data : formDom.serialize(),
				async : true,
				error : function(request) {
					alert("发布帖子失败！" + request);
				},
				success : function(data) {
					var jsonObj = eval("(" + data + ")");
					alert(jsonObj.result);
					if (jsonObj.success) {
						window.location.href = "/forum/index.html";
					}
				}
			});
		}
	});

});

function checkForm() {
	var subject = $.trim($("input[name='subject']").val());
	var moduleId = $.trim($("select[name='moduleId']").val());
	var type = $.trim($("select[name='type']").val());
	$("input[name='content']")
			.val($.trim(UE.getEditor('editor').getPlainTxt()));
	var content = $.trim($("input[name='content']").val());

	if (subject == "") {
		alert("标题不能为空！");
		return false;
	} else if (moduleId == "0") {
		alert("请选择版块！");
		return false;
	} else if (type == "0") {
		alert("请选择类型！");
		return false;
	} else if (content == "") {
		alert("请输入内容！");
		return false;
	} else {
		return true;
	}
}
