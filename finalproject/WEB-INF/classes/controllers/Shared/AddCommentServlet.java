/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description:  AddCommentServlet handles adding comments to both regular issues and knowledge base articles
    for users and itstaff. It uses the parameters "type", "newComment" and "issueid" for knowledge base articles
    and "statusDropdown", "newComment" and "issueid" for non-knowledge base articles.
    
    If invalid parameters are provided, it will redirect to the Dashboard servlet.
 */

package controllers.Shared;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet("/Shared/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String category = (String) request.getParameter("statusDropdown");
		String comment = (String) request.getParameter("newComment");
		String sIssueID = (String) request.getParameter("issueid");
		String username = request.getUserPrincipal().getName();

		String knowBase = (String) request.getParameter("type");

		int issueID = 0;

		Issue targetIssue = null;

		String address = "/Shared/Dashboard";

		if (Helper.isNumeric(sIssueID)  && Helper.isValidString(comment)) {
			issueID = Integer.parseInt(sIssueID);

			targetIssue = new Issue(Issue.getIssue(issueID));

			//handles adding comments knowledgebase
			if (Helper.isValidString(knowBase) && knowBase.equals("kb")  && Helper.isValidString(username)) {

				targetIssue.addComment(username, comment, true);
				targetIssue.setResolutionDetails("comment");
				//targetIssue.addIssueToDB();

				//returns to the knowledge base showing the relevant article
				address = "/Shared/KBaseServlet?SelectedID=" + sIssueID;

				//handles adding comments to issues
			} else if (Helper.isValidString(category) && Helper.isValidString(username)) {
				IssueStatus status = IssueStatus.getIssueStatus(category);

				//create a new issue with the users data
				targetIssue.addComment(username, comment, false);
				System.out.println("Added comment");



				//NEED TO ADD STATUS UPDATE
				if (status != null && status != IssueStatus.KNOWLEDGE_BASE) {
					targetIssue.updateIssueStatus(status);
				}
				
				boolean issueCompleted = false;

				if (targetIssue.getStatus() == IssueStatus.COMPLETED) {
					issueCompleted = true;
				}

				request.setAttribute("issueid", issueID);

				
				//adds a request object that is true if the issue is completed
				request.setAttribute("issueCompleted", issueCompleted);


			}

		}

		response.sendRedirect(request.getContextPath() + address);
		return;
	}



}
