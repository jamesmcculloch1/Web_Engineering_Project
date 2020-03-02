/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: NewIssueServlet handles adding a new issue to the database when given a valid
    "category", "title" and "description". Redirects to dashboard when done.
 */

package controllers.Users;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class NewIssueServlet
 */
@WebServlet("/User/NewIssueServlet")
public class NewIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewIssueServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getUserPrincipal().getName();
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sCategory = request.getParameter("category");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String username = request.getUserPrincipal().getName();

		IssueCatagories category = null;
		if (Helper.isValidString(sCategory)) {
		category = IssueCatagories.getIssueCatagory(sCategory);
		}

		String address = "/Shared/Dashboard";

		if (category != null && Helper.isValidString(title) && Helper.isValidString(description)) {

			//create a new issue with the users data
			if (Helper.isValidString(username)) {
				Issue newIssue = new Issue(username, description, title, category);
				newIssue.addIssueToDB();

			}
		}

		response.sendRedirect( request.getContextPath() + address);
		return;
	}



}
