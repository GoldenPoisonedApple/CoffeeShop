package com.mamezou.shop.dataaccess;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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
	private static ItemDao itemDao;
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
	public void setUpEach() {
		// テスト対象クラス
		itemDao = new ItemDao(ApplicationProperties.getInstance(Environment.TEST));
	}


	// ------------------------------
	// selectByAreaメソッドのテスト
	// 原産地域を指定して商品情報を取得する
	// ------------------------------
	/**
	 * 正常系
	 * 検索結果が1件以上ある場合(2件)
	 */
	@Test
	public void testSelectByArea_01() throws SQLException, DaoException {
		// テストデータ投入
		sqlFileRunner.runSqlScript("Items_3data.sql");

		// テスト対象メソッドを実行
		List<Item> actual = itemDao.selectByArea("アジア");

		// 結果検証
		assertEquals(2, actual.size());
	}
}
