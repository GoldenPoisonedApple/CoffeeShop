package com.mamezou.shop.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

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
		sqlFileRunner.runSqlScript("orders_0data.sql");

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
		// できない


		// // 事前条件 なし

		// // テスト用注文情報
		// Order order = new Order("氏名1", "住所1", "電話番号1", 1001);
		// // モック作成
		// // Act: DriverManager.getConnection() を呼び出す際にSQLExceptionをスローするように静的モックを設定
		// try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
		// 	mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
		// 			.thenThrow(new SQLException("Connection failure"));

		// 	// 検証
		// 	Exception exception = assertThrows(DaoException.class, () -> orderDao.insert(order));
		// 	assertTrue(exception.getMessage().contains("データベース関連エラー"));
		// }
	}
}
