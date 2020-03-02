/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: Logout logs the user out and redirects them to the login page.
 */

package controllers.Shared;


import javax.servlet.RequestDispatcher;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


/**
 * Servlet implementation class KBaseServlet
 */
@WebServlet("/Shared/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			//end session
      HttpSession session = request.getSession(false);

      if( session != null ) {
      	session.invalidate(); //destroy session
			}
			String path = request.getContextPath();
		response.sendRedirect(path + "/Login");
	}

}
