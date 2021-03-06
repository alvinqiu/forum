package com.forum.utility;

public class RegisterValidateService {

	/**
	 * 发送激活邮件
	 */
	public void processregister(String email, String url) {

		// 添加邮件标题
		String subject = "账号激活邮件";

		// 添加邮件内容
		StringBuffer sb = new StringBuffer(
				"点击下面链接激活账号，24小时内有效，否则重新注册账号，请尽快激活！<br>");
		sb.append("<a href='" + url + "/activate.json?email=" + email
				+ "' target='_blank'>" + url + "/activate.json?email=" + email
				+ "</a>");

		// 发送邮件
		SendMail.send(email, subject, sb.toString());
	}
}
