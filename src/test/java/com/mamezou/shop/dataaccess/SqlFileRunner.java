package com.mamezou.shop.dataaccess;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.mamezou.shop.util.ApplicationProperties;

/**
 * SQLファイルを実行するクラス
 * 必ずclose()を呼び出してリソースを解放すること
 * テスト用
 */
public class SqlFileRunner {
	/** DB接続 */
	private Connection connection;

	/**
	 * コンストラクタ
	 * 
	 * @param properties DB接続情報
	 */
	public SqlFileRunner(ApplicationProperties properties) throws SQLException {
		// DB接続情報取得
		String url = properties.getDatabaseUrl();
		String  user = properties.getDatabaseUser();
		String password = properties.getDatabasePassword();
		// DB接続
		connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 指定したリソース名のSQLスクリプトを実行する.
	 * リソースファイルはCLASSPATHに含まれていること.
	 * 
	 * @param resourceName 実行するSQLスクリプトのリソース名
	 * @throws IOException リソースが見つからない場合
	 */
	public void runSqlScript(String resourceName) throws SQLException {
		// ScriptRunnerを使用してSQLファイルを実行
		ScriptRunner scriptRunner = new ScriptRunner(connection);
		InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourceName);
		if (inputStream == null) {
			throw new SQLException("リソースが見つかりません:" + resourceName);
		}
		scriptRunner.runScript(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
	}

	/**
	 * 指定したテーブルのデータを取得する.
	 * 
	 * @param tableName テーブル名
	 * @return テーブルのデータ
	 * @throws SQLException SQL例外が発生した場合
	 */
	public ResultSet getTableData (String tableName) throws SQLException {
		return connection.createStatement().executeQuery("SELECT * FROM " + tableName);
	}

	/**
	 * リソースを解放する.
	 * 必ず呼び出すこと.
	 */
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
