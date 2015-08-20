package com.forum.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ��֤�˻�
 */
@Service
@Transactional
public class Verify {
	public boolean check(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		if (session.getAttribute(Constants.LOGINED_USER) == null) {
			return false;
		} else {
			return true;
		}

	}
}
