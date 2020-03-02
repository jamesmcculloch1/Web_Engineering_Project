/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: AddResolutionServlet handles adding the resolution details that the ITStaff inputs
    into the database and returns them to the dashboard.
 */

package controllers.IT;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class AddResolutionServlet
 */
@WebServlet("/IT/AddResolutionServlet")
public class AddResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddResolutionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resolution = (String) request.getParameter("newComment");
		String sIssueID = (String) request.getParameter("issueid");
		String username = request.getUserPrincipal().getName();

		Issue targetIssue = null;

		String address = "/Shared/Dashboard";

		if (Helper.isNumeric(sIssueID) && Helper.isValidString(resolution) && Helper.isValidString(username)) {
			int issueID = Integer.parseInt(sIssueID);

			targetIssue = new Issue(Issue.getIssue(issueID));

			//handles adding resolution to issue (can only be done by itstaff)
			if (Helper.isValidString(username)) {

				targetIssue.setResolution(resolution);



			} else { //log them out if they have an invalid username?
					request.getSession().invalidate();
				}
			}
		
			response.sendRedirect(request.getContextPath() + address);
			return;
		}
}
