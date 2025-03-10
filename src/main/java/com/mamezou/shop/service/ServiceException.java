package com.mamezou.shop.service;

/**
 * サービスパッケージが送出する例外クラス
 * 
 * @author mamezou
 * 
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 指定された詳細メッセージおよび原因を使用して新規例外を生成します．
     * 
     * @param message 例外インスタンスに付加する詳細メッセージ
     * @param cause 例外インスタンスに付加する原因
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
