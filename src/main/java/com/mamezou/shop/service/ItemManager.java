package com.mamezou.shop.service;

import java.util.List;

import com.mamezou.shop.dataaccess.DaoException;
import com.mamezou.shop.dataaccess.ItemDao;
import com.mamezou.shop.entity.Item;

/**
 * 商品情報管理クラス
 * 
 * @author ito
 */
public class ItemManager implements AutoCloseable {
	/** Daoオブジェクト */
	private ItemDao itemDao;

	/**
	 * コンストラクタ
	 * 
	 * @param itemDao Daoオブジェクト
	 */
	public ItemManager(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	@Override
	public void close() throws ServiceException {
		if (itemDao != null) {
			try {
				itemDao.close();
				
			} catch (DaoException e) {
				throw new ServiceException("リソースの開放に失敗しました", e);
			}
		}
	}

	/**
	 * 商品情報取得
	 * 
	 * @param area 原産地域
	 * @return 商品情報リスト
	 * @throws ServiceException DaoExceptionが発生した場合
	 */
	public List<Item> findByArea(String area) throws ServiceException {
		try {
			// 商品情報取得 返す
			List<Item> items = itemDao.selectByArea(area);
			return items;

		} catch (DaoException e) {
			throw new ServiceException("サービス関連エラー", e);
		}
	}
}
