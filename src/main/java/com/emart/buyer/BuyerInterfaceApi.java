package com.emart.buyer;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.emart.data.Item;
import com.emart.data.ItemCart;

@Component
public interface BuyerInterfaceApi {

    public enum ACTIONS {
    	createAccount,
    	login,
    	logout,
    	purchase,
    	pushFeedback,
    	getSellerRating,
    	getBuyerHistory,
        searchItemWithCategory,
        addItemToCart,
        removeItemFromCart,
        clearCart,
        displayCart
    }
    
    
    public JSONObject displayCart(int buyerId) throws ParseException;
    
    public JSONObject login(String username, String password) throws ParseException;
    
    public JSONObject logout(int buyerId) throws ParseException;
    
    public JSONObject pushFeedback(int itemId, int buyerId,int sellerId, int feedback) throws ParseException;
    
    public JSONObject getSellerRating(int sellerId, int buyerId) throws ParseException;
    
    public JSONObject getBuyerHistory(int buyerId) throws ParseException;
    
    public JSONObject purchase(String creditCardName, int creditCardNumber, String expirtationDate, int buyerId) throws ParseException;
    
    
    /**
     * API for searching items
     *
     * @param category of the item needed
     * @param keywords search keyword
     * @return list of Items that matched the search
     */
    public JSONObject searchItems(int category, String keywords, int buyerId) throws ParseException;

    /**
     * API for adding item to the cart
     *
     * @param buyerId of the buyer
     * @param itemId  of the item to be added to the cart
     * @param quantity of the items to be added
     */
    public JSONObject addItemToCart(int buyerId, int sellerId,int itemId,String itemName, int quantity) throws ParseException;

    /**
     * API for removing item from the cart
     *
     * @param buyerId  of the buyer
     * @param itemId   of the item to be removed from the cart
     * @param quantity of the item to be removed from the cart
     */
    public JSONObject removeItemFromCart(int buyerId, int itemId, int quantity) throws ParseException;

    /**
     * API for clearing the cart
     *
     * @param buyerId of the buyer
     */
    public void clearCart(int buyerId) throws ParseException;

	JSONObject createAccount(int buyerId,String username, String name, String password) throws ParseException;

}
