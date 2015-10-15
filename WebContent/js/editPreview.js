$(function() {
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
			alert("获取数据失败！" + request)
		},
		success: function(data) {
			if (data != "") {
				var key;
				if (data != "{}") {
					key = 0;
					var jsonObj = eval("(" + data + ")");
					$(".filePanel").html("<img src='" + jsonObj.result.head + "' />");
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
		var nickname = $("input[name='nickName']").val();
		var formDom = $("form[name='previewForm']");
		if (nickname != "") {
			$.ajax({
				type : "post",
				url : formDom.attr("action"),
				data : formDom.serialize(),
				async : false,
				error : function(request) {
					alert("修改个人信息失败！" + request);
				},
				success : function(data) {
					var jsonObj = eval("(" + data + ")");
					alert(jsonObj.result);
					if (jsonObj.success) {
						window.location.href = "./index.html";
					}
				}
			});
		} else {
			alert("昵称不能为空！");
		}
		return false;
	});

	$.ajax({
		url: "getToken.json",
		type: "get",
		async: false,
		success: function(data) {
			// 上传头像至七牛
			var uploader = Qiniu.uploader({
				runtimes: 'html5,flash,html4', // 上传模式,依次退化
				browse_button: 'pickfiles', // 上传选择的点选按钮，**必需**
				// uptoken_url: '/token',
				// //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
				uptoken: data, // 若未指定uptoken_url,则必须指定 uptoken
				// ,uptoken由其他程序生成
				unique_names: true, // 默认
				// false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
				// save_key: true, // 默认
				// false。若在服务端生成uptoken的上传策略中指定了
				// `sava_key`，则开启，SDK会忽略对key的处理
				domain: '7xjhdq.com2.z0.glb.qiniucdn.com', // '7xil4n.com1.z0.glb.clouddn.com',
				// //
				// bucket
				// 域名，下载资源时用到，**必需**
				// container: 'container', //上传区域DOM
				// ID，默认是browser_button的父元素，
				max_file_size: '2mb', // 最大文件体积限制
				max_retries: 3, // 上传失败最大重试次数
				// dragdrop: true, //开启可拖曳上传
				// drop_element: 'container',
				// //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
				chunk_size: '1mb', // 分块上传时，每片的体积
				auto_start: true, // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
				init: {
					'FilesAdded': function(up, files) {
						plupload.each(files, function(file) {
							// 文件添加进队列后,处理相关的事情
						});
					},
					'BeforeUpload': function(up, file) {
						// 每个文件上传前,处理相关的事情
					},
					'UploadProgress': function(up, file) {
						// 每个文件上传时,处理相关的事情
					},
					'FileUploaded': function(up, file, info) {
						// 每个文件上传成功后,处理相关的事情
						var domain = up.getOption('domain');
						var res = jQuery.parseJSON(info);
						var sourceLink = "http://" + domain + "/" + res.key; // 获取上传成功后的文件的Url
						$(".filePanel").html("<img src='" + sourceLink + "' onload='AutoResizeImage(150,150,this)' />");
						$("input[name='head']").val(sourceLink);
					},
					'Error': function(up, err, errTip) {
						alert(errTip);
						// 上传出错时,处理相关的事情
					},
					'UploadComplete': function() {
						// 队列文件处理完毕后,处理相关的事情
					},
					'Key': function(up, file) {
						// 若想在前端对每个文件的key进行个性化处理，可以配置该函数
						// 该配置必须要在 unique_names: false ,
						// save_key: false 时才生效

						var key = "";
						// do something with key here
						return key
					}
				}
			});
			// domain
			// 为七牛空间（bucket)对应的域名，选择某个空间后，可通过"空间设置->基本设置->域名设置"查看获取
			// uploader
			// 为一个plupload对象，继承了所有plupload的方法，参考http://plupload.com/docs
		}
	});
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
	} else if (maxWidth == 0) {//
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
