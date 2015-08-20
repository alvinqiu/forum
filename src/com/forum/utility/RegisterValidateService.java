package com.forum.utility;

public class RegisterValidateService {
	
	/**
     * ���ͼ����ʼ�
     */
    public void processregister(String email,String url){
         
        ///�ʼ�������
		StringBuffer sb = new StringBuffer(
				"����������Ӽ����˺ţ�48Сʱ��Ч����������ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�<br>");
		sb.append(url + "/forum/activate.json?email=" + email);
         
        //�����ʼ�
        SendMail.send(email, sb.toString());
    }
}
