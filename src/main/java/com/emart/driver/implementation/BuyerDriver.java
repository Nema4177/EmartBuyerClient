package com.emart.driver.implementation;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.utils.BuyerConstants;
import com.emart.utils.Utils;
import com.emart.buyer.BuyerInterfaceApi;
import com.emart.driver.Driver;
import com.emart.networt.RestUtils;

@Component
public class BuyerDriver implements Driver {

	@Autowired
	private BuyerInterfaceApi buyerApi;

	@Autowired
	private Utils utils;

	@Autowired
	RestUtils restUtils;

	private int buyerId;

	@PostConstruct
	@Override
	public void start() {
		try {
			String message = "failure";
			int input = 0;
			while (message.equals(BuyerConstants.failure)) {
				System.out.println("Select an option\n 1.Create an account\n " + "2.Login into account\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = createAccount();
				case 2 -> message = login();
				}
				if (message.equals(BuyerConstants.failure)) {
					System.out.println("Operation falied, please try again");
				} else if (message.equals("success")) {
					System.out.println("Operation success");
				}
			}
			while (input != 5) {
				System.out.println("Select an option\n " + "1.Get Seller Rating\n " + "2.Search item with category\n"
						+ "3.Add item to cart\n" + "4.Remove item from cart\n" + "5.Display cart\n" + "6.Clear cart\n"
						+ "7.Purchase an item\n" + "8.Push Feedback\n" + "9. getBuyerHistory\n" + "10.Logout\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = getSellerRating();
				case 2 -> message = searchItemWithCategory();
				case 3 -> message = addItemToCart();
				case 4 -> message = removeItemFromCart();
				case 5 -> message = displayCart();
				case 6 -> message = clearCart();
				case 7 -> message = purchaseItem();
				case 8 -> message = pushFeedback();
				case 9 -> message = getBuyerHistory();
				case 10 -> message = logout();
				}
				// System.out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String displayCart() {
		String status = "";
		try {
			JSONObject response = buyerApi.displayCart(buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				JSONArray itemJsonArray = (JSONArray) response.get(BuyerConstants.response_itemsList_key);
				if (itemJsonArray.size() == 0) {
					System.out.println("Cart has no items");
				}
				for (int i = 0; i < itemJsonArray.size(); i++) {
					JSONObject obj = (JSONObject) itemJsonArray.get(i);
					String itemName = (String) obj.get(BuyerConstants.request_item_name_key);
					int itemId = (int) obj.get(BuyerConstants.request_item_id_key);
					int quantity = (int) obj.get(BuyerConstants.request_item_quantity_key);
					int sellerId = (int) obj.get(BuyerConstants.response_sellerId_key);
					System.out.println("ItemName: " + itemName + " itemId: " + itemId + " sellerId: " + sellerId
							+ " quantity: " + quantity);
				}
			} else {
				System.out.println("Display cart failed with the message: "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String clearCart() {
		String status = "";
		try {
			JSONObject response = buyerApi.clearCart(buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				System.out.println("Cart is cleared");
			} else {
				System.out.println("Clearing cart failed with the message: "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String removeItemFromCart() {
		String status = "";
		try {
			System.out.println("Enter itemid that you want to remove to cart");
			int itemId = utils.readInputInteger();
			System.out.println("Enter the quantity that you want to add");
			int quantity = utils.readInputInteger();
			JSONObject response = buyerApi.removeItemFromCart(buyerId, itemId, quantity);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				quantity = (int) response.get(BuyerConstants.response_quantity_removed_key);
				System.out.println("Quantity: " + quantity + " of the itemid: " + itemId + " removed");
			} else {
				System.out.println("adding item failed with the response "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String addItemToCart() {
		String status = "";
		try {
			System.out.println("Enter itemid that you want to add to cart");
			int itemId = utils.readInputInteger();
			System.out.println("Enter the quantity that you want to add");
			int quantity = utils.readInputInteger();
			JSONObject response = buyerApi.addItemToCart(buyerId, itemId, quantity);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				quantity = (int) response.get(BuyerConstants.response_quantity_added_key);
				System.out.println("Quantity: " + quantity + " of the itemid: " + itemId + " added");
			} else {
				System.out.println("adding item failed with the response "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String searchItemWithCategory() {
		String status = "";
		try {
			System.out.println("Enter following criteria for search: Item category,Keywords(seperated by hyphen)");
			String input = utils.readInputString();
			String inputArray[] = input.split(",");
			int category = 0;
			String keywords = "";
			try {
				category = Integer.parseInt(inputArray[0]);
			} catch (NumberFormatException e) {
				System.out.print("Please enter a valid number for category");
			}
			keywords = inputArray[1];

			JSONObject response = buyerApi.searchItems(category, keywords,buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				JSONArray itemJsonArray = (JSONArray) response.get(BuyerConstants.response_itemsList_key);
				if (itemJsonArray.size() == 0) {
					System.out.println("Search returned zero items");
				}
				for (int i = 0; i < itemJsonArray.size(); i++) {
					JSONObject obj = (JSONObject) itemJsonArray.get(i);
					String itemName = (String) obj.get(BuyerConstants.request_item_name_key);
					int itemId = (int) obj.get(BuyerConstants.request_item_id_key);
					Double salePrice = (Double) obj.get(BuyerConstants.request_item_price_key);
					int quantity = (int) obj.get(BuyerConstants.request_item_quantity_key);
					System.out.println("ItemName: " + itemName + " itemId: " + itemId + " salePrice: " + salePrice
							+ " quantity: " + quantity);
				}
			} else {
				System.out.println(
						"Search failed with the message " + response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// TODO
	private String logout() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getBuyerHistory() throws Exception {
		String status = "";
		try {
			JSONObject response = buyerApi.getBuyerHistory(buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.failure)) {
				System.out.println("Fetching buyer history unsuccessful");
			} else {
				JSONArray itemArray = (JSONArray) response.get(BuyerConstants.response_purchasedItems_key);
				for (int i = 0; i < itemArray.size(); i++) {
					JSONObject obj = (JSONObject) itemArray.get(i);
					String itemName = (String) obj.get(BuyerConstants.request_item_name_key);
					int itemId = (int) obj.get(BuyerConstants.request_item_id_key);
					String sellerId = (String) obj.get(BuyerConstants.response_sellerId_key);
					String sellerName = (String) obj.get(BuyerConstants.response_selleName_key);
					Double salePrice = (Double) obj.get(BuyerConstants.request_item_price_key);
					int quantity = (int) obj.get(BuyerConstants.request_item_quantity_key);
					int feedback = (int) obj.get(BuyerConstants.response_feedback_provided_key);
					String feedbackString = "";
					switch (feedback) {
					case 0 -> feedbackString = "Not provided";
					case 1 -> feedbackString = "Upvote";
					case 2 -> feedbackString = "Downvote";
					}
					System.out.println("ItemName: " + itemName + " itemId: " + itemId + " salePrice: " + salePrice
							+ " quantity: " + quantity + " sellerName: " + sellerName + " sellerId: " + sellerId
							+ " Feedback: " + feedbackString);
				}
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String pushFeedback() throws Exception {
		String status = "";
		try {
			System.out.println("Enter itemId for which you want to give feedback");
			int itemId = utils.readInputInteger();
			int currItemId = -1;
			int feedback;
			JSONObject response = buyerApi.getBuyerHistory(buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.failure)) {
				System.out.println("Fetching buyer history unsuccessful");
			} else {
				JSONArray itemArray = (JSONArray) response.get(BuyerConstants.response_purchasedItems_key);
				for (int i = 0; i < itemArray.size(); i++) {
					JSONObject obj = (JSONObject) itemArray.get(i);
					currItemId = (int) obj.get(BuyerConstants.request_item_id_key);
					if (itemId == currItemId) {
						feedback = (int) obj.get(BuyerConstants.response_feedback_provided_key);
						if (feedback != 0) {
							String feedbackString = "";
							switch (feedback) {
							case 0 -> feedbackString = "Not provided";
							case 1 -> feedbackString = "Upvote";
							case 2 -> feedbackString = "Downvote";
							}
							System.out.println("Feedback already provided as : " + feedbackString);
						} else {
							System.out.println(
									"Please enter the feedback you wish to provide as number as per following 1.Upvote\n 2.Downvote");
							feedback = utils.readInputInteger();
							response = buyerApi.pushFeedback(itemId, buyerId, feedback);
							status = (String) response.get(BuyerConstants.response_status_key);
							if (status.equalsIgnoreCase(BuyerConstants.failure)) {
								System.out.println("Pushing feedback unsuccessful");
							} else {
								System.out.println("Feedback pushed successfully");
							}
						}
					}
				}
				if (currItemId == -1) {
					System.out.println("No item matched in the purchase history");
				}
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String purchaseItem() throws Exception {
		String status = "";
		try {
			System.out.println("Enter following details of the item you wish to purchase: itemId,quantity");
			int itemId = utils.readInputInteger();
			int quantity = utils.readInputInteger();
			System.out.println("Enter the credit card details to make the purchase");
			System.out.println("Enter name on the credit card");
			String creditCardName = utils.readInputString();
			System.out.println("Enter the credit card number");
			int creditCardNumber = utils.readInputInteger();
			System.out.println("Enter the expiration date");
			String expirtationDate = utils.readInputString();
			JSONObject response = buyerApi.purchase(itemId, quantity, creditCardName, creditCardNumber,
					expirtationDate,buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				System.out.println("Purchase of the item successful");
			} else {
				System.out.println("Purchase failed with the message: "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return (String) response.get(BuyerConstants.response_status_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String getSellerRating() {
		String status = "";
		try {
			System.out.println("Enter sellerId");
			int sellerId = utils.readInputInteger();
			JSONObject response = buyerApi.getSellerRating(sellerId,buyerId);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				System.out.println("Seller Rating of " + sellerId + " is "
						+ response.get(BuyerConstants.response_sellerRating_key));
			} else {
				System.out.println("Fetching seller rating failed");
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String createAccount() throws Exception {
		String status = "";
		try {
			String username;
			String password;
			System.out.println("Enter username");
			username = utils.readInputString();
			System.out.println("Enter password");
			password = utils.readInputString();
			JSONObject response = buyerApi.createAccount(username, password);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase(BuyerConstants.success)) {
				this.buyerId = (int) response.get(BuyerConstants.response_sellerId_key);
			}
			return (String) response.get(BuyerConstants.response_status_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private String login() {
		String status = "";
		try {
			String username;
			String password;
			System.out.println("Enter username");
			username = utils.readInputString();
			System.out.println("Enter password");
			password = utils.readInputString();
			JSONObject response = buyerApi.login(username, password);
			status = (String) response.get(BuyerConstants.response_status_key);
			if (status.equalsIgnoreCase("success")) {
				this.buyerId = (int) response.get(BuyerConstants.response_sellerId_key);
			}
			return (String) response.get(BuyerConstants.response_status_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
