package com.emart.utils;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	
	public int readInputInteger() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		s.trim();
		try{
		    return Integer.parseInt(s);
		}
		catch(NumberFormatException ex){
		    System.out.println("Its not a valid Integer");
		}
		return Integer.MIN_VALUE;
	}
	
	public String readInputString() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		return s.trim();
	}
	
	public Double readInputDouble() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		s.trim();
		try{
		    return Double.parseDouble(s);
		}
		catch(NumberFormatException ex){
		    System.out.println("Its not a valid Double");
		}
		return Double.NEGATIVE_INFINITY;
	}

}
