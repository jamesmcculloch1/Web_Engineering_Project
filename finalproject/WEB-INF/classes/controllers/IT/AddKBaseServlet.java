/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description:  AddKBaseServlet is responsible for adding the resolved issues selected by the itstaff
    to the knowledge base. It takes an array of "issueid" and adds them to the knowledge base.
 */

package controllers.IT;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Used to add an issue to the knowledge base
 */
@WebServlet("/IT/AddKBaseServlet")
public class AddKBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddKBaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String address = "/Shared/Dashboard";
		
		//each checkbox you select before clicking "Submit to KB" will add the issueid to an array
		String[] params = request.getParameterValues("issueid");
		
		if (params == null || params.length == 0) {
			response.sendRedirect(request.getContextPath() + address);
			return;
		}

		List<Issue> issues = new LinkedList<Issue>();
		List<Integer> issueIDs = new LinkedList<Integer>();

		//gets me a list of the issueIDs the staff member wishes to add to the knowledge base
		for (String string: params) {
			if (Helper.isNumeric(string)) {
				issueIDs.add(Integer.parseInt(string));
			}
		}

		//gets me all the issues that I want to add to the knowledge base
		for (Integer i: issueIDs) {
			if (Helper.validateID(i)) {
				issues.add(new Issue(Issue.getIssue(i)));
			}
		}

		//adds issue to the knowledge base
		for (Issue issue: issues) {
				issue.updateIssueStatus(IssueStatus.KNOWLEDGE_BASE);
		}

		response.sendRedirect(request.getContextPath() + address);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
