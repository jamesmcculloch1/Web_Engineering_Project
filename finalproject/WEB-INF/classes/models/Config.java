/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: Used to get a connection for our database.
 */

package models;

import javax.sql.*;
import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Config {
	public static Connection getConnection() throws NamingException, SQLException {
		try {
			DataSource datasource =
				(DataSource)new InitialContext().lookup("java:/comp/env/SENG2050_A3");

			Connection connection = datasource.getConnection();
			return connection;
		}
		catch (NamingException ne) {
			System.err.println(ne.getMessage());
			System.err.println(ne.getStackTrace());
			throw ne;
		}
		catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			System.err.println(sqle.getStackTrace());
			throw sqle;
		}
	}
}

