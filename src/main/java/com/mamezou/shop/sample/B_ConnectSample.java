package com.mamezou.shop.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mamezou.shop.util.ApplicationProperties;
import com.mamezou.shop.util.Environment;

public class B_ConnectSample {

	public static void main(String[] args) {
		ApplicationProperties properties = ApplicationProperties.getInstance(Environment.PROD);
		// DB接続用定数
		String URL = properties.getDatabaseUrl();
		String USER = properties.getDatabaseUser();
		String PASS = properties.getDatabasePassword();

		System.out.println("URL: " + URL);
		System.out.println("USER: " + USER);
		System.out.println("PASS: " + PASS);

		// データベースに接続
		// tryブロックから抜けるとConnectionオブジェクトは自動的にクローズされる
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// データベースに対する処理
			System.out.println("データベースに接続に成功");

			// データベースの情報を取得
			// バインド(SQLインジェクション対策)
			String query = "SELECT * FROM items";
			try (PreparedStatement ps = conn.prepareStatement(query)) {

				// SQLの実行
				try (ResultSet rs = ps.executeQuery()) {
					// 結果の表示
					while (rs.next()) {
						System.out.println(rs.getString("Name"));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}