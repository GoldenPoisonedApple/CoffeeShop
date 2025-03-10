/*
 * JdbcDriverLoadListener.java
 */
package com.mamezou.shop.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Webアプリケーション起動時に、Jdbcドライバをロードします．
 *
 * @author mamezou
 */
public class JdbcDriverLoadListener implements ServletContextListener {

	private static Logger logger = LogManager.getLogger(JdbcDriverLoadListener.class);

	/** ドライバクラス名 */
	protected String driverFqcn = null;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextListener.super.contextInitialized(sce);

		driverFqcn = sce.getServletContext().getInitParameter("jdbc-driver-class");
		if (driverFqcn != null && !driverFqcn.isBlank()) {
			logger.info("context-param jdbc-driver-class=[{}]", driverFqcn);
			try {
				Class.forName(driverFqcn);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("JDBCドライバのロードに失敗しました。[" + driverFqcn + "]", e);
			}
		}
	}

}
