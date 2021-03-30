package com.emart.data;

public class CartItem {

    private int cartId;
    private int itemId;
    private String itemName;
    private int sellerId;
    private int buyerId;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(int cartId, int itemId, String itemName, int sellerId, int buyerId, int quantity, double price) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
