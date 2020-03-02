/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: KBaseServlet handles adding all the knowledge base articles to a list to be
    displayed to the user in KnowledgeBase.jsp. 
 */

package controllers.Shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Helper;
import models.Issue;
import models.IssueStatus;

/**
 * Servlet implementation class KBaseServlet
 */
@WebServlet("/Shared/KBaseServlet")
public class KBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBaseServlet() {
        super();
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
		
		List<Issue> kBaseIssues = new LinkedList<Issue>();

		//gets all the issues that have knowledge base status and adds them to a list
		for (Issue issue: Issue.getAllIssues()) {
			if (issue.getStatus() == IssueStatus.KNOWLEDGE_BASE) {
				kBaseIssues.add(issue);
			}
		}

		request.setAttribute("KBase", kBaseIssues);

		String selectedID = request.getParameter("SelectedID"); //get the selectedID
		boolean issueIsSelected = Helper.validateID(selectedID);
		request.setAttribute("issueIsSelected", issueIsSelected);

		//-------if the user has selected a knowledge base article----
		//get the knowledge base comments for this issue
		if(issueIsSelected)
		{
			//get the issue object from the db and load it into the request scope
			Issue selectedIssue = Issue.getIssue(Integer.parseInt(selectedID));
			
			boolean kBaseArticle = false;
			//prevents user from accessing invalid issue ids
			for (Issue issue: kBaseIssues) {
				if (issue.getIssueID() == selectedIssue.getIssueID()) {
					kBaseArticle = true;
				}
			}
			
			if (!kBaseArticle) {
				response.sendRedirect(request.getContextPath() + "/Shared/Dashboard");
				return;
			}
			
			request.setAttribute("selectedIssue", selectedIssue);
			

			//get the issue comments to display for the kb article as well as the knowledge base comments
			List<Issue.Comment> comments = new ArrayList<>();
			
			
			//appends that this comment is an issue comment to the body of the comment
			for (Issue.Comment comment: selectedIssue.getComments(false)) {
				String commentCopy = comment.getBody();
				comment.setBody("Issue - " + commentCopy);
				comments.add(comment); // then get kb comments
			}
			//appends that this comment is a knowledge base comment to the body of the comment
			for (Issue.Comment comment: selectedIssue.getComments(true)) {
				String commentCopy = comment.getBody();
				comment.setBody("Knowledge base - " + commentCopy);
				comments.add(comment); // then get kb comments
			}


			request.setAttribute("comments", comments);
		}




		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/shared/KnowledgeBase.jsp");
		dispatcher.forward(request, response);
		return;
	}

}
