package com.forum.utility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiniu.util.Auth;

@Controller
public class GetToken {
	
	@RequestMapping("/getToken.json")
	@ResponseBody
	public String getToken() {
		Auth auth = Auth.create("GBxC6qxubowS0RWEWRm2OvkIIGHnWkNcvQAx4IF6", "XZwqLm4OFHXQowZ831yabromxoX5vq16Z_TUKD_7");
		System.out.println(auth.uploadToken("a-a1"));
		return auth.uploadToken("a-a1");
	}
}
