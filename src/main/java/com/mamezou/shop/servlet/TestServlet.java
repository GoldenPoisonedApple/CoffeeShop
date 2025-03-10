package com.mamezou.shop.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mamezou.shop.sample.LogTest;


@WebServlet("/test")
public class TestServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Logger logger = LogManager.getLogger(TestServlet.class);
		logger.error("Error message");
		logger.warn("Warn message");
		logger.info("Info message");
		logger.debug("Debug message");
		logger.trace("Trace message");

		new LogTest().act();

		response.getWriter().println("Hello, World!");
	}
}
