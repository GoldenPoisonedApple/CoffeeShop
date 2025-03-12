package com.mamezou.shop.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * HTMLエスケープユーティリティ
 * @author ito
 */
public class HtmlEscapeUtil {

	private static final Map<Character, String> ESCAPE_MAP;

	// エスケープマップの初期化
	static {
		Map<Character, String> map = new HashMap<>();
		map.put('&', "&amp;");
		map.put('<', "&lt;");
		map.put('>', "&gt;");
		map.put('"', "&quot;");
		map.put('\'', "&#39;");
		ESCAPE_MAP = Collections.unmodifiableMap(map);
	}

	private HtmlEscapeUtil() {
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * HTMLエスケープ処理
	 * @param input 入力文字列
	 * @return エスケープ後の文字列
	 */
	public static String escapeHtml(String input) {
		if (input == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (char c : input.toCharArray()) {
			String replacement = ESCAPE_MAP.get(c);
			if (replacement != null) {
				builder.append(replacement);
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}
