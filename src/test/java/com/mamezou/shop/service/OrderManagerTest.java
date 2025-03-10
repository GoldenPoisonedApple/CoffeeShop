/**
 * Mockitoを使用したテスト
 */

package com.mamezou.shop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mamezou.shop.entity.Order;
import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.OrderDao;

/**
 * OrderManagerクラスのテストクラス
 * {@link com.mamezou.shop.dataaccess.OrderDao} のスタブを作成、使用
 */
public class OrderManagerTest {
	/** モック化したDaoオブジェクト */
	// @Mockアノテーション: モックオブジェクトを作成
	@Mock
	private OrderDao orderDao;

	/** テスト対象クラス */
	private OrderManager orderManager;

	@BeforeEach
	public void setUp() {
		// モックの初期化
		MockitoAnnotations.openMocks(this);
		// モックを注入
		orderManager = new OrderManager(orderDao);
	}


	// ------------------------------
	// registerメソッドのテスト
	// 注文情報を登録する
	// ------------------------------
	/**
	 * 正常系
	 * 登録成功の場合
	 */
	@Test
	public void testRegister_01() throws DaoException, ServiceException {

		Order order = new Order("氏名2", "住所2", "111-1111-1111", 2);
		// モックの動作を定義
		when(orderDao.insert(order)).thenReturn(1);

		// テスト対象メソッドを実行
		int actual = orderManager.register(order);

		// 結果検証
		assertEquals(1, actual);
		verify(orderDao, times(1)).insert(order); // insertメソッドが1回呼ばれたことを検証
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
		verify(orderDao, times(1)).insert(order); // insertメソッドが1回呼ばれたことを検証
	}
}
