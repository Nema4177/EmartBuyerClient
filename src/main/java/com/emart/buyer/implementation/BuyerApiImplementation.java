package com.emart.buyer.implementation;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.buyer.BuyerInterfaceApi;
import com.emart.networt.RestUtils;
import com.emart.utils.BuyerConstants;

@Component
public class BuyerApiImplementation implements BuyerInterfaceApi{
	
	@Autowired
	RestUtils restUtils;

	@Override
	public JSONObject createAccount(String username, String password) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_username_key, username);
    	request.put(BuyerConstants.request_password_key, password);
    	String url = BuyerConstants.baseUrl+BuyerConstants.createAccountPath;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;
	}

	@Override
	public JSONObject login(String username, String password) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_username_key, username);
    	request.put(BuyerConstants.request_password_key, password);
    	String url = BuyerConstants.baseUrl+BuyerConstants.loginPath;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject logout(int buyerId) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.logoutPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);

    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject pushFeedback(int itemId, int buyerId, int feedback) throws ParseException{
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_item_id_key, itemId);
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.pushFeedbackPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}


	@Override
	public JSONObject getSellerRating(int sellerId, int buyerId) throws ParseException{
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_sellerId_key, sellerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.getSellerRatingPath;
    	System.out.println("Request url is "+url);
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject getBuyerHistory(int buyerId) throws ParseException{
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.getSellerRatingPath;
    	System.out.println("Request url is "+url);
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject purchase(int itemId, int quantity, String creditCardName, int creditCardNumber,
			String expirtationDate, int buyerId) throws ParseException{
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_item_id_key, itemId);
    	request.put(BuyerConstants.request_item_quantity_key, quantity);
    	request.put(BuyerConstants.request_credit_card_name, creditCardName);
    	request.put(BuyerConstants.request_credit_card_number, creditCardNumber);
    	request.put(BuyerConstants.request_credit_card_expiration_date, expirtationDate);
    	String url = BuyerConstants.baseUrl+BuyerConstants.purchaseItemPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject searchItems(int category, String keywords, int buyerId) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_item_category_key, category);
    	request.put(BuyerConstants.request_item_keywords_key, keywords);
    	String url = BuyerConstants.baseUrl+BuyerConstants.searachItemsPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject addItemToCart(int buyerId, int itemId, int quantity) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	request.put(BuyerConstants.request_item_id_key, itemId);
    	request.put(BuyerConstants.request_item_quantity_key, quantity);
    	String url = BuyerConstants.baseUrl+BuyerConstants.addItemToCartPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject removeItemFromCart(int buyerId, int itemId, int quantity) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	request.put(BuyerConstants.request_item_id_key, itemId);
    	request.put(BuyerConstants.request_item_quantity_key, quantity);
    	String url = BuyerConstants.baseUrl+BuyerConstants.removeItemFromCartPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject clearCart(int buyerId) throws ParseException{
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.clearCartPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

	@Override
	public JSONObject displayCart(int buyerId) throws ParseException {
		JSONObject request = new JSONObject();
    	request.put(BuyerConstants.request_buyerId_key, buyerId);
    	String url = BuyerConstants.baseUrl+BuyerConstants.displayCartPath;
    	url = url+"?"+BuyerConstants.response_buyerId_key+"="+buyerId;
    	System.out.println("Request url is "+url);
    	System.out.println("Request payload is "+request);
    	JSONObject response =  restUtils.postRequest(request, url);
    	System.out.println("Response payload is "+response);
    	return response;	}

}
