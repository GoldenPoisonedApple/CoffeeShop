/**
 * Mockitoを使用したテスト
 */

package com.mamezou.shop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mamezou.shop.entity.Order;
import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.OrderDao;

/**
 * {@link com.mamezou.shop.service.OrderManager} のテストクラス
 * {@link com.mamezou.shop.dataaccess.OrderDao} のスタブを作成、使用
 * 
 * @author ito
 */
public class OrderManagerTest {
	/** テスト対象クラス */
	private OrderManager orderManager;
	/** モック */
	private OrderDao orderDao;

	/** テスト前処理 */
	@BeforeEach
	public void setUp() throws Exception {
		// モックを作成
		orderDao = mock(OrderDao.class);
		// テスト対象クラス作成
		orderManager = new OrderManager();
		// モックを挿入
		Field field = orderManager.getClass().getDeclaredField("orderDao");
		field.setAccessible(true); // アクセス制限を解除
		field.set(orderManager, orderDao);	// 値を設定
	}

	/** テスト後処理 */
	@AfterEach
	public void tearDown() throws ServiceException {
	}

	// ------------------------------
	// registerメソッドのテスト
	// 注文情報を登録する
	// ------------------------------
	/**
	 * 正常系
	 * 登録成功の場合
	 * アンチパターン
	 * モックとスタブの両方の役割をになっているため、内部実装の検証と外部作用の検証を一つのテスト内で行っている
	 * > テストの目的が曖昧となる
	 */
	@Test
	public void testRegister_01() throws DaoException, ServiceException {
		Order registerOrder = new Order("氏名2", "住所2", "111-1111-1111", 2);

		// モックの動作を定義
		when(orderDao.insert(registerOrder)).thenReturn(1);

		// テスト対象メソッドを実行
		int actual = orderManager.register(registerOrder);

		// 結果検証
		assertEquals(1, actual);
		verify(orderDao, times(1)).insert(registerOrder); // insertメソッドが1回呼ばれたことを検証
	}

	/**
	 * 異常系
	 * DaoExceptionが発生した場合
	 */
	@Test
	public void testRegister_02() throws DaoException {
		Order order = new Order("氏名2", "住所2", "111-1111-1111", 2);

		// モックの動作を定義
		when(orderDao.insert(order)).thenThrow(new DaoException("データベース関連エラー", new Exception()));

		// テスト対象メソッドを実行
		Exception exception = assertThrows(ServiceException.class, () -> orderManager.register(order));
		assertTrue(exception.getMessage().contains("サービス関連エラー"));
		verify(orderDao, times(1)).insert(order);
	}

}
