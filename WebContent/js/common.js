$(function() {
	// 当滚动条的位置处于距顶部150像素以下时，跳转链接出现，否则消失
	$(window).scroll(function() {
		if ($(window).scrollTop() > 150) {
			$("#js-scrolltop").css("left", document.body.clientWidth > 1366 ? (document.body.clientWidth - (document.body.clientWidth - 1200) / 2) : 1366 - (1366 - 1200) / 2);
			$("#js-scrolltop").fadeIn(700);
		} else {
			$("#js-scrolltop").fadeOut(700);
		}
	});

	// 当点击跳转链接后，回到页面顶部位置
	$("#js-scrolltop").click(function() {
		$('body,html').animate({
			scrollTop: 0
		}, 500);
		return false;
	});

	// 设置签到区日期
	$("#js-week").html(function() {
		var myDate = new Date();
		return myDate.toString(myDate);
	});

	//签到
	$("#js-signIn").click(signIn);

	//搜索
	$("#js_search_btn").click(search);

	//绑定搜索框
	$("#js_search_content").bind('keydown', function(event) {
		if (event.keyCode == 13) {
			$("#js_search_btn").trigger("click");
		}
	});

	//弹出框
	window.wxc = window.wxc || {};
	window.wxc.xcConfirm = function(popHtml, type, options) {
		var btnType = window.wxc.xcConfirm.btnEnum;
		var eventType = window.wxc.xcConfirm.eventEnum;
		var popType = {
			info: {
				title: "信息",
				icon: "0 0", //蓝色i
				btn: btnType.ok
			},
			success: {
				title: "成功",
				icon: "0 -48px", //绿色对勾
				btn: btnType.ok
			},
			error: {
				title: "错误",
				icon: "-48px -48px", //红色叉
				btn: btnType.ok
			},
			confirm: {
				title: "提示",
				icon: "-48px 0", //黄色问号
				btn: btnType.okcancel
			},
			warning: {
				title: "警告",
				icon: "0 -96px", //黄色叹号
				btn: btnType.okcancel
			},
			input: {
				title: "输入",
				icon: "",
				btn: btnType.ok
			},
			custom: {
				title: "",
				icon: "",
				btn: btnType.ok
			}
		};
		var itype = type ? type instanceof Object ? type : popType[type] || {} : {}; //格式化输入的参数:弹窗类型
		var config = $.extend(true, {
			//属性
			title: "", //自定义的标题
			icon: "", //图标
			btn: btnType.ok, //按钮,默认单按钮
			//事件
			onOk: $.noop, //点击确定的按钮回调
			onCancel: $.noop, //点击取消的按钮回调
			onClose: $.noop //弹窗关闭的回调,返回触发事件
		}, itype, options);

		var $txt = $("<p>").html(popHtml); //弹窗文本dom
		var $tt = $("<span>").addClass("tt").text(config.title); //标题
		var icon = config.icon;
		var $icon = icon ? $("<div>").addClass("bigIcon").css("backgroundPosition", icon) : "";
		var btn = config.btn; //按钮组生成参数

		var popId = creatPopId(); //弹窗索引

		var $box = $("<div>").addClass("xcConfirm"); //弹窗插件容器
		var $layer = $("<div>").addClass("xc_layer"); //遮罩层
		var $popBox = $("<div>").addClass("popBox"); //弹窗盒子
		var $ttBox = $("<div>").addClass("ttBox"); //弹窗顶部区域
		var $txtBox = $("<div>").addClass("txtBox"); //弹窗内容主体区
		var $btnArea = $("<div>").addClass("btnArea"); //按钮区域

		var $ok = $("<a>").addClass("sgBtn").addClass("ok").text("确定"); //确定按钮
		var $cancel = $("<a>").addClass("sgBtn").addClass("cancel").text("取消"); //取消按钮
		var $input = $("<input>").addClass("inputBox"); //输入框
		var $clsBtn = $("<a>").addClass("clsBtn"); //关闭按钮

		//建立按钮映射关系
		var btns = {
			ok: $ok,
			cancel: $cancel
		};

		init();

		function init() {
			//处理特殊类型input
			if (popType["input"] === itype) {
				$txt.append($input);
			}

			creatDom();
			bind();
		}

		function creatDom() {
			$popBox.append(
				$ttBox.append(
					$clsBtn
				).append(
					$tt
				)
			).append(
				$txtBox.append($icon).append($txt)
			).append(
				$btnArea.append(creatBtnGroup(btn))
			);
			$box.attr("id", popId).append($layer).append($popBox);
			$("body").append($box);
		}

		function bind() {
			//点击确认按钮
			$ok.click(doOk);

			//回车键触发确认按钮事件
			$(window).bind("keydown", function(e) {
				if (e.keyCode == 13) {
					if ($("#" + popId).length == 1) {
						doOk();
					}
				}
			});

			//点击取消按钮
			$cancel.click(doCancel);

			//点击关闭按钮
			$clsBtn.click(doClose);
		}

		//确认按钮事件
		function doOk() {
			var $o = $(this);
			var v = $.trim($input.val());
			if ($input.is(":visible"))
				config.onOk(v);
			else
				config.onOk();
			$("#" + popId).remove();
			config.onClose(eventType.ok);
		}

		//取消按钮事件
		function doCancel() {
			var $o = $(this);
			config.onCancel();
			$("#" + popId).remove();
			config.onClose(eventType.cancel);
		}

		//关闭按钮事件
		function doClose() {
			$("#" + popId).remove();
			config.onClose(eventType.close);
			$(window).unbind("keydown");
		}

		//生成按钮组
		function creatBtnGroup(tp) {
			var $bgp = $("<div>").addClass("btnGroup");
			$.each(btns, function(i, n) {
				if (btnType[i] == (tp & btnType[i])) {
					$bgp.append(n);
				}
			});
			return $bgp;
		}

		//重生popId,防止id重复
		function creatPopId() {
			var i = "pop_" + (new Date()).getTime() + parseInt(Math.random() * 100000); //弹窗索引
			if ($("#" + i).length > 0) {
				return creatPopId();
			} else {
				return i;
			}
		}
	};

	//按钮类型
	window.wxc.xcConfirm.btnEnum = {
		ok: parseInt("0001", 2), //确定按钮
		cancel: parseInt("0010", 2), //取消按钮
		okcancel: parseInt("0011", 2) //确定&&取消
	};

	//触发事件类型
	window.wxc.xcConfirm.eventEnum = {
		ok: 1,
		cancel: 2,
		close: 3
	};

	//弹窗类型
	window.wxc.xcConfirm.typeEnum = {
		info: "info",
		success: "success",
		error: "error",
		confirm: "confirm",
		warning: "warning",
		input: "input",
		custom: "custom"
	};


	// 查询是否登录
	$.ajax({
		url: "checkLogin.json",
		async: true,
		error: function() {
			var txt = "请重新登录！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var jsonObj = eval("(" + data + ")");
			$("#js-checkLogin ul").empty();
			if (jsonObj.success) {
				$("#js-checkLogin ul").append("<li><a id='username'>" + jsonObj.name + "</a>" +
					"<div class='panel_top_content_menu'>" +
					"<p><a href='./editPreview.html'>我的信息</a></p>" +
					"<p><a href='./myPost.html?page=1'>我的帖子</a></p>" +
					"<p><a href='./myReply.html?page=1'>我的回复</a></p>" +
					"<p><a href='javascript:void(0);' onclick='loginOut()'>退出账户</a></p>" +
					"</div></li>");
			} else {
				$("#js-checkLogin ul").append("<li><a href='login.html'>登录</a></li><li> | </li><li><a href='register.html'>注册</a></li>");
			}

			if (jsonObj.isAdmin) {
				$("#js-manage").show();
			}

			if (jsonObj.isSignIn) {
				$("#signin_status").text("已签到");
			} else {
				$("#signin_status").text("签 到");
			}

			//鼠标移入用户名
			$("#username").parent().mouseenter(function() {
				$(".panel_top_content_menu").css("display", 'block');
			}).mouseleave(function() {
				$(".panel_top_content_menu").css("display", 'none');
			});

		}
	});
});

