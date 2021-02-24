package com.emart.selector;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.emart.driver.implementation.BuyerDriver;

@Component
public class Selector {
	
private int interfaceType = 0;

@Autowired
BuyerDriver buyerDriver;
	
//@PostConstruct
	public void selector() {
		while(interfaceType !=1 && interfaceType !=2) {
			interfaceType = selectInterface();
			if(interfaceType == 1) {
				buyerDriver.start();
			}else if(interfaceType ==2) {
				buyerDriver.start();
			}
		}
	}

	private int selectInterface() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please select the input\n 1.Sellers interface\n 2.Buyers interface\n");
		int myInt = scanner.nextInt();
		if(myInt == 1) {
			System.out.println("Sellers interface selected\n");
		}else if(myInt == 2) {
			System.out.println("Buyers interface selected\n");
		}else {
			System.out.println("Please select a valid option");
		}
		return myInt;
	}
}
