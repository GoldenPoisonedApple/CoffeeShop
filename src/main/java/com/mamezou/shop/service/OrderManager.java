package com.mamezou.shop.service;

import java.util.List;

import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.OrderDao;
import com.mamezou.shop.entity.Order;

/** 
 * 商品情報管理クラス
 * @author ito
 */
public class OrderManager {
	/** Daoオブジェクト */
	private OrderDao orderDao;

	/**
	 * コンストラクタ
	 */
	public OrderManager() {
		this.orderDao = new OrderDao();
	}
	
	/**
	 * 注文情報登録
	 * @param order 注文情報 登録後にIDが設定される
	 * @return 注文情報ID
	 * @throws ServiceException DaoExceptionが発生した場合
	 */
	public int register(Order order) throws ServiceException {

		try {
			// 注文情報登録
			int orderId = orderDao.insert(order);
			// 注文情報IDを返す
			return orderId;

		} catch (DaoException e) {
			throw new ServiceException("サービス関連エラー", e);
		}
		
	}

	/**
	 * 注文情報取得
	 * @return 注文情報リスト
	 * @throws ServiceException DaoExceptionが発生した場合
	 */
	public List<Order> getAll() throws ServiceException {
		try {
			// 注文情報取得 返す
			List<Order> orders = orderDao.selectAll();
			return orders;

		} catch (DaoException e) {
			throw new ServiceException("サービス関連エラー", e);
		}
	}
}
