package com.emart.driver.implementation;

import java.time.Duration;
import java.time.Instant;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.utils.BuyerConstants;
import com.emart.utils.Utils;
import com.google.gson.Gson;
import com.emart.buyer.BuyerInterfaceApi;
import com.emart.data.CartItem;
import com.emart.data.Item;
import com.emart.data.itemsList;
import com.emart.data.ItemsPurchased;
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
	
	private Gson gson = new Gson();

	@PostConstruct
	@Override
	public void start() {
		try {
			int status = 400;
			int input = 0;
			while (status != BuyerConstants.success) {
				System.out.println("Select an option\n 1.Create an account\n " + "2.Login into account\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> status = createAccount();
				case 2 -> status = login();
				}
				if (status != BuyerConstants.success) {
					System.out.println("Operation falied, please try again");
				} else if (status == BuyerConstants.success) {
					System.out.println("Operation success");
				}
			}
			while (input != 10) {
				System.out.println("Select an option\n" + "1.Get Seller Rating\n" + "2.Search item with category\n"
						+ "3.Add item to cart\n" + "4.Remove item from cart\n" + "5.Display cart\n" + "6.Clear cart\n"
						+ "7.Purchase an item\n" + "8.Push Feedback\n" + "9. getBuyerHistory\n" + "10.Logout\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> status = getSellerRating();
				case 2 -> status = searchItemWithCategory();
				case 3 -> status = addItemToCart();
				case 4 -> status = removeItemFromCart();
				case 5 -> status = displayCart();
				case 6 -> status = clearCart();
				case 7 -> status = purchaseItem();
				case 8 -> status = pushFeedback();
				case 9 -> status = getBuyerHistory();
				case 10 -> status = logout();
				}
				System.out.println(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int displayCart() {
		Instant start = Instant.now();
		int status = 0;
		try {
			JSONObject response = buyerApi.displayCart(buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				itemsList itemList = gson.fromJson((String)response.get(BuyerConstants.response_itemsList_key), itemsList.class);
				if (itemList.getCartItems().size() == 0) {
					System.out.println("Search returned zero items");
				}
				for (CartItem item: itemList.getCartItems()) {
					String itemName = item.getItemName();
					int itemId = item.getItemId();
					int quantity = item.getQuantity();
					int sellerId = item.getSellerId();
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

	private int clearCart() {
		Instant start = Instant.now();
		int status = 200;
		try {
			buyerApi.clearCart(buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int removeItemFromCart() {
		int status = 0;
		try {
			System.out.println("Enter itemid that you want to remove to cart");
			int itemId = utils.readInputInteger();
			System.out.println("Enter the quantity that you want to remove");
			int quantity = utils.readInputInteger();
			Instant start = Instant.now();
			JSONObject response = buyerApi.removeItemFromCart(buyerId, itemId, quantity);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				System.out.println("Item removed with the response message "+response.get(BuyerConstants.response_message_key));
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

	private int addItemToCart() {
		int status = 0;
		try {
			System.out.println("Enter itemid that you want to add to cart");
			int itemId = utils.readInputInteger();
			System.out.println("Enter itemName that you want to add to cart");
			String itemName = utils.readInputString();
			System.out.println("Enter the sellerId of the item");
			int sellerId = utils.readInputInteger();
			System.out.println("Enter the quantity that you want to add");
			int quantity = utils.readInputInteger();
			Instant start = Instant.now();
			JSONObject response = buyerApi.addItemToCart(buyerId, sellerId,itemId, itemName.trim(),quantity);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				
				System.out.println("Items added: "+response.get(BuyerConstants.response_message_key));
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

	private int searchItemWithCategory() {
		int status = 0;
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
			if(inputArray.length >1) {
				keywords = inputArray[1];
			}
			Instant start = Instant.now();
			JSONObject response = buyerApi.searchItems(category, keywords, buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				itemsList itemList = gson.fromJson((String)response.get(BuyerConstants.response_itemsList_key), itemsList.class);
				if (itemList.getItems().size() == 0) {
					System.out.println("Search returned zero items");
				}
				for (Item item: itemList.getItems()) {
					String itemName = item.getName();
					int itemId = item.getId();
					Double salePrice = item.getPrice();
					int quantity = item.getQuantity();
					int sellerId = item.getSellerId();
					System.out.println("ItemName: " + itemName + " itemId: " + itemId + " salePrice: " + salePrice
							+ " quantity: " + quantity+" sellerId: "+sellerId);
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

	private int logout() {
		int status = 0;
		try {
			Instant start = Instant.now();
			JSONObject response = buyerApi.logout(buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				System.out.println("Logout success");
			} else {
				System.out.println(
						"Logout failed with the message: " + response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int getBuyerHistory() throws Exception {
		int status = 0;
		try {
			Instant start = Instant.now();
			JSONObject response = buyerApi.getBuyerHistory(buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status != BuyerConstants.success) {
				System.out.println("Fetching buyer history unsuccessful");
			} else {
				itemsList itemList = gson.fromJson((String)response.get(BuyerConstants.response_itemsList_key), itemsList.class);
				if (itemList.getItemsPurchased().size() == 0) {
					System.out.println("Items Purchased is 0");
				}
				for (ItemsPurchased item: itemList.getItemsPurchased()) {
					String itemName = item.getItemName();
					int itemId = item.getItemId();
					int sellerId = item.getSellerId();
					Double salePrice = item.getItemPrice();
					int quantity = item.getQuantity();
					int feedback = item.getFeedback();
					String feedbackString = "";
					switch (feedback) {
					case 0 -> feedbackString = "Not provided";
					case 1 -> feedbackString = "Upvote";
					case -1 -> feedbackString = "Downvote";
					}
					System.out.println("ItemName: " + itemName + " itemId: " + itemId + " salePrice: " + salePrice
							+ " quantity: " + quantity + " sellerId: " + sellerId
							+ " Feedback: " + feedbackString);
				}
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int pushFeedback() throws Exception {
		int status = 0;
		try {
			System.out.println("Enter itemId for which you want to give feedback");
			int itemId = utils.readInputInteger();
			System.out.println("Enter sellerId for which you want to give feedback");
			int sellerId = utils.readInputInteger();
			System.out.println("Enter feedback you wish to enter");
			int feedback = utils.readInputInteger();
			Instant start = Instant.now();
			JSONObject response = buyerApi.pushFeedback(itemId,buyerId,sellerId,feedback);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				System.out.println("Pushing feedback successful");
			} else {
				System.out.println("Pushing feedback was not successful with the message"+response.get(BuyerConstants.response_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return status;
	}

	private int purchaseItem() throws Exception {
		int status = 0;
		try {
			System.out.println("Enter the credit card details to make the purchase");
			System.out.println("Enter name on the credit card");
			String creditCardName = utils.readInputString();
			System.out.println("Enter the credit card number");
			int creditCardNumber = utils.readInputInteger();
			System.out.println("Enter the expiration date");
			String expirtationDate = utils.readInputString();
			Instant start = Instant.now();
			JSONObject response = buyerApi.purchase(creditCardName, creditCardNumber, expirtationDate,
					buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				System.out.println("Purchase of the item successful");
			} else {
				System.out.println("Purchase failed with the message: "
						+ response.get(BuyerConstants.response_failure_message_key));
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int getSellerRating() {
		int status = 0;
		try {
			System.out.println("Enter sellerId");
			int sellerId = utils.readInputInteger();
			Instant start = Instant.now();
			JSONObject response = buyerApi.getSellerRating(sellerId, buyerId);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				System.out.println("Upvotes of " + sellerId + " is "
						+ response.get("upvotes")+" Downvotes of the seller are "+response.get("downvotes"));
			} else {
				System.out.println("Fetching seller rating failed");
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int createAccount() throws Exception {
		int status = 0;
		try {
			String username;
			String password;
			String name;
			int buyerId;
			System.out.println("Enter BuyerId");
			buyerId = utils.readInputInteger();
			System.out.println("Enter username");
			username = utils.readInputString();
			System.out.println("Enter name");
			name = utils.readInputString();
			System.out.println("Enter password");
			password = utils.readInputString();
			Instant start = Instant.now();
			JSONObject response = buyerApi.createAccount(buyerId,username, name, password);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				st = (long) response.get(BuyerConstants.response_buyerId_key);
				this.buyerId = st.intValue();
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int login() {
		int status = 0;
		try {
			String username;
			String password;
			System.out.println("Enter username");
			username = utils.readInputString();
			System.out.println("Enter password");
			password = utils.readInputString();
			Instant start = Instant.now();
			JSONObject response = buyerApi.login(username, password);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			Long st = (long) response.get(BuyerConstants.response_status_key);
			status = st.intValue();
			if (status == BuyerConstants.success) {
				st = (long) response.get(BuyerConstants.response_buyerId_key);
				this.buyerId = st.intValue();
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
