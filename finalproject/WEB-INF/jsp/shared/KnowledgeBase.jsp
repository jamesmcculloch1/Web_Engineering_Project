<%--
Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
Student No: 3291441, 3276255, 3088810 , 3189731
Date: 08/06/2019
Course: SENG2050
Assignment: Assignment 3

Description: KnowledgeBase.jsp displays all archive entries made by the staff for users to access
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>


<!DOCTYPE html>
<html lang="en-au">
  <head>
    <meta charset="utf-8"  />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
  </head>


  <body>
    <div class="page-container">
      <div class="content-div">

        <%--Navigation Menu--%>
        <div class="navigation">
          <span class="logo">T-issue</span>
          <%-- Nav links--%>
          <div class="links" id="menu">
            <a href="${pageContext.request.contextPath}/Shared/Dashboard" class="second">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Shared/Logout" class="last">Logout</a>
          </div>
          <%--mobile menu button --%>
          <a style="background-image:url(&#034${pageContext.request.contextPath}/img/hamburger.png&#034);" href="javascript:void(0);" class="hamburger" onclick="revealNav()"></a>
        </div>

        <h1>Knowledge Base</h1>
        <div class="container">
          <div class="archive-container">
            <h3>Archive</h3>
            <div class="archive">

              <%--For each item in the Knowledge base print it out below--%>
              <c:forEach var="archive" items="${KBase}">
                <%--title as a link to select that issue and display it on the same page--%>
                <a href="${pageContext.request.contextPath}/Shared/KBaseServlet?SelectedID=${archive.issueID}"><c:out value="${archive.title}"></c:out></a><br />
                <%--get category--%>
                <span><i>"<c:out value="${archive.catagory}"></c:out>"</i></span>
                <p>
                  <%--get description--%>
                  <c:out value="${archive.content}"></c:out>
                </p><br /><br />
              </c:forEach>
            </div>
          </div>

      <%--DISPLAYS THE SELECTED ISSUE IF ONE OF THE KNOWLEDGE_BASE ARTICLES HAS BEEN SELECTED--%>
      <div class="selectedIssue">
            <h3>Selected Issue</h3>
            <c:choose>
              <%--When an issue has been selected display that issue--%>
              <c:when test="${issueIsSelected}">
                <%--FORM FOR SUBMITTING COMMENTS MADE REGARDING THE KNOWLEDGE_BASE ARTICLE THAT HAS BEEN SELECTED--%>
                <form method="post" name="selectIssueForm" action="${pageContext.request.contextPath}/Shared/AddCommentServlet?type=kb" onsubmit="return newCom()">
                  <div class="container-head">
                    <span class="title">Title : <c:out value="${selectedIssue.title}"></c:out></span><br />
                    <span class="author">Author: <c:out value="${selectedIssue.username}"></c:out></span>
                    <span class="date">Date Resolved: <c:out value="${selectedIssue.dateResolved}"></c:out></span><br />
                  </div><br />


                  <div>
                    <div class="container-desc">
                      <h4 class="DescMobileText">Description</h4><br />
                      <span class="categoryText"><i>category : "<c:out value="${selectedIssue.catagory}"></c:out>"</i></span><br />
                    </div>
                    <p>
                      <c:out value="${selectedIssue.content}"></c:out>
                    </p>

                    <h4>Resolution</h4>
                    <p>
                      <c:out value="${selectedIssue.resolutionDetails}"></c:out>
                    </p>


                    <h4>Comments</h4>
                    <div class="comments">

                      <%--display each comment connected to the issue--%>
                      <c:forEach var="comment" items="${comments}">
                        <div class="comment">
                          <%-- GET COMMENT AUTHOR --%>
                          <span class="author"><c:out value="${comment.author}"></c:out></span>
                          <%-- GET COMMENT SUBMISSION DATE --%>
                          <span class="date"><c:out value="${comment.dateCreated}"></c:out></span><br />
                          <p>
                            <%-- GET COMMENT BODY --%>
                            <c:out value="${comment.body}">></c:out>
                          </p>
                        </div>
                      </c:forEach>
                    </div>

                    <%--Add a Comment to the knowledge-bage regarding the article--%>
                    <h4>New Comment</h4>
                    <textarea name="newComment" placeholder="Enter comment"></textarea>
                    <input type="hidden" name="issueid" value="${selectedIssue.issueID}"/>
                    <input type="submit" class="button" name="submit" value="submit" />
                  </div>
                </div>
              </form>
            </c:when>
            <%--DEFAULT VIEW WHEN AN ISSUE HAS NOT BEEN SELECTED TO VIEW--%>
            <c:otherwise>
              <p style="width:100%">
                No Knowledge Base items have been selected.
              </p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
    <%--FOOTER--%>
    <footer>
      <span>T-issue Reporting System &copy;</span>
    </footer>
  </body>
</html>
