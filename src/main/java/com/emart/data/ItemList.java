package com.emart.data;

import java.util.List; 

public class ItemList {

    private List<Item> items;

    private List<CartItem> cartItems;

    private List<ItemsPurchased> itemsPurchased;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> item) {
        this.items = item;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<ItemsPurchased> getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(List<ItemsPurchased> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }
}