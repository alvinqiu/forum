package com.forum.utility;

import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

public class Constants {

	public static final String LOGINED_USER = "logined_user";

	/**
	 * 1.公告 2.公共 3.待审核
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
	 * 1.管理员 2.版主 3.普通用户
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

	/**
	 * json字符串转map集合
	 *
	 * @param jsonStrjson字符串
	 * @param map接收的map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String jsonStr, Map<String, Object> map) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		map = JSONObject.fromObject(jsonObject);
		// 递归map的value,如果
		for (Entry<String, Object> entry : map.entrySet()) {
			json2map1(entry, map);
		}
		return map;
	}

	/**
	 * json转map,递归调用的方法
	 *
	 * @param entry
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2map1(Entry<String, Object> entry,
			Map<String, Object> map) {
		if (entry.getValue() instanceof Map) {
			JSONObject jsonObject = JSONObject.fromObject(entry.getValue());

			Map<String, Object> map1 = JSONObject.fromObject(jsonObject);

			for (Entry<String, Object> entry1 : map1.entrySet()) {
				map1 = json2map1(entry1, map1);
				map.put(entry.getKey(), map1);
			}
		}
		return map;
	}
}
