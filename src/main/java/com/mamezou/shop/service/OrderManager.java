package com.mamezou.shop.service;

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
	 * @param orderDao 注文情報DAO
	 */
	public OrderManager(OrderDao orderDao) {
		this.orderDao = orderDao;
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
}
