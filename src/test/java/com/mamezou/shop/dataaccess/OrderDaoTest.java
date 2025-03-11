package com.mamezou.shop.dataaccess;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;

import com.mamezou.shop.entity.Order;
import com.mamezou.shop.entity.Order;
import com.mamezou.shop.util.ApplicationProperties;
import com.mamezou.shop.util.Environment;

/**
 * {@link com.mamezou.shop.dataaccess.OrderDao}のテストクラス
 * 
 * @author ito
 */
public class OrderDaoTest {
	/** テスト対象クラス */
	private OrderDao orderDao;
	/** SQLファイルから実行 */
	private static SqlFileRunner sqlFileRunner;

	/** 事前処理 */
	@BeforeAll
	public static void setUp() throws SQLException {
		// SQLファイルから実行
		sqlFileRunner = new SqlFileRunner(ApplicationProperties.getInstance(Environment.TEST));
	}

	/** 事後処理 */
	@AfterAll
	public static void tearDown() throws SQLException {
		// データベース初期化
		sqlFileRunner.runSqlScript("InitAll.sql");
		// リソース解放
		sqlFileRunner.close();
	}

	/** テスト前処理 */
	@BeforeEach
	public void setUpEach() throws DaoException {
		// テスト対象クラス
		orderDao = new OrderDao(ApplicationProperties.getInstance(Environment.TEST));
	}
	/** テスト後処理 */
	@AfterEach
	public void tearDownEach() throws DaoException {
		// リソース解放
		orderDao.close();
	}

	// ------------------------------
	// registerメソッドのテスト
	// 注文情報を登録する
	// ------------------------------
	/**
	 * 正常系
	 * 注文情報を登録
	 * 
	 * 戻り値が登録した注文情報のIDであること
	 * 登録した注文情報に登録注文情報のIDが設定されていること
	 * 
	 * 登録した注文情報がDBに登録されていること
	 */
	@Test
	public void testRegister_01() throws Exception {
		// 事前条件 テーブル初期化
		sqlFileRunner.runSqlScript("Orders_0data.sql");

		// テスト対象メソッドを実行
		Order expected = new Order("氏名1", "住所1", "電話番号1", 1001);
		int id = orderDao.insert(expected);

		// 期待結果
		assertEquals(2001, id);
		assertEquals(2001, expected.getId());

		// 事後結果取得
		ResultSet rs = sqlFileRunner.getTableData("ORDERS");
		List<Order> actuals = new ArrayList<>();
		while (rs.next()) {
			Order order = new Order(
					rs.getInt("ID"),
					rs.getString("NAME"),
					rs.getString("ADDRESS"),
					rs.getString("TEL_NUMBER"),
					rs.getInt("ITEM_ID"));
			actuals.add(order);
		}
		// 事後結果検証
		assertEquals(1, actuals.size());
		Order actual = actuals.get(0);
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * DB接続に失敗
	 * 
	 * DaoExceptionが発生すること
	 */
	@Test
	public void testRegister_02() throws Exception {
		// スタブの作成
		Connection conn = mock(Connection.class);
		// スタブの動作を定義
		try {
			when(conn.prepareStatement(any(), anyInt())).thenThrow(new SQLException("テスト用例外"));
		} catch (SQLException e) {
			fail(e);
		}

		// テスト対象クラスにスタブを注入
		// private変数のフィールドを取得
		Field field = orderDao.getClass().getDeclaredField("conn");
		// private変数へのアクセス制限を解除
		field.setAccessible(true);
		// private変数に値を設定
		field.set(orderDao, conn);

		// テスト対象メソッドを実行
		Order test = new Order("氏名1", "住所1", "電話番号1", 1001);
		Exception exception = assertThrows(DaoException.class, () -> orderDao.insert(test));
		assertTrue(exception.getMessage().contains("データベース関連エラー"));
	}

	// ------------------------------
	// selectAllメソッドのテスト
	// 全ての商品情報を取得する
	// ------------------------------
	/**
	 * 正常系
	 * 商品情報が1件もない場合
	 */
	@Test
	public void testSelectAll_02() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Orders_0data.sql");

		// テスト対象メソッドを実行
		List<Order> actual = orderDao.selectAll();

		// 結果検証
		assertEquals(0, actual.size());
	}

	/**
	 * 正常系
	 * 商品情報が1件以上ある場合(3件)
	 */
	@Test
	public void testSelectAll_01() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Orders_3data.sql");

		// テスト対象メソッドを実行
		List<Order> actual = orderDao.selectAll();

		// 期待値作成
		List<Order> expected = Arrays.asList(
			new Order(2001, "氏名1", "住所1", "電話1", 1001),
			new Order(2002, "氏名2", "住所2", "電話2", 1002),
			new Order(2003, "氏名3", "住所3", "電話3", 1001)
		);

		// 結果検証
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * java.sql.SQLExceptionが発生した場合
	 */
	@Test
	public void testSelectAll_03() throws Exception {
		// スタブの作成
		Connection conn = mock(Connection.class);
		// スタブの動作を定義
		try {
			when(conn.prepareStatement(any())).thenThrow(new SQLException("テスト用例外"));
		} catch (SQLException e) {
			fail(e);
		}

		// テスト対象クラスにスタブを注入
		// private変数のフィールドを取得
		Field field = orderDao.getClass().getDeclaredField("conn");
		// private変数へのアクセス制限を解除
		field.setAccessible(true);
		// private変数に値を設定
		field.set(orderDao, conn);

		// テスト対象メソッドを実行
		Exception exception = assertThrows(DaoException.class, () -> orderDao.selectAll());
		assertTrue(exception.getMessage().contains("データベース関連エラー"));
	}
}
