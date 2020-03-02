<%--
Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
Student No: 3291441, 3276255, 3088810 , 3189731
Date: 08/06/2019
Course: SENG2050
Assignment: Assignment 3

Description: UserIssue jsp displays the users selected issue to view and update.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en-au"  >
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
        <%-- Navigation Menu --%>
        <div class="navigation">
          <span class="logo">T-issue</span>
          <%-- Nav links--%>
          <div class="links" id="menu">
            <a href="${pageContext.request.contextPath}/Shared/Dashboard" class="second">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Shared/KBaseServlet" class="first">Knowledge-Base</a>
            <a href="${pageContext.request.contextPath}/Shared/Logout" class="last">Logout</a>
          </div>
          <%-- mobile menu button --%>
        <a style="background-image:url(&#034${pageContext.request.contextPath}/img/hamburger.png&#034);" href="javascript:void(0);" class="hamburger" onclick="revealNav()"></a>
        </div>


        <h1>Issue View</h1>

        <div class="selectedIssue" id="UserIssueView">
          <h3>Selected Issue</h3>

          <%--Form to submit user changes to the database based off of the selected issue--%>
          <form method="post" name="selectIssueForm" action="${pageContext.request.contextPath}/Shared/AddCommentServlet" onsubmit="return validateChange()">
              <span class="title"><c:out value="${issue.title}"></c:out></span><br />
              <span class="author"><c:out value="${issue.username}"></c:out></span>
              <span class="date"><c:out value="${issue.dateCreated}"></c:out></span><br />


              <c:choose>
                <%--
                  If the status of the issue is 'RESOLVED', 'KNOWLEDGE_BASE', 'WAITING_THIRDPARTY' or 'NEW'
                  set a hidden dropdown menu to the current status as to stop any changes to the issues status
                  and not affect validation when adding comments.
                --%>
                <c:when test="${issue.status=='RESOLVED' || issue.status=='KNOWLEDGE_BASE' || issue.status=='WAITING_THIRDPARTY' || issue.status=='NEW'}">
                  <select style="display:none;" name="statusDropdown" class="dropdown">
                    <option value="${issue.status}">current status</option>
                  </select><br />
                </c:when>
                <%--
                  otherwise display a dropdown menu that has options to select
                --%>
                <c:otherwise>
                  <select name="statusDropdown" class="dropdown">
                    <option value="option">change status:</option>
                    <c:choose>
                      <%--when an issue's status is marked as complete show options to accept or reject the solution provided --%>
                      <c:when test="${issueCompleted}">
                        <option value="resolved">Resolution Accepted</option>
                        <option value="not_accepted">Resolution Rejected</option>
                      </c:when>
                      <%-- otherwise general correspondence option shown that changes the status to 'IN_PROGRESS'--%>
                      <c:otherwise>
                        <option value="in_progress">waiting for response</option>
                      </c:otherwise>
                    </c:choose>
                  </select><br />
                </c:otherwise>
              </c:choose>


              <h4>Description</h4>
              <%--Display the issues description--%>
              <p>
                <c:out value="${issue.content}"></c:out>
              </p>

            <h4>Comments</h4>
            <div class="comments">
              <%--
                For each gets each comment stored in that issue and displays it to the user.
              --%>
              <c:forEach var="issueComment" items="${issueComments}">
                <div class="comment">
                  <%-- GET COMMENT AUTHOR --%>
                  <span class="author"><c:out value="${issueComment.author}"></c:out></span>
                  <%-- GET COMMENT SUBMISSION DATE --%>
                  <span class="date"><c:out value="${issueComment.dateCreated}"></c:out></span><br />
                  <p>
                    <%-- GET COMMENT BODY --%>
                    <c:out value="${issueComment.body}"></c:out>
                  </p>
                </div>
              </c:forEach>
            </div>
            <%--ADD A NEW COMMENT SECTION--%>
            <c:choose>
              <%--when the issue's status is set to 'RESOLVED' or 'KNOWLEDGE_BASE' disable the ability make a comment on the issue as it is no longer active--%>
              <c:when test="${issue.status=='RESOLVED' || issue.status=='KNOWLEDGE_BASE'}">
                <p>  </p>
              </c:when>
              <%--otherwise show  textbox to accept comments to the issue--%>
              <c:otherwise>
                <h4>New Comment</h4>

                <textarea name="newComment" placeholder="Enter comment"></textarea>
                <input type="hidden" name="issueid" value="${issue.issueID}"/>
                <input type="submit" class="button" name="submit" value="submit"/>
              </c:otherwise>
            </c:choose>
          </form>
        </div>
      </div>
      <%--FOOTER--%>
      <footer>
        <span>T-issue Reporting System &copy; </span>
      </footer>
  </body>
</html>
