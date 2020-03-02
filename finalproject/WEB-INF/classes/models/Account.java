/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: This class loads the Account information that is stored in the database
    for a given username, use the getAccountFromDB static method!
 */

package models;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.naming.NamingException;

/* This class loads the Account information that is stored in the database
 * for a given username, use the getAccountFromDB static method!
 */
public class Account implements Serializable{
	
	//private variables
	private String firstName;
	private String lastName;
	private String email;
	private int contactNumber;
	//Constructor
	public Account() {
		
	}
	
	public static Account getAccountFromDB(String username)
	{
		Account account = new Account();
		String query = "SELECT * FROM accounts WHERE username=?";
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//Database Fetch
			 connection = Config.getConnection(); //connect to DB
			 ps = connection.prepareStatement(query); //prepare statement for DB
			 ps.setString(1, username);
			 rs = ps.executeQuery(); //send the statement to the DB & store the results as a result set
			 rs.beforeFirst(); //Move cursor to the start of the table
			 
			 rs.next();  //move cursor to first row
					 		 			 
			//load data into the class
			account.setContactNumber(rs.getInt("contactnumber"));
			account.setEmail(rs.getString("email"));
			account.setFirstName(rs.getString("firstname"));
			account.setLastName(rs.getString("lastname"));
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return account;
	}
	
	// Start of getter / setter methods for private variables
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}
	// end of getter / setter methods for private variables
}
