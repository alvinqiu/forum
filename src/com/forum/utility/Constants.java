package com.forum.utility;

public class Constants {

	public static final String LOGINED_USER = "logined_user";
	
	/**
	 * 1.����
	 * 2.��ͨ��
	 * 3.�����
	 */
	public enum PostType {
		notice {
			public String getValue() {
				return "1";
			}
		},
		common {
			public String getValue() {
				return "2";
			}
		},
		authstr {
			public String getValue() {
				return "3";
			}
		};

		public abstract String getValue();
	}
}
