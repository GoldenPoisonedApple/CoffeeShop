package com.mamezou.shop.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * HtmlEscapeUtilのテストクラス
 * @see HtmlEscapeUtil
 */
public class HtmlEscapeUtilTest {
	

	// --------------------------------------------------
	// String escapeHtml(String input)
	// HTMLエスケープ処理
	// --------------------------------------------------
	/**
	 * 正常系
	 * 入力文字列がnullの場合、空文字列が返却されること
	 */
	@Test
	public void testEscapeHtml_InputNull() {
		// テスト対象メソッド実行
		String actual = HtmlEscapeUtil.escapeHtml(null);
		// 実行結果確認
		assertEquals("", actual);
	}

	/**
	 * 正常系
	 * 入力文字列が空文字の場合、空文字列が返却されること
	 */
	@Test
	public void testEscapeHtml_InputEmpty() {
		// テスト対象メソッド実行
		String actual = HtmlEscapeUtil.escapeHtml("");
		// 実行結果確認
		assertEquals("", actual);
	}

	/**
	 * 正常系
	 * 入力文字列にエスケープ対象文字が含まれている場合、エスケープされた文字列が返却されること
	 */
	@Test
	public void testEscapeHtml_EscapeTarget() {
		// テスト対象メソッド実行
		String actual = HtmlEscapeUtil.escapeHtml("<script>alert('こんにちは');</script>");
		// 実行結果確認
		assertEquals("&lt;script&gt;alert(&#39;こんにちは&#39;);&lt;/script&gt;", actual);
	}
}
