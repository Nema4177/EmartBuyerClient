package com.emart.utils;

import java.util.ArrayList;
import java.util.List;

public class BuyerConstants {
	public static final String baseUrl = "http://localhost:8080/";
	public static final String createAccountPath = "buyer/register";
	public static final String loginPath = "buyer/login";
	public static final String logoutPath = "buyer/logout";
	public static final String getSellerRatingPath = "buyer/getSellerRating";
	public static final String searachItemsPath = "item/search";
	public static final String addItemToCartPath = "buyer/addItem";
	public static final String removeItemFromCartPath = "buyer/removeItem";
	public static final String clearCartPath = "buyer/clearCart";
	public static final String displayCartPath = "buyer/getCart";
	public static final String purchaseItemPath = "buyer/purchase";
	public static final String pushFeedbackPath = "buyer/provideFeedback";
	public static final String getBuyerHistoryPath = "buyer/getPurchaseHistory";

	public static final List<String> baseURLs = new ArrayList<>(List.of("http://localhost:8080/", "http://localhost:8080/",
			"http://localhost:8080/", "http://localhost:8080/"));
	
	public static final int success = 200;
	public static final long failure = 400;
	
	public static final String request_type_key = "requestType";
	
	public static final String request_item_id_key = "itemId";
	public static final String request_item_price_key = "itemPrice";
	public static final String request_item_quantity_key = "itemQuantity";
	public static final String request_item_name_key = "itemName";
	public static final String request_item_category_key = "itemCategory";
	public static final String request_item_keywords_key = "itemKeywords";
	public static final String request_item_condition_key = "itemCondition";
	public static final String request_buyerId_key = "buyerId";
	public static final String request_sellerId_key = "sellerId";
	public static final String request_credit_card_number = "cardNumber";
	public static final String request_credit_card_name = "name";
	public static final String request_credit_card_expiration_date = "expDate";

	//REST METHOD
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int DELETE = 3;

	public static final String request_username_key = "username";
	public static final String request_password_key = "password";

	
	public static final String response_status_key = "status";
	public static final String response_message_key = "message";
	public static final String response_sellerId_key = "sellerId";
	public static final String response_selleName_key = "sellerName";
	public static final String response_buyerId_key = "buyerId";
	public static final String response_sellerRating_key = "sellerRating";
	public static final String response_purchasedItems_key = "purchasedItemsKey";
	public static final String response_feedback_provided_key = "feedbackProvided";
	public static final String response_quantity_added_key = "quantityAdded";
	public static final String response_quantity_removed_key = "quantityRemoved";


	public static final String response_itemsList_key = "itemsList";
	public static final String response_failure_message_key = "message";	
}
