<%--
  Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
  Student No: 3291441, 3276255, 3088810 , 3189731
  Date: 08/06/2019
  Course: SENG2050
  Assignment: Assignment 3

  Description: UserPage jsp displays the Dashboard home page of the standard user.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-au">

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
  </head>


<%--
  Choose statement used to determine that if the IT Staff have updated an issue and
  it requires the users input and attention, run the onload function which alerts the user of an update
  otherwise, just run the page as normal without loading anything.
--%>
<c:choose>
  <c:when test="${newAlert}">
    <body onload="return newAction('${newAlert}')">
  </c:when>
  <c:otherwise>
    <body>
  </c:otherwise>
</c:choose>

    <div class="page-container">
      <div class="content-div">
        <%--Navigation Menu --%>
        <div class="navigation">
          <span class="logo">T-issue</span>
          <%--Nav Links--%>
          <div class="links" id="menu">
            <a href="${pageContext.request.contextPath}/Users/MyAccount" class="first">My Account</a>
            <a href="${pageContext.request.contextPath}/Shared/KBaseServlet" class="second">Knowledge-Base</a>
            <a href="${pageContext.request.contextPath}/Shared/Logout" class="last">Logout</a>
          </div>
          <%-- mobile menu button --%>
          <a style="background-image:url(&#034${pageContext.request.contextPath}/img/hamburger.png&#034);" href="javascript:void(0);" class="hamburger" onclick="revealNav()"></a>
        </div>


        <h1>Dashboard</h1>

        <div class="container">
        <%--DISPLAY ONLY USERS ISSUES THEY HAVE SUBMITTED--%>
        <%--Active Issues for User Section--%>
          <table class="userIssues">
            <thead>
              <tr>
                <%--Heading--%>
                <td colspan="2"><h3>Active Issues</h3></td>
              </tr>
              <tr>
                <th>Current Status</th>
                <th>Issue Title</th>
              </tr>
            </thead>
            <tbody>
              <%--
                For each of the user issues they have submitted. print each one out in the table with its
                status and title as a link to redirect to the userIssues.jsp via Servlet)
              --%>
              <c:forEach var="myIssue" items="${myIssues}">
                <tr>
                  <%--displays the current status of each issue--%>
                  <c:choose>
                    <%--Change the visual representation of a rejected "NOT_ACCEPTED' status to 'IN_PROGRESS' on user side--%>
                    <c:when test="${myIssue.status=='NOT_ACCEPTED'}">
                      <td><i><c:out value="IN_PROGRESS"></c:out></i></td>
                    </c:when>
                    <%--otherwise just display the current status --%>
                    <c:otherwise>
                      <td><i><c:out value="${myIssue.status}"></c:out></i></td>
                    </c:otherwise>
                  </c:choose>

                  <%--titleof the issue displayed as a link to redirect to the userIssues.jsp via Servlet)--%>
                  <td><a class="issueSelect" href="${pageContext.request.contextPath}/User/UserIssueServlet?issueid=<c:out value="${myIssue.issueID}"/>"><c:out value="${myIssue.title}"></c:out></a></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <%--
            New issue section for users to submit a new issue to the IT Staff
          --%>
          <form class="newIssue" name="newIssue" method=post action="${pageContext.request.contextPath}/User/NewIssueServlet" onsubmit="return validateNewIssue()">
            <h3>Submit Issue</h3>
            <p>Please enter the details of your issue below.</p>
            <%--SELECT CATEGORY OF ISSUE--%>
            <select name="category" class="dropdown">
              <option value="option">Select option:</option>
              <option value="network">Network</option>
              <option value="software">Software</option>
              <option value="hardware">Hardware</option>
              <option value="email">Email</option>
              <option value="account">Account</option>
            </select><br />
            <%--TITLE INPUT--%>
            <input type="text" class="textIn" id="title" placeholder="Issue title:" name="title" /><br />
            <%--DESCRIPTION INPUT--%>
            <textarea class="Description" name="description" placeholder="What is your issue?"></textarea><br />
            <input type="submit" class="button" name="submit" value="submit" />
          </form>
        </div>
      </div>
    </div>

    <%-- Footer --%>
    <footer>
      <span>T-issue Reporting System &copy; </span>
    </footer>

  </body>

</html>
