package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mamezou.shop.entity.Order;
import com.mamezou.shop.service.OrderManager;
import com.mamezou.shop.service.ServiceException;
import com.mamezou.shop.util.HtmlEscapeUtil;

/**
 * 商品情報検索サーブレット
 * @author ito
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストパラメータから原産地域取得
		request.setCharacterEncoding("UTF-8");
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String telNumber = request.getParameter("telNumber");
		// HTMLエスケープ処理
		name = HtmlEscapeUtil.escapeHtml(name);
		address = HtmlEscapeUtil.escapeHtml(address);
		telNumber = HtmlEscapeUtil.escapeHtml(telNumber);

		// 注文情報生成
		Order order = new Order(name, address, telNumber, itemId);

		// 注文情報登録
		int orderId = -1;
		try {
			orderId = new OrderManager().register(order);

		} catch (ServiceException e) {
			// リクエストパラメータにエラー情報格納
			request.setAttribute("exception", e);
			// エラーページにフォワード
			request.getRequestDispatcher("exceptionMessage.jsp").forward(request, response);
		}

		// リクエストパラメータに注文ID格納
		request.setAttribute("orderId", orderId);
		// 商品情報ページにフォワード
		request.getRequestDispatcher("orderComplete.jsp").forward(request, response);
	}
}
