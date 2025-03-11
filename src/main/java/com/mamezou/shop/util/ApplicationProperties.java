package com.mamezou.shop.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 設定ファイルから、DB接続情報を読み取るシングルトン
 */
public class ApplicationProperties {
	/** 本番環境時 読み込みファイル名 */
	private final String PROPERTIES_FILE = "jdbc.properties";

	/** ロガー */
	private static Logger logger = LogManager.getLogger(ApplicationProperties.class);

	/** 読み込んだDB接続状況 */
	private Properties props;

	/** 自インスタンス */
	private static ApplicationProperties appProps;

	/**
	 * インスタンスを取得
	 * @return インスタンス
	 */
	public static ApplicationProperties getInstance() {
		// synchronizedを使ってスレッドセーフにする
		synchronized (ApplicationProperties.class) {
			if (appProps == null) {
				// インスタンス生成
				appProps = new ApplicationProperties();
			}
		}
		return appProps;
	}

	/**
	 * コンストラクタ.
	 *
	 * 本アプリケーションの設定ファイル(application.properties)から設定値を読み込みます．
	 *
	 * 利用可能な設定項目(キー名)は以下の通り：
	 * <pre>
	 * # DBのURL
	 * db.url
	 * # DBのユーザ名
	 * db.user
	 * # DBのパスワード
	 * db.password
	 * </pre>
	 * @param env 実行環境
	 * @throws RuntimeException 設定ファイル application.properties の読み込みに失敗した場合
	 */
	private ApplicationProperties() {

		// プロパティファイルの読み込み
		props = new Properties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));

		} catch (IOException e) {
			throw new RuntimeException("DB設定ファイルの読み込みに失敗しました", e);
		}
		// 読み込んだ設定ファイルをログ出力
		logger.debug("properties=[{}]", props);
	}

	/**
	 * DBの接続先となるURLを取得します．
	 * 
	 * @return DBの接続先URL
	 */
	public String getDatabaseUrl() {
		return props.getProperty("URL") + props.getProperty("DATABASE") + props.getProperty("PROPATIES");
	}

	/**
	 * DBのユーザ名を取得します．
	 *
	 * @return DBのユーザ名
	 */
	public String getDatabaseUser() {
		return props.getProperty("USER");
	}

	/**
	 * DBのパスワードを取得します．
	 * {@link #getDatabaseUser()}で返されるDBユーザのパスワードです．
	 *
	 * @return DBのパスワード
	 */
	public String getDatabasePassword() {
		return props.getProperty("PASS");
	}
}
