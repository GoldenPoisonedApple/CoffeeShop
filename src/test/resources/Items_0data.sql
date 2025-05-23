-- 商品テーブルの作成
CREATE TABLE IF NOT EXISTS ITEMS (
    ID              INTEGER     NOT NULL AUTO_INCREMENT,
    NAME            VARCHAR(64) NOT NULL COMMENT '商品名',
    AREA            VARCHAR(64) NOT NULL COMMENT '原産地域',
    ORIGINAL_HOME   VARCHAR(64) NOT NULL COMMENT '原産地',
    PRICE           INTEGER     NOT NULL COMMENT '価格',
    CONSTRAINT PK_ITEMS_ID PRIMARY KEY(ID)
) COMMENT='商品';

-- 全件削除
SET foreign_key_checks = 0;	-- 外部キー制約を無効にする
TRUNCATE TABLE ITEMS;
SET foreign_key_checks = 1;	-- 外部キー制約を有効にする

-- AUTO_INCREMENT の開始値を設定
ALTER TABLE ITEMS AUTO_INCREMENT = 1001;