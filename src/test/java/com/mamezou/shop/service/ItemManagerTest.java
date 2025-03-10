package com.mamezou.shop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.ItemDao;
import com.mamezou.shop.entity.Item;
import com.mamezou.shop.util.ApplicationProperties;
import com.mamezou.shop.util.Environment;

/**
 * ItemManagerクラスのテストクラス
 * @see ItemManager
 * {@link com.mamezou.shop.dataaccess.ItemDao} のスタブを作成、使用
 */
public class ItemManagerTest {

	/** テスト前処理 */
	@BeforeEach
	void setUp() {
		// コンストラクタでDaoオブジェクトを渡すためない
	}

	/** テスト後処理 */
	@AfterEach
	void tearDown() {
		// ない
	}

	// ------------------------------
	// findByAreaメソッドのテスト
	// 原産地域を指定して商品情報を取得する
	// ------------------------------
	/**
	 * 正常系
	 * 検索結果が0件の場合
	 */
	@Test
	public void testFindByArea_01() {
		// スタブを作成
		ItemDao itemDao = new ItemDaoStub01();
		ItemManager itemManager = new ItemManager(itemDao);
		
		// テスト対象メソッドを実行
		try {
			List<Item> actual = itemManager.findByArea("テスト");
			
			// 結果検証
			assertEquals(0, actual.size());
			
		} catch (ServiceException e) { Assertions.fail(e); }

	}
	private class ItemDaoStub01 extends ItemDao {
		public ItemDaoStub01() {
			super(ApplicationProperties.getInstance(Environment.TEST));
		}

		@Override
		public List<Item> selectByArea(String area) {
			return new ArrayList<>();
		}
	}

	/**
	 * 正常系
	 * 検索結果が1件以上の場合
	 */
	@Test
	public void testFindByArea_02() {
		// スタブを作成
		ItemDao itemDao = new ItemDaoStub02();
		ItemManager itemManager = new ItemManager(itemDao);
		
		// テスト対象メソッドを実行
		try {
			List<Item> actual = itemManager.findByArea("テスト");
			
			// 期待値作成
			List<Item> expected = Arrays.asList(
				new Item(1, "商品1", "原産1", "原産地1", 500),
				new Item(2, "商品2", "原産2", "原産地2", 600)
			);

			// 結果検証
			assertEquals(expected, actual);
			
		} catch (ServiceException e) { Assertions.fail(e); }

	}
	private class ItemDaoStub02 extends ItemDao {
		public ItemDaoStub02() {
			super(ApplicationProperties.getInstance(Environment.TEST));
		}

		@Override
		public List<Item> selectByArea(String area) {
			List<Item> items = new ArrayList<>();
			items.add(new Item(1, "商品1", "原産1", "原産地1", 500));
			items.add(new Item(2, "商品2", "原産2", "原産地2", 600));
			return items;
		}
	}

	/**
	 * 異常系
	 * DaoExceptionが発生した場合
	 */
	@Test
	public void testFindByArea_03() {
		// スタブを作成
		ItemDao itemDao = new ItemDaoStub03();
		ItemManager itemManager = new ItemManager(itemDao);
		
		// テスト対象メソッドを実行
		try {
			itemManager.findByArea("テスト");
			fail("発生すべき例外 ServiceExceptionが発生しませんでした");
			
		} catch (ServiceException e) {
			// 例外発生時の挙動を検証
			assertEquals("サービス関連エラー", e.getMessage());
		}
	}
	private class ItemDaoStub03 extends ItemDao {
		public ItemDaoStub03() {
			super(ApplicationProperties.getInstance(Environment.TEST));
		}

		@Override
		public List<Item> selectByArea(String area) throws DaoException {
			throw new DaoException("サービス関連エラー", new Throwable());
		}
	}

}
