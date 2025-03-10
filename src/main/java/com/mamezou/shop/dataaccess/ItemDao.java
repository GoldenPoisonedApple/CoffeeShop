package com.mamezou.shop.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mamezou.shop.entity.Item;
import com.mamezou.shop.util.ApplicationProperties;

/**
 * 商品情報DAOクラス
 * ITEMSテーブルにアクセス
 * 
 * @author ito
 */
public class ItemDao {
	/** {@link #selectByArea(String)} で使用するSQL */
	private static final String SELECT_BY_AREA_SQL = "SELECT * FROM ITEMS WHERE AREA = ?";

	/** DB接続URL */
	private String url;
	/** DB接続ユーザ */
	private String user;
	/** DB接続ユーザパスワード */
	private String password;

	/**
	 * コンストラクタ
	 * 
	 * @param properties JDBC接続情報
	 */
	public ItemDao(ApplicationProperties properties) {

		url = properties.getDatabaseUrl();
		user = properties.getDatabaseUser();
		password = properties.getDatabasePassword();
	}

	/**
	 * 原産地域を検索条件としてコーヒー豆情報を取得する
	 * @param area 原産地域
	 * @return 商品情報リスト
	 * @throws DaoException SQLExeptionが発生した場合
	 */
	public List<Item> selectByArea(String area) throws DaoException {

		// DB接続
		try (Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = conn.prepareStatement(SELECT_BY_AREA_SQL)) {			
			// バインド
			ps.setString(1, area);

			// SQL実行
			try (ResultSet rs = ps.executeQuery()) {
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
				
				// 結果返却
				return items;
			}

		} catch (SQLException e) {
			throw new DaoException("データベース関連エラー", e);

		}
	}

}
