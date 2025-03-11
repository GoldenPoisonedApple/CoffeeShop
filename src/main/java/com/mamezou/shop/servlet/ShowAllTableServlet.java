package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.ItemDao;
import com.mamezou.shop.dataaccess.OrderDao;
import com.mamezou.shop.service.ItemManager;
import com.mamezou.shop.service.OrderManager;
import com.mamezou.shop.service.ServiceException;
import com.mamezou.shop.util.ApplicationProperties;
import com.mamezou.shop.util.Environment;

/**
 * 商品情報検索サーブレット
 * @author ito
 */
@WebServlet("/showAllTable")
public class ShowAllTableServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// テーブルの全データ取得
		try (ItemManager itemManager = new ItemManager(new ItemDao(ApplicationProperties.getInstance(Environment.PROD)));
			OrderManager orderManager = new OrderManager(new OrderDao(ApplicationProperties.getInstance(Environment.PROD)))
			) {
			
			// 商品情報取得
			request.setAttribute("items", itemManager.getAll());
			// 注文情報取得
			request.setAttribute("orders", orderManager.getAll());
			
		} catch (ServiceException | DaoException e) {
			// リクエストパラメータにエラー情報格納
			request.setAttribute("exception", e);
			// エラーページにフォワード
			request.getRequestDispatcher("exceptionMessage.jsp").forward(request, response);
		}

		// ページ遷移
		request.getRequestDispatcher("showAllTable.jsp").forward(request, response);
	}
}
