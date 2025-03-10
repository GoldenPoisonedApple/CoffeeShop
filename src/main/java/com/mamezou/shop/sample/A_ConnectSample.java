package com.mamezou.shop.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class A_ConnectSample {

	public static void main(String[] args) {
		// DB接続用定数
		String DATABASE_NAME = "world";
		String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		String URL = "jdbc:mysql://localhost/" + DATABASE_NAME + PROPATIES;
		// DB接続用・ユーザ定数
		String USER = "root";
		String PASS = "hedowigu";

		// データベースに接続
		// tryブロックから抜けるとConnectionオブジェクトは自動的にクローズされる
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// データベースに対する処理
			System.out.println("データベースに接続に成功");

			// データベースの情報を取得
			// バインド(SQLインジェクション対策)
			String query = "SELECT * FROM city where Population > ? limit ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				// セット
				ps.setInt(1, 100);
				ps.setInt(2, 10);

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