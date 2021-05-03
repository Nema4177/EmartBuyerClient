package com.emart.buyer.implementation;

import com.emart.buyer.BuyerInterfaceApi;
import com.emart.networt.RestUtils;
import com.emart.utils.BuyerConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.Random;

@Component
public class BuyerApiImplementation implements BuyerInterfaceApi {

    @Autowired
    RestUtils restUtils;

    @Override
    public JSONObject createAccount(int buyerId,String username, String name, String password) throws ParseException {
        JSONObject request = new JSONObject();
        request.put("buyerId", buyerId);
        request.put(BuyerConstants.request_username_key, username);
        request.put("name", name);
        request.put(BuyerConstants.request_password_key, password);

        return makeRESTAPICall(request, BuyerConstants.createAccountPath, BuyerConstants.POST);
    }

    @Override
    public JSONObject login(String username, String password) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_username_key, username);
        request.put(BuyerConstants.request_password_key, password);

        return makeRESTAPICall(request, BuyerConstants.loginPath, BuyerConstants.POST);
    }

    @Override
    public JSONObject logout(int buyerId) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_buyerId_key, buyerId);

        return makeRESTAPICall(request, BuyerConstants.logoutPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId + "&username=", BuyerConstants.POST);
    }

    @Override
    public JSONObject pushFeedback(int itemId, int buyerId, int sellerId, int feedback) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_item_id_key, itemId);
        request.put(BuyerConstants.request_sellerId_key, sellerId);
        request.put("feedbackStatus", feedback);

        return makeRESTAPICall(request, BuyerConstants.pushFeedbackPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.POST);
    }


    @Override
    public JSONObject getSellerRating(int sellerId, int buyerId) throws ParseException {
        return makeRESTAPICall(null, BuyerConstants.getSellerRatingPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId + "&sellerId=" + sellerId, BuyerConstants.GET);
    }

    @Override
    public JSONObject getBuyerHistory(int buyerId) throws ParseException {
        return makeRESTAPICall(null, BuyerConstants.getBuyerHistoryPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.GET);
    }

    @Override
    public JSONObject purchase(String creditCardName, int creditCardNumber,
                               String expirtationDate, int buyerId) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_credit_card_name, creditCardName);
        request.put(BuyerConstants.request_credit_card_number, creditCardNumber);
        request.put(BuyerConstants.request_credit_card_expiration_date, expirtationDate);

        return makeRESTAPICall(request, BuyerConstants.purchaseItemPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.POST);
    }

    @Override
    public JSONObject searchItems(int category, String keywords, int buyerId) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_item_category_key, category);
        request.put(BuyerConstants.request_item_keywords_key, keywords);
        return makeRESTAPICall(request, BuyerConstants.searachItemsPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.POST);
    }

    @Override
    public JSONObject addItemToCart(int buyerId, int sellerId, int itemId, String itemName, int quantity) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_sellerId_key, sellerId);
        request.put(BuyerConstants.request_item_id_key, itemId);
        request.put(BuyerConstants.request_item_name_key, itemName);
        request.put(BuyerConstants.request_item_quantity_key, quantity);
        return makeRESTAPICall(request, BuyerConstants.addItemToCartPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.POST);
    }

    @Override
    public JSONObject removeItemFromCart(int buyerId, int itemId, int quantity) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(BuyerConstants.request_buyerId_key, buyerId);
        request.put(BuyerConstants.request_item_id_key, itemId);
        request.put(BuyerConstants.request_item_quantity_key, quantity);
        return makeRESTAPICall(request, BuyerConstants.removeItemFromCartPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.POST);
    }

    @Override
    public void clearCart(int buyerId) throws ParseException {
        makeRESTAPICall(null, BuyerConstants.clearCartPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.DELETE);
    }

    @Override
    public JSONObject displayCart(int buyerId) throws ParseException {
        return makeRESTAPICall(null, BuyerConstants.displayCartPath + "?" +
                BuyerConstants.response_buyerId_key + "=" + buyerId, BuyerConstants.GET);
    }

    private String getBaseURL() {
        int random = new Random().nextInt(BuyerConstants.baseURLs.size());
        return BuyerConstants.baseURLs.get(random);
    }

    private void removeURL(String url) {
        BuyerConstants.baseURLs.remove(url);
    }

    private JSONObject makeRESTAPICall(JSONObject request, String endpoint, int restMethod) throws ParseException {
        JSONObject response = new JSONObject();
        String baseUrl = getBaseURL();
        String url = baseUrl + endpoint;
        System.out.println("Request url is " + url);
        System.out.println("Request payload is " + request);
        try {
            switch (restMethod) {
                case BuyerConstants.GET -> response = restUtils.getRequest(url);
                case BuyerConstants.DELETE -> restUtils.deleteRequest(url);
                default -> response = restUtils.postRequest(request, url);
            }
        } catch (ResourceAccessException ex) {
            ex.printStackTrace();
            removeURL(baseUrl);
            if (!BuyerConstants.baseURLs.isEmpty()) {
                response = makeRESTAPICall(request, endpoint, restMethod);
            }
        }
        System.out.println("Response payload is " + response);
        return response;
    }

}
