package com.mamezou.shop.dataaccess;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import com.mamezou.shop.entity.Order;

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
	public static void setUp() {
		// SQLファイルから実行
		sqlFileRunner = new SqlFileRunner();
	}

	/** 事後処理 */
	@AfterAll
	public static void tearDown() throws SQLException {
		// データベース初期化
		sqlFileRunner.runSqlScript("InitAll.sql");
	}

	/** テスト前処理 */
	@BeforeEach
	public void setUpEach() {
		// テスト対象クラス
		orderDao = new OrderDao();
	}

	/** テスト後処理 */
	@AfterEach
	public void tearDownEach() {

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
	public void testRegister_01() throws SQLException, DaoException {
		// 事前条件 テーブル初期化
		sqlFileRunner.runSqlScript("Orders_0data.sql");

		// テスト対象メソッドを実行
		Order expected = new Order("氏名1", "住所1", "電話番号1", 1001);
		int id = orderDao.insert(expected);

		// 期待結果
		assertEquals(2001, id);
		assertEquals(2001, expected.getId());

		// 事後結果取得
		List<Order> actuals = sqlFileRunner.getAllOrder();

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
		// 偽のURLを設定
		String url = "not connect url";
		// private変数のフィールドを取得
		Field field = orderDao.getClass().getDeclaredField("url");
		field.setAccessible(true); // アクセス制限を解除
		field.set(orderDao, url); // 値を設定

		// テスト対象メソッドを実行
		Order order = new Order("氏名1", "住所1", "電話番号1", 1001);
		Exception exception = assertThrows(DaoException.class, () -> orderDao.insert(order));
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
				new Order(2003, "氏名3", "住所3", "電話3", 1001));

		// 結果検証
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * java.sql.SQLExceptionが発生した場合
	 */
	@Test
	public void testSelectAll_03() throws Exception {
		// 偽のURLを設定
		String url = "not connect url";
		// private変数のフィールドを取得
		Field field = orderDao.getClass().getDeclaredField("url");
		field.setAccessible(true); // アクセス制限を解除
		field.set(orderDao, url); // 値を設定

		// テスト対象メソッドを実行
		Exception exception = assertThrows(DaoException.class, () -> orderDao.selectAll());
		assertTrue(exception.getMessage().contains("データベース関連エラー"));
	}
}
