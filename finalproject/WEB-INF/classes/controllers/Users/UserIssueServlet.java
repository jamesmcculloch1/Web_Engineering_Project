/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: UserIssueServlet is used to pass the issue specified in the "issueid" parameter
    to the UserIssue.jsp page along with relevant information to that issue that the JSP needs.
    
    If no "issueid" is provided, will redirect to the Dashboard servlet.
 */

package controllers.Users;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class GetUserIssue
 */
@WebServlet("/User/UserIssueServlet")
public class UserIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIssueServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//gets the issueid from the url
				String issueID = (String) request.getParameter("issueid");
				int index = 0;

				String username = request.getUserPrincipal().getName();
				String address = "/Shared/Dashboard";

				if (issueID != null && !issueID.equals("")) {
					if (Helper.isNumeric(issueID)) {
						index = Integer.parseInt(issueID);

						//makes sure the issueID is between 1 and the max amount of issues in the database
						if (index > 0 && index <= Issue.getAllIssues().size()) {

							Issue issue = new Issue(Issue.getIssue(index));
							

							//makes sure that this issue belongs to the user before giving them the information
							if (issue.getUsername().equals(username)) {
								request.setAttribute("issue", issue);
								request.setAttribute("issueComments", issue.getComments(false));

								boolean issueCompleted = false;

								if (issue.getStatus() == IssueStatus.COMPLETED) {
									issueCompleted = true;
								}
								//adds a request object that is true if the issue is completed
								request.setAttribute("issueCompleted", issueCompleted);


								address = "/WEB-INF/jsp/User/UserIssue.jsp";
								RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
								dispatcher.forward(request, response);
								return;
							}
						}
					}
				}

				response.sendRedirect(request.getContextPath() + address);
				return;
	}
}
