package com.mamezou.shop.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.ItemDao;
import com.mamezou.shop.entity.Item;

/**
 * {@link com.mamezou.shop.service.ItemManager} のテストクラス
 * {@link com.mamezou.shop.dataaccess.ItemDao} のスタブを作成、使用
 * 
 * @author ito
 */
public class ItemManagerTest {
	/** テスト対象クラス */
	private ItemManager itemManager;
	/** モック */
	private ItemDao itemDao;

	/** テスト前処理 */
	@BeforeEach
	void setUp() throws Exception {
		// モックを作成
		itemDao = mock(ItemDao.class);
		// テスト対象クラス作成
		itemManager = new ItemManager();
		// モックを挿入
		Field field = itemDao.getClass().getDeclaredField("itemDao");
		field.setAccessible(true); // アクセス制限を解除
		field.set(itemManager, itemDao);	// 値を設定
	}

	/** テスト後処理 */
	@AfterEach
	void tearDown() {
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
	public void testFindByArea_01() throws ServiceException {
		String findArea = "テスト";

		// スタブとしてのモックの動作を定義
		when(itemManager.findByArea(findArea)).thenReturn(new ArrayList<>());

		// テスト対象メソッドを実行
		List<Item> actual = itemManager.findByArea(findArea);

		// 結果検証
		assertEquals(0, actual.size());
	}

	/**
	 * 正常系
	 * 検索結果が1件以上の場合
	 */
	@Test
	public void testFindByArea_02() throws ServiceException {
		String findArea = "原産1";

		// スタブとしてのモックの動作を定義
		List<Item> items = new ArrayList<>();
		items.add(new Item(1, "商品1", "原産1", "原産地1", 500));
		items.add(new Item(2, "商品2", "原産1", "原産地2", 600));
		when(itemManager.findByArea(findArea)).thenReturn(items);

		// テスト対象メソッドを実行
		List<Item> actual = itemManager.findByArea(findArea);

		// 結果検証
		assertEquals(2, actual.size());

		// 期待値作成
		List<Item> expected = Arrays.asList(
				new Item(1, "商品1", "原産1", "原産地1", 500),
				new Item(2, "商品2", "原産1", "原産地2", 600));

		// 結果検証
		assertEquals(expected, actual);
	}

	/**
	 * 異常系
	 * DaoExceptionが発生した場合
	 */
	@Test
	public void testFindByArea_03() throws ServiceException {
		String findArea = "テスト";

		// スタブとしてのモックの動作を定義
		when(itemManager.findByArea(findArea)).thenThrow(new DaoException("データベース関連エラー", new Exception()));

		// テスト対象メソッドを実行
		Exception exception = assertThrows(ServiceException.class, () -> itemManager.findByArea(findArea));

		// 例外発生時の挙動を検証
		assertEquals("サービス関連エラー", exception.getMessage());
	}
}
