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
		Auth auth = Auth.create("LxjOBdjOjosbbQyeBnuTBswi2MpG06llf4wnzIm4", "E1Gx5rNmuGGRPCdcNf_Qs6E7FWnye68FAuc1x92z");
		//System.out.println(auth.uploadToken("a-a1"));
		return auth.uploadToken("community");
	}
}
