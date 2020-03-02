/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: Provides useful functions for validating parameters sent through request objects
 */

package models;

public class Helper {
	
	//used for validating strings passed as request parameter strings
	public static boolean isValidString(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		
		return true;
		
	}
	
	//used for validating ints passed as request parameter strings
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str); 
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	//checks if the issueID is a valid entry
	public static boolean validateID(int issueID) {
		if (issueID > 0 && issueID <= Issue.getAllIssues().size()) {
			return true;
		}
		return false;
	}
	
	//checks if the string issueID is a valid entry
	public static boolean validateID(String issueID) {
		if (isNumeric(issueID)) {
			return validateID(Integer.parseInt(issueID));
		}
		return false;
	}

}
