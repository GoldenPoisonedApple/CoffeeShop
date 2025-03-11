package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mamezou.shop.service.ItemManager;
import com.mamezou.shop.service.OrderManager;
import com.mamezou.shop.service.ServiceException;

/**
 * 商品情報検索サーブレット
 * @author ito
 */
@WebServlet("/showAllTable")
public class ShowAllTableServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// テーブルの全データ取得
		try {
			// 商品情報取得
			request.setAttribute("items", new ItemManager().getAll());
			// 注文情報取得
			request.setAttribute("orders", new OrderManager().getAll());
			
		} catch (ServiceException e) {
			// リクエストパラメータにエラー情報格納
			request.setAttribute("exception", e);
			// エラーページにフォワード
			request.getRequestDispatcher("exceptionMessage.jsp").forward(request, response);
		}

		// ページ遷移
		request.getRequestDispatcher("showAllTable.jsp").forward(request, response);
	}
}
