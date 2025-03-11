package com.mamezou.shop.dataaccess;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import com.mamezou.shop.entity.Item;

/**
 * {@link com.mamezou.shop.dataaccess.ItemDao}のテストクラス
 * 
 * @author ito
 */
public class ItemDaoTest {
	/** テスト対象クラス */
	private ItemDao itemDao;
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
		itemDao = new ItemDao();
	}
	/** テスト後処理 */
	@AfterEach
	public void tearDownEach() {

	}


	// ------------------------------
	// selectByAreaメソッドのテスト
	// 原産地域を指定して商品情報を取得する
	// ------------------------------
	/**
	 * 正常系
	 * 検索結果が1件もない場合
	 */
	@Test
	public void testSelectByArea_02() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Items_3data.sql");

		// テスト対象メソッドを実行
		List<Item> actual = itemDao.selectByArea("ヨーロッパ");

		// 結果検証
		assertEquals(0, actual.size());
	}

	/**
	 * 正常系
	 * 検索結果が1件以上ある場合(2件)
	 */
	@Test
	public void testSelectByArea_01() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Items_3data.sql");

		// テスト対象メソッドを実行
		List<Item> actual = itemDao.selectByArea("原産地2");

		// 期待値作成
		List<Item> expected = Arrays.asList(
			new Item(1002, "商品2", "原産地2", "原産地域2", 200),
			new Item(1003, "商品3", "原産地2", "原産地域3", 300)
		);

		// 結果検証
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * データベース接続ができない場合
	 * java.sql.SQLExceptionが発生した場合
	 */
	@Test
	public void testSelectByArea_03() throws Exception {
		// 偽のURLを設定
		String url = "not connect url";
		// private変数のフィールドを取得
		Field field = itemDao.getClass().getDeclaredField("url");
		field.setAccessible(true); // アクセス制限を解除
		field.set(itemDao, url);	// 値を設定

		// テスト対象メソッドを実行
		Exception exception = assertThrows(DaoException.class, () -> itemDao.selectByArea("原産地1"));
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
	public void testSelectAll_01() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Items_0data.sql");

		// テスト対象メソッドを実行
		List<Item> actual = itemDao.selectAll();

		// 結果検証
		assertEquals(0, actual.size());
	}

	/**
	 * 正常系
	 * 商品情報が1件以上ある場合(3件)
	 */
	@Test
	public void testSelectAll_02() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Items_3data.sql");

		// テスト対象メソッドを実行
		List<Item> actual = itemDao.selectAll();

		// 期待値作成
		List<Item> expected = Arrays.asList(
			new Item(1001, "商品1", "原産地1", "原産地域1", 100),
			new Item(1002, "商品2", "原産地2", "原産地域2", 200),
			new Item(1003, "商品3", "原産地2", "原産地域3", 300)
		);

		// 結果検証
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * データベース接続ができない場合
	 * java.sql.SQLExceptionが発生した場合
	 */
	@Test
	public void testSelectAll_03() throws Exception {
		// 偽のURLを設定
		String url = "not connect url";
		// private変数のフィールドを取得
		Field field = itemDao.getClass().getDeclaredField("url");
		field.setAccessible(true); // アクセス制限を解除
		field.set(itemDao, url);	// 値を設定

		// テスト対象メソッドを実行
		Exception exception = assertThrows(DaoException.class, () -> itemDao.selectAll());
		assertTrue(exception.getMessage().contains("データベース関連エラー"));
	}
}
