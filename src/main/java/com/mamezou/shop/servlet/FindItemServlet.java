package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mamezou.shop.dataaccess.ItemDao;
import com.mamezou.shop.service.ItemManager;
import com.mamezou.shop.service.ServiceException;
import com.mamezou.shop.util.ApplicationProperties;

/**
 * 商品情報検索サーブレット
 * @author ito
 */
@WebServlet("/findItem")
public class FindItemServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストパラメータから原産地域取得
		request.setCharacterEncoding("UTF-8");
		String area = request.getParameter("area");

		// 商品情報取得
		ItemManager itemManeger = new ItemManager(new ItemDao(ApplicationProperties.getInstance()));
		try {
			// リクエストパラメータに条件に合う商品情報格納
			request.setAttribute("itemList", itemManeger.findByArea(area));
			// 商品情報表示ページにフォワード
			request.getRequestDispatcher("itemList.jsp").forward(request, response);

		} catch (ServiceException e) {
			// リクエストパラメータにエラー情報格納
			request.setAttribute("exception", e);
			// エラーページにフォワード
			request.getRequestDispatcher("exceptionMessage.jsp").forward(request, response);
		}
	}
}
