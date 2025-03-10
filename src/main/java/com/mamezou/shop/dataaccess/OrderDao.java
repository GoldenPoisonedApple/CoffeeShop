package com.mamezou.shop.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public OrderDao(ApplicationProperties properties) {
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
}
