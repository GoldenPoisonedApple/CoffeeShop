package com.mamezou.shop.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import com.mamezou.shop.entity.Item;
import com.mamezou.shop.util.ApplicationProperties;
import com.mamezou.shop.util.Environment;

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

	/** テスト後処理 */
	@BeforeEach
	public void setUpEach() throws DaoException {
		// テスト対象クラス
		itemDao = new ItemDao(ApplicationProperties.getInstance(Environment.TEST));
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
	 * java.sql.SQLExceptionが発生した場合
	 */
	@Test
	public void testSelectByArea_03() throws Exception {
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
		Field field = itemDao.getClass().getDeclaredField("conn");
		// private変数へのアクセス制限を解除
		field.setAccessible(true);
		// private変数に値を設定
		field.set(itemDao, conn);

		// テスト対象メソッドを実行
		Exception exception = assertThrows(DaoException.class, () -> itemDao.selectByArea("原産地1"));
		assertTrue(exception.getMessage().contains("データベース関連エラー"));
	}

}
