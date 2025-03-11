package com.mamezou.shop.servlet;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/change-language")
public class ChangeLanguageServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエスト元URL取得
		String referer = request.getHeader("Referer");
		if (referer == null) {
			referer = "index.jsp";
		}
		// デバッグ用
		Logger logger = LogManager.getLogger(ChangeLanguageServlet.class.getName());
		logger.debug("Referer: " + referer);

		// 言語取得
		String language = request.getParameter("lang");

		// セッションオブジェクトを取得
		HttpSession session = request.getSession();
		// セッションスコープに言語設定
		session.setAttribute("language", language);

		// リクエスト元にリダイレクト
		response.sendRedirect(referer);
	}
	
}
