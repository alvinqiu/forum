$(function() {
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		cache : false,
		complete : function(XHR, TS) {
			var resText = XHR.responseText;
			var sessionstatus = XHR.getResponseHeader("sessionstatus");
			var loginPath = XHR.getResponseHeader("loginPath");
			if ("intercept" == sessionstatus) {
				alert("请先登录！");
				window.location.replace(loginPath);
				return false;
			}
		}
	});
});