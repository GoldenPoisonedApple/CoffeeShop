package com.mamezou.shop.entity;

import java.util.Objects;

/**
 * 商品情報
 * 
 * @author mamezou
 * 
 */
public class Item {

    /** ID */
    private Integer id;

    /** 商品名 */
    private String name;

    /** 原産地域 */
    private String area;

    /** 原産地 */
    private String originalHome;

    /** 価格 */
    private Integer price;

    /**
     * ID以外の属性値を初期化するためのコンストラクタ.
     *
     * @param name 商品名
     * @param area 原産地域
     * @param originalHome 原産地
     * @param price 価格
     */
    public Item(String name, String area, String originalHome, Integer price) {
        this(null, name, area, originalHome, price);
    }

    /**
     * 属性値を初期化するためのコンストラクタ.
     *
     * @param id ID
     * @param name 商品名
     * @param area 原産地域
     * @param originalHome 原産地
     * @param price 価格
     */
    public Item(Integer id, String name, String area, String originalHome, Integer price) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.originalHome = originalHome;
        this.price = price;
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
     * 商品名を取得します．
     *
     * @return 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 商品名を設定します．
     *
     * @param name 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 原産地域を取得します．
     *
     * @return 原産地域
     */
    public String getArea() {
        return area;
    }

    /**
     * 原産地域を設定します．
     *
     * @param area 原産地域
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 原産地を取得します．
     *
     * @return 原産地
     */
    public String getOriginalHome() {
        return originalHome;
    }

    /**
     * 原産地を設定します．
     *
     * @param originalHome 原産地
     */
    public void setOriginalHome(String originalHome) {
        this.originalHome = originalHome;
    }

    /**
     * 価格を取得します．
     *
     * @return 価格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 価格を設定します．
     *
     * @param price 価格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(area, id, name, originalHome, price);
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
        Item other = (Item) obj;
        return Objects.equals(area, other.area) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(originalHome, other.originalHome) && Objects.equals(price, other.price);
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Item [id=%s, name=%s, area=%s, originalHome=%s, price=%s]", id, name, area, originalHome,
                price);
    }
}
