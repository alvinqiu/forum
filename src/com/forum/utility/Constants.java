package com.forum.utility;

public class Constants {

	public static final String LOGINED_USER = "logined_user";
	
	/**
	 * 1.公告
	 * 2.公共
	 * 3.待审核
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
	
	/**
	 * 1.管理员
	 * 2.版主
	 * 3.普通用户
	 */
	public enum GroupType {
		admin {
			public long getValue() {
				return 1;
			}
		},
		bz {
			public long getValue() {
				return 2;
			}
		},
		user {
			public long getValue() {
				return 3;
			}
		};

		public abstract long getValue();
	}
}
