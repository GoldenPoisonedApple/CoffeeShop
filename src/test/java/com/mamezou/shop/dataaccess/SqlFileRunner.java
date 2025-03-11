package com.mamezou.shop.dataaccess;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.mamezou.shop.entity.Item;
import com.mamezou.shop.entity.Order;
import com.mamezou.shop.util.ApplicationProperties;

/**
 * SQLファイルを実行するクラス
 * テスト用
 */
public class SqlFileRunner {
	/** DB接続URL */
	private String url;
	/** DB接続ユーザ */
	private String user;
	/** DB接続パスワード */
	private String password;

	/**
	 * コンストラクタ
	 * 
	 * @param properties DB接続情報
	 */
	public SqlFileRunner() {
		// DB接続情報取得
		ApplicationProperties properties = ApplicationProperties.getInstance();
		url = properties.getDatabaseUrl();
		user = properties.getDatabaseUser();
		password = properties.getDatabasePassword();
	}

	/**
	 * 指定したリソース名のSQLスクリプトを実行する.
	 * リソースファイルはCLASSPATHに含まれていること.
	 * 
	 * @param resourceName 実行するSQLスクリプトのリソース名
	 * @throws SQLException リソースが見つからない場合
	 */
	public void runSqlScript(String resourceName) throws SQLException {
		// DB接続
		try (Connection conn = DriverManager.getConnection(url, user, password)) {

			// ScriptRunnerを使用してSQLファイルを実行
			ScriptRunner scriptRunner = new ScriptRunner(conn);
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourceName);
			if (inputStream == null) {
				throw new SQLException("リソースが見つかりません:" + resourceName);
			}
			scriptRunner.runScript(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		}
	}

	/**
	 * 商品情報を全件取得する
	 * @return 商品情報リスト
	 * @throws SQLException SQL例外が発生した場合
	 */
	public List<Item> getAllItem() throws SQLException {
		// DB接続
		// SQL実行
		try (Connection conn = DriverManager.getConnection(url, user, password);
				ResultSet rs = conn.prepareStatement("SELECT * FROM ITEMS").executeQuery()) {
			
			// 結果取得
			List<Item> items = new ArrayList<>();
			while (rs.next()) {
				Item item = new Item(
						rs.getInt("ID"),
						rs.getString("NAME"),
						rs.getString("AREA"),
						rs.getString("ORIGINAL_HOME"),
						rs.getInt("PRICE"));
				items.add(item);
			}
			return items;
		}
	}

	/**
	 * 注文情報を全件取得する
	 * @return 注文情報リスト
	 * @throws SQLException SQL例外が発生した場合
	 */
	public List<Order> getAllOrder() throws SQLException {
		// DB接続
		// SQL実行
		try (Connection conn = DriverManager.getConnection(url, user, password);
				ResultSet rs = conn.prepareStatement("SELECT * FROM ORDERS").executeQuery()) {
			
			// 結果取得
			List<Order> orders = new ArrayList<>();
			while (rs.next()) {
				Order order = new Order(
						rs.getInt("ID"),
						rs.getString("NAME"),
						rs.getString("ADDRESS"),
						rs.getString("TEL_NUMBER"),
						rs.getInt("ITEM_ID"));
				orders.add(order);
			}
			return orders;
		}
	}

}
