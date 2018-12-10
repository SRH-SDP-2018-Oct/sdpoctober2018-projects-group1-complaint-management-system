package com.srh.cms.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatch {
	String emailRegex = "^(.+)@(.+)$";
	//String passwordRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$";
	
	 
	
	public boolean checkMail(String email){
	Pattern pattern = Pattern.compile(emailRegex);
	Matcher matcher = pattern.matcher(email);
    return matcher.matches();
	}
	
	
	public boolean checkPassword(String password){
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(password);
	    return matcher.matches() ;
		}
}
