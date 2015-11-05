$(function() {
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		cache : false,
		complete : function(XHR, TS) {
			var resText = XHR.responseText;
			var sessionstatus = XHR.getResponseHeader("sessionstatus");
			var loginPath = XHR.getResponseHeader("loginPath");
			if ("intercept" == sessionstatus) {
				var txt = "请先登录,确定要跳转至登录页吗？";
				window.wxc.xcConfirm(txt, "confirm", {
					onOk : function() {
						window.location.replace(loginPath);
					}
				});

				return false;
			}
		}
	});
});