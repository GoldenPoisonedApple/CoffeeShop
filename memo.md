ログの設定色々やってみた

MySQLが何やってもShiftJISしか受け付けないのでそのつもりでどうぞ

JDBCの明示的なロードは必須らしい

## ApplicationProperties
シングルトンに引数渡すの良くなかったかな
初回の環境設定のみ受け付けるようになってる

## テストメモ
モック：「ちゃんとこの関数が呼ばれた？」を確認するためのオブジェクト
スタブ：「こんな入力が来たら、こう返してね」という決まった答えを返すオブジェクト

アンチパターン
モックとスタブの両方の役割を担い、内部実装の検証と外部作用の検証を一つのテスト内で行っている
> テストの目的が曖昧となる

ItemManager: スタブを使用した古典的なやり方
OrderManager: モックのライブラリを使用した現代的なやり方

モックは動作が他と干渉してそうな感じあるから、それぞれのメソッド内部で作った方が安心感ある

テストはプライベートにアクセス

DaoオブジェクトでのDBからのエラーのテストはDriverManagerのモックを作成してやろうかと思い色々試したが本当に無理だったので諦め
動くことだけを考えた単体テスト

エラー辺りは結合テストにやってもらおう



## リソース
Item系統はConnectionを長引かせ、リソースを気にしてみた。
Connectionが重いらしいので使いまわそうと
ItemDaoのリソース管理を色々やってみて惨敗
汚い > 綺麗にした
Factory使った方がいいと思う

Order系統はConnectionの粒度を細かくしている。


# サーバ環境構築
> https://qiita.com/kaburankattara/items/09db660fc5037ab1e32c

### java
javaダウンロードURL確認
> https://jdk.java.net/java-se-ri/19
- ダウンロード
`curl -OL https://download.java.net/openjdk/jdk19/ri/openjdk-19+36_linux-x64_bin.tar.gz`
- インストール
mkdir ~/java && tar xvf openjdk-19+36_linux-x64_bin.tar.gz -C ~/java --strip-components 1
sudo mv ~/java /usr/local/java
- 環境変数
/etc/profileに以下を追記
```
export JAVA_HOME=/usr/local/java
export PATH=$PATH:$JAVA_HOME/bin
```
source /etc/profile

上手くいかんかったから
sudo apt install -y default-jdk
で21入れた

### Tomcat
/optにて
sudo wget https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.36/bin/apache-tomcat-10.1.36.tar.gz
sudo tar -xzf apache-tomcat-10.1.36.tar.gz -C /opt/tomcat --strip-components=1

curl http://localhost:8080
curl http://153.127.215.8:8080
はできるけど外部からはできない。多分ファイアーウォール設定で8080ポートを開放してないからだと思う

- サービスとして登録
ユーザ作成
sudo useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
sudo chown -R tomcat:tomcat /opt/tomcat
作成: /etc/systemd/system/tomcat.service
```
[Unit]
Description=Apache Tomcat 10
After=network.target

[Service]
Type=forking
User=tomcat
Group=tomcat
Environment="JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64"
Environment="CATALINA_PID=/opt/tomcat/temp/tomcat.pid"
Environment="CATALINA_HOME=/opt/tomcat"
Environment="CATALINA_BASE=/opt/tomcat"
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
Restart=on-failure

[Install]
WantedBy=multi-user.target
```
サービス有効化
sudo systemctl daemon-reload
sudo systemctl enable tomcat
sudo systemctl start tomcat
sudo systemctl status tomcat


- 管理ユーザ設定
編集: /opt/tomcat/conf/tomcat-users.xml
role: manager-gui
反映
sudo systemctl restart tomcat


#### すでにappache2が入ってたので連携させる
プロキシモジュール有効化
sudo a2enmod proxy proxy_http
sudo systemctl restart apache2

編集: /etc/apache2/sites-available/000-default.conf
```
<VirtualHost *:80>
	ServerName example.com

	ProxyPass "/app/" "http://localhost:8080/app/"
	ProxyPassReverse "/app/" "http://localhost:8080/app/"

	ProxyPass "/manager/" "http://localhost:8080/manager/"
	ProxyPassReverse "/manager/" "http://localhost:8080/manager/"

	ProxyPass "/mywebapp/" "http://localhost:8080/mywebapp/"
	ProxyPassReverse "/mywebapp/" "http://localhost:8080/mywebapp/"

	ProxyPass "/CoffeeShop/" "http://localhost:8080/CoffeeShop/"
	ProxyPassReverse "/CoffeeShop/" "http://localhost:8080/CoffeeShop/"
</VirtualHost>
```
sudo systemctl restart apache2

- TomCat側のセキュリティ強化 (こいつやったらmanagerが受け付けなくなった なぜ)
(まあファイアーウォールで8080ポート許してないからやらなくてもいいよね)
外部からのアクセスを受け付けなくする
編集: /opt/tomcat/conf/server.xml
```
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"
           address="127.0.0.1" />
```

**できた！**
http://153.127.215.8/app/
http://153.127.215.8/manager/
に接続するとTomcatのNotFound画面になる


### MySQL
sudo apt update
sudo apt install mysql-server

初期設定
sudo mysql_secure_installation
匿名ユーザ削除・リモートログイン不許可

編集: /etc/mysql/conf.d/
[mysqld]
bind-address = 127.0.0.1

- 再起動 `sudo systemctl restart mysql`

- 上手くいかん
リモートログイン可能ユーザを新規に作成したら上手くいった
rootにログインできないだけなのかリモートログインが可能にならないといけないのか

### デプロイ手順
サーバーにコピー
scp -i C:/Users/itous/.ssh/mirai_server -P 10022 target\CoffeeShop.war itous@153.127.215.8:/home/itous/Programs/Java/war/
サーバ接続・ルートユーザへ
デプロイ
sudo cp /home/itous/Programs/Java/war/CoffeeShop.war /opt/tomcat/webapps/
リロード

## 言語切り替え機能
Viewのソースが非常に見にくいので普通にフレームワーク使った方が良かったと思う

# Version情報
Tomcat 10.1.36
MySQL 9.2
Java 19.0.1