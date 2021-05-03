package com.emart.data;

import java.util.ArrayList;

public class Item {

    private int id;
    private String itemName;
    private int category;
    private String keywords;
    private ItemCondition condition;
    private double price;
    private int sellerId;
    private int quantity;

    public Item() {
    }

    public Item(String name, int category, String keyWords, ItemCondition condition, double price,
                int sellerId, int quantity) {
        this.itemName = name;
        this.category = category;
        this.keywords = keyWords;
        this.condition = condition;
        this.price = price;
        this.sellerId = sellerId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getKeyWords() {
        return keywords;
    }

    public void setKeyWords(String keyWords) {
        this.keywords = keyWords;
    }

    public ItemCondition getCondition() {
        return condition;
    }

    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
