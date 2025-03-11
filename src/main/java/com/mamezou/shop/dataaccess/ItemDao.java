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
public class ItemDao implements AutoCloseable {
	/** {@link #selectByArea(String)} で使用するSQL */
	private static final String SELECT_BY_AREA_SQL = "SELECT * FROM ITEMS WHERE AREA = ?";
	/** {@link #selectAll()} で使用するSQL */
	private static final String SELECT_ALL_SQL = "SELECT * FROM ITEMS";

	/** DB接続 */
	private Connection conn;

	/**
	 * コンストラクタ
	 * @param properties DB接続情報
	 * @throws DaoException DBに接続できない場合
	 */
	public ItemDao(ApplicationProperties properties) throws DaoException {
		// DB接続情報取得
		String url = properties.getDatabaseUrl();
		String user = properties.getDatabaseUser();
		String password = properties.getDatabasePassword();

		// DB接続
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DaoException("DB接続に失敗しました", e);
		}
	}


	/**
	 * DB接続をクローズする
	 * @throws DaoException クローズできない場合
	 */
	@Override
	public void close() throws DaoException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DaoException("DB接続の切断に失敗しました", e);
		}
	}

	/**
	 * 原産地域を検索条件としてコーヒー豆情報を取得する
	 * @param area 原産地域
	 * @return 商品情報リスト
	 * @throws DaoException SQLExeptionが発生した場合
	 */
	public List<Item> selectByArea(String area) throws DaoException {

		// DB接続
		try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_AREA_SQL)) {			
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

	/**
	 * 全てのコーヒー豆情報を取得する
	 * @return 商品情報リスト
	 * @throws DaoException SQLExeptionが発生した場合
	 */
	public List<Item> selectAll() throws DaoException {
		// DB接続
		try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL)) {
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
