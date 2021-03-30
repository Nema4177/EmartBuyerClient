package com.emart.networt;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RestErrorHandler extends DefaultResponseErrorHandler {
		  @Override
		  public void handleError(ClientHttpResponse response) throws IOException {
			  System.out.println("API call has returned has failed, status code is "+response.getStatusCode());
		  }
		}
