package com.mamezou.shop.entity;

import java.util.Objects;

/**
 * 注文情報
 * 
 * @author mamezou
 * 
 */
public class Order {

    /** ID */
    private Integer id;

    /** 氏名 */
    private String name;

    /** 住所 */
    private String address;

    /** 電話番号 */
    private String telNumber;

    /** 注文商品ID */
    private Integer itemId;

    /**
     * ID以外の属性値を初期化するためのコンストラクタ.
     *
     * @param name 氏名
     * @param address 住所
     * @param telNumber 電話番号
     * @param itemId 注文商品ID
     */
    public Order(String name, String address, String telNumber, Integer itemId) {
        this(null, name, address, telNumber, itemId);
    }

    /**
     * 属性値を初期化するためのコンストラクタ.
     *
     * @param id ID
     * @param name 氏名
     * @param address 住所
     * @param telNumber 電話番号
     * @param itemId 注文商品ID
     */
    public Order(Integer id, String name, String address, String telNumber, Integer itemId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.itemId = itemId;
    }

    /**
     * IDを取得します．
     * 
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * IDを設定します．
     * 
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 氏名を取得します．
     * 
     * @return 氏名
     */
    public String getName() {
        return name;
    }

    /**
     * 氏名を設定します．
     * 
     * @param name 氏名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 住所を取得します．
     * 
     * @return 住所
     */
    public String getAddress() {
        return address;
    }

    /**
     * 住所を設定します．
     * 
     * @param address 住所
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 電話番号を取得します．
     * 
     * @return 電話番号
     */
    public String getTelNumber() {
        return telNumber;
    }

    /**
     * 電話を設定します．
     * 
     * @param telNumber 電話番号
     */
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    /**
     * 注文商品IDを取得します．
     * 
     * @return 注文商品ID
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * 注文商品IDを設定します．
     * 
     * @param itemId 注文商品ID
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, id, itemId, name, telNumber);
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return Objects.equals(address, other.address) && id == other.id && itemId == other.itemId
                && Objects.equals(name, other.name) && Objects.equals(telNumber, other.telNumber);
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Order [id=%s, itemId=%s, name=%s, address=%s, telNumber=%s]", id, itemId, name, address,
                telNumber);
    }
}
