/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: An enumeration of the categories an issue can be in.
 */

package models;


public enum IssueCatagories {
	NETWORK, SOFTWARE, HARDWARE, EMAIL, ACCOUNT;
	
	
	public static IssueCatagories getIssueCatagory(String category) {
		IssueCatagories cat = null;
		
		if (category.equals("network")) {
			cat = IssueCatagories.NETWORK;
		} else if (category.equals("software")) {
			cat = IssueCatagories.SOFTWARE;
		} else if (category.equals("hardware")) {
			cat = IssueCatagories.HARDWARE;
		}  else if (category.equals("email")) {
			cat = IssueCatagories.EMAIL;
		}  else if (category.equals("account")) {
			cat = IssueCatagories.ACCOUNT;
		}
		
		return cat;
	}
}