Date.prototype.toString = function(showWeek) {
	var myDate = this;
	if (showWeek) {
		var Week = ['日', '一', '二', '三', '四', '五', '六'];
		return '星期' + Week[myDate.getDay()];
	}
}

//退出
function loginOut() {
	$.ajax({
		url: "loginOut.json",
		type: "post",
		cache: false,
		error: function() {
			var txt = "退出失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			var jsonObj = eval("(" + data + ")");
			if (jsonObj) {
				location.reload();
			}
		}
	});
}

// 签到
function signIn() {
	$.ajax({
		url: "signIn.json",
		error: function() {
			var txt = "签到失败！";
			window.wxc.xcConfirm(txt, "error");
		},
		success: function(data) {
			if (data != "") {
				var jsonObj = eval("(" + data + ")");
				if (typeof(jsonObj.result) != "undefined") {
					var txt = jsonObj.result;
					window.wxc.xcConfirm(txt, "info");
				}

				if (jsonObj.success) {
					$("#signin_status").text("已签到");
				}
			}
		}
	});
}

// 搜索
function search() {
	var _this = $(this).prev();
	var _thisVal = $.trim(_this.val());
	if (_thisVal != "") {
		location.href = "./search.html?kw=" + _thisVal + "&page=1";
	} else {
		var txt = "请输入要搜索的内容！";
		window.wxc.xcConfirm(txt, "info", {
			onOk: function() {
				_this.focus();
			}
		});
	}
}