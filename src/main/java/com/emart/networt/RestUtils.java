package com.emart.networt;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.emart.utils.BuyerConstants;

@Component
public class RestUtils {
	

	public JSONObject getRequest(String url) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestErrorHandler());
		ResponseEntity<String> response
		  = restTemplate.getForEntity(url, String.class);
		HttpStatus statusCode = response.getStatusCode();
		if(!statusCode.equals(HttpStatus.OK)) {
			System.out.println("API call returned status code "+statusCode);
			JSONObject failureResponse = new JSONObject();
			failureResponse.put(BuyerConstants.response_status_key, BuyerConstants.failure);
			return failureResponse;
		}
		String responseBody = response.getBody();
		return (JSONObject) jsonParser.parse(responseBody);
			
		}
	
	public JSONObject postRequest(JSONObject postObject, String url) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestErrorHandler());
		ResponseEntity<String> response
		  = restTemplate.postForEntity(url, postObject, String.class);
		HttpStatus statusCode = response.getStatusCode();
		if(!statusCode.equals(HttpStatus.OK)) {
			System.out.println("API call returned status code "+statusCode);
			JSONObject failureResponse = new JSONObject();
			failureResponse.put(BuyerConstants.response_status_key, BuyerConstants.failure);
			return failureResponse;
		}
		String responseBody = response.getBody();
		return (JSONObject) jsonParser.parse(responseBody);
	}
}


