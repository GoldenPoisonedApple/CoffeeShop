package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mamezou.shop.dataaccess.OrderDao;
import com.mamezou.shop.entity.Order;
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
		

	}
}
