/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: Dashboard provides a list of issues to the user and itstaff. It sends relevant data to UserPage.jsp
    or ITStaff.jsp.
 */

package controllers.Shared;
import models.*;


import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Shared/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println(Issue.getAllIssues().size());

		String username = request.getUserPrincipal().getName();
		String address = "";
		List<Issue> issues = new ArrayList<>();




		/*CHECKS USER ROLES AND REDIRECTS TO THAT SPECIFIC USER'S DASHBOARD*/
		if(request.isUserInRole("User")){


			for (int i = 0; i < 7; i++) {
			//gets all the issues that have knowledge base status and set the display to the user as resolved so they cannot see it was added to the knowledge base
				for (Issue issue: Issue.getIssuesByUsername(username)) {

					if (issue.getStatus() == IssueStatus.KNOWLEDGE_BASE) {
						issue.setStatus(IssueStatus.RESOLVED);
					}

					switch(i) {
						case 0:
							if (issue.getStatus() == IssueStatus.NEW) {
								issues.add(issue);
							} else {
								break;
							}
						case 1:
							if (issue.getStatus() == IssueStatus.IN_PROGRESS) {
								issues.add(issue);
							} else {
								break;
							}

						case 2:
							if (issue.getStatus() == IssueStatus.NOT_ACCEPTED) {
								issues.add(issue);
							} else {
								break;
							}

						case 3:
							if (issue.getStatus() == IssueStatus.COMPLETED) {
								issues.add(issue);
								request.setAttribute("newAlert", true);
							} else {
								break;
							}
						case 4:
							if (issue.getStatus() == IssueStatus.WAITING_REPORTER) {
								issues.add(issue);
								request.setAttribute("newAlert", true);
							} else {
								break;
							}
						case 5:
							if (issue.getStatus() == IssueStatus.WAITING_THIRDPARTY) {
								issues.add(issue);
							} else {
								break;
							}
						case 6:
							if (issue.getStatus() == IssueStatus.RESOLVED) {
								issues.add(issue);
							} else {
								break;
							}
						}
					}
				}

			request.setAttribute("myIssues", issues);

			address = "/WEB-INF/jsp/User/UserPage.jsp";

		} else if(request.isUserInRole("IT_Staff")) {

			for (int i = 0; i < 7; i++) {
			//gets all the issues that have knowledge base status and set the display to the user as resolved so they cannot see it was added to the knowledge base
				for (Issue issue: Issue.getAllIssues()) {
					switch(i) {
						case 0:
							if (issue.getStatus() == IssueStatus.NEW) {
								issues.add(issue);
							} else {
								break;
							}
						case 1:
							if (issue.getStatus() == IssueStatus.NOT_ACCEPTED) {
								issues.add(issue);
							} else {
								break;
							}
						case 2:
							if (issue.getStatus() == IssueStatus.IN_PROGRESS) {
								issues.add(issue);
							} else {
								break;
							}
						case 3:
							if (issue.getStatus() == IssueStatus.COMPLETED) {
								issues.add(issue);
							} else {
								break;
							}
						case 4:
							if (issue.getStatus() == IssueStatus.WAITING_REPORTER) {
								issues.add(issue);
							} else {
								break;
							}
						case 5:
							if (issue.getStatus() == IssueStatus.WAITING_THIRDPARTY) {
								issues.add(issue);
							} else {
								break;
							}
						case 6:
							if (issue.getStatus() == IssueStatus.RESOLVED) {
								issues.add(issue);
							} else {
								break;
							}
						case 7:
							if (issue.getStatus() == IssueStatus.KNOWLEDGE_BASE) {
								issues.add(issue);
							} else {
								break;
							}
						}
					}
				}


			//gets all the issues
			request.setAttribute("myIssues", issues);

			address = "/WEB-INF/jsp/IT/ITStaff.jsp";


			if(request.getParameter("issueid") != null){

				int id = Integer.parseInt(request.getParameter("issueid"));

				//stops itstaff from accessing issues that don't exist in the DB
				if (!Helper.validateID(id)) {
					response.sendRedirect( request.getContextPath() + "/Shared/Dashboard");
					return;
				}
					
				Issue selectedIssue = Issue.getIssue(id);
				

				request.setAttribute("selectedIssue", selectedIssue);
				request.setAttribute("selectedComment", selectedIssue.getComments(false));
				
				//prevents accessing and changing status of knowledge base articles from dashboard
				if (selectedIssue.getStatus() == IssueStatus.KNOWLEDGE_BASE && request.isUserInRole("IT_Staff")) {
					request.setAttribute("isSelected", false);
					response.sendRedirect( request.getContextPath() + "/Shared/Dashboard");
					return;
				} else
					request.setAttribute("isSelected", true);

				//allows the change from add comment to add resolution in ITStaff.jsp
				if (selectedIssue.getStatus() == IssueStatus.RESOLVED) {
					request.setAttribute("isComplete", true);
				} else {
					request.setAttribute("isComplete", false);
				}



			} else {
				request.setAttribute("isSelected", false);
			}


		} else {
			//add default error.
			request.setAttribute("error", "You are not logged in as a User nor an ITStaff member");
			address = "/WEB-INF/jsp/public/LoginError.jsp";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
