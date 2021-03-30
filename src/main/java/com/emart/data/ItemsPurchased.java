package com.emart.data;

public class ItemsPurchased {

    private int purchaseId;
    private int itemId;
    private String itemName;
    private int buyerId;
    private int sellerId;
    private int quantity;
    private double itemPrice;
    private int feedback;

    public ItemsPurchased() {
    }

    public ItemsPurchased(int purchaseId, int itemId, String itemName, int buyerId, int sellerId, int quantity, double itemPrice, int feedback) {
        this.purchaseId = purchaseId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.feedback = feedback;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
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

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }
}