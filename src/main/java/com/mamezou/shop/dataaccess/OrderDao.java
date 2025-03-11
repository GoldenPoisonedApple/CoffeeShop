package com.mamezou.shop.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mamezou.shop.entity.Order;
import com.mamezou.shop.util.ApplicationProperties;

/**
 * 注文情報DAOクラス
 * ORDERSテーブルにアクセス
 * 
 * @author ito
 */
public class OrderDao {
	/** {@link #insert(Order order)} で使用するSQL */
	private static final String INSERT_SQL = "INSERT INTO ORDERS (NAME, ADDRESS, TEL_NUMBER, ITEM_ID) VALUES (?, ?, ?, ?)";
	/** {@link #selectAll()} で使用するSQL */
	private static final String SELECT_ALL_SQL = "SELECT * FROM ORDERS";

	/** DB接続URL */
	private String url;
	/** DB接続ユーザ */
	private String user;
	/** DB接続パスワード */
	private String password;

	/**
	 * コンストラクタ
	 */
	public OrderDao() {
		// DB接続情報取得
		ApplicationProperties properties = ApplicationProperties.getInstance();
		url = properties.getDatabaseUrl();
		user = properties.getDatabaseUser();
		password = properties.getDatabasePassword();
	}


	/**
	 * 注文情報を登録する
	 * 
	 * @param order 注文情報 登録後にIDが設定される
	 * @return 登録した注文情報のID
	 * @throws DaoException SQLExeptionが発生した場合
	 */
	public int insert(Order order) throws DaoException {

		// DB接続
		try (Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			// バインド
			ps.setString(1, order.getName());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getTelNumber());
			ps.setInt(4, order.getItemId());

			// SQL実行
			ps.executeUpdate();

			// ID取得
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				int id = rs.getInt(1);
				// OrderオブジェクトにIDを設定
				order.setId(id);

				// IDを返す
				return id;
			}

		} catch (SQLException e) {
			throw new DaoException("データベース関連エラー", e);

		}
	}

	/**
	 * 全ての注文情報を取得する
	 * @return 注文情報リスト
	 * @throws DaoException SQLExeptionが発生した場合
	 */
	public List<Order> selectAll() throws DaoException {
		// DB接続
		try (Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {

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

		} catch (SQLException e) {
			throw new DaoException("データベース関連エラー", e);
		}
	}
}
