<%--
Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
Student No: 3291441, 3276255, 3088810 , 3189731
Date: 08/06/2019
Course: SENG2050
Assignment: Assignment 3

Description: ITStaff jsp displays the Staff Dashboard.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <%-- Navigation Menu --%>
        <div class="navigation">
          <span class="logo">T-issue</span>
          <%-- Nav links--%>
          <div class="links" id="menu">
            <a href="${pageContext.request.contextPath}/Shared/KBaseServlet" class="first">Knowledge-Base</a>
            <a href="${pageContext.request.contextPath}/Shared/Logout" class="last">Logout</a>
          </div>
          <%-- mobile menu button --%>
          <a style="background-image:url(&#034${pageContext.request.contextPath}/img/hamburger.png&#034);" href="javascript:void(0);" class="hamburger" onclick="revealNav()"></a>
        </div>

        <h1>Dashboard</h1>

        <div class="container">
          <%--Form for submitting resolved issues to the database--%>
          <form class="staffIssueForm" name="submitKB" method="post" action="${pageContext.request.contextPath}/IT/AddKBaseServlet" onsubmit="return validateCheckBoxes()">
            <%--Active Issues table to display ALL issues that have been submitted and their status--%>
            <table class="staffIssue">
              <thead>
              <tr>
                <td class="aIhead" colspan="3">
                  <h3>Active Issues</h3>
                </td>
              </tr>
              <tr>
                <%--set a variable resolvedDisplayed to false as to hide the submit button from view while there are no issues eligible to be submitted--%>
                <c:set var = "resolvedDisplayed" scope="request" value = "${false}"/>


                <%--For each issue--%>
                <c:forEach var="issue" items="${myIssues}">
                    <c:choose>
                      <%--
                          For each issue when the issue has resolution details added and isnt already
                          marked as knowledge base, set the 'resolvedDisplayed' variable used to hid
                          the submit button to true as to reveal the button and allow knowledge-base submissions
                        --%>
                      <c:when test="${issue.resolutionDetails!='' && issue.status!='KNOWLEDGE_BASE'}">
                        <c:set var = "resolvedDisplayed" scope="request" value = "${true}"/>
                      </c:when>
                    </c:choose>
                </c:forEach>

                <%--If there are eligble issues to be submitted to the knowledge base, reveal the submit button--%>
                <c:if test="${resolvedDisplayed!=false}">
                  <td style="padding: 0 20px 0 20px"> <input type="submit" id ="sKB" class="button" name="submit" value="Submit To KB"/></td>
                  <td style="padding: 0 20px 0 20px; text-align: center; background-color:#C9C8C9" colspan="2">To add issues to the Knowledge-Base you must make sure to add its resolution details by selecting the issue. After it has been resolved a button to submit will appear.</td>
                </c:if>
                <%--If there are no eligble issues to be submitted just display a message--%>
                <c:if test="${resolvedDisplayed==false}">
                  <td style="padding: 20px; text-align: center; background-color:#C9C8C9" colspan="3">To add issues to the Knowledge-Base you must make sure to add its resolution details by selecting the issue. After it has been resolved a button to submit will appear.</td>
                </c:if>
              </tr>
              <tr>
                <td id="KB">Mark KB:</td>
                <td id="Stat">Status</td>
                <td id="issue">Issue</td>
              </tr>
              </thead>

              <tbody>
                <%--
                  for Each issue stored in the database, output a checkbox in column 1 and assign the issues ID to a checkbox for marking as knowledge base,
                  output the current status of the issue in column 2 and create a link to the issue that is the title of the issue in column 3.
                --%>
                <c:forEach var="issue" items="${myIssues}">
                  <tr>
                    <c:choose>
                      <%--When the issue has resolution details added and isnt already
                          marked as knowledge base, set the 'resolvedDisplayed' variable used to hide
                          the submit button to true as to reveal the button and reveal the checkbox for that particular issue
                          so it can be added to the knowledge base
                      --%>
                      <c:when test="${issue.resolutionDetails!='' && issue.status!='KNOWLEDGE_BASE'}">
                        <td>
                          <input type="checkbox" name="issueid" value="${issue.issueID}" id="${issue.issueID}"/>
                        </td>
                        <input type="checkbox" name="checkbox1" style="display: none" checked>
                        <c:set var = "resolvedDisplayed" scope="request" value = "${true}"/>
                      </c:when>
                      <%--otherwise if it isnt eligble to be added, display an empty cell--%>
                      <c:otherwise>
                        <td></td>
                      </c:otherwise>
                    </c:choose>
                    <%-- get the issues current status  --%>
                    <td><i><c:out value="${issue.status}"></c:out></i></td>

                    <c:choose>
                      <%--
                        If the issues status is marked as knowledge base, remove the link from
                        the title as to not allow further changes. Changes can be made instead
                        via the knowledge-base link/page
                      --%>
                      <c:when test="${issue.status=='KNOWLEDGE_BASE'}">
                          <td><c:out value="${issue.title}"></c:out></a></td>
                      </c:when>
                      <%--otherwise get the issues title to act as a link to view the issue --%>
                      <c:otherwise>
                        <td><a class="issueSelect" href="${pageContext.request.contextPath}/Shared/Dashboard?issueid=<c:out value="${issue.issueID}"/>"><c:out value="${issue.title}"></c:out></a></td>
                      </c:otherwise>
                    </c:choose>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </form>



          <%-- SELECTED ISSUES SECTION --%>
          <div class="selectedIssue">
              <h3>Selected Issue</h3>

              <c:choose>
                <%-- When an active issue has been selected --%>
                <c:when test="${isSelected}">
                  <c:choose>
                    <%--And when the item has been marked as RESOLVED BY THE USER and isComplete is true--%>
                    <c:when test="${isComplete}">
                      <%--DISPLAY A FORM FROM SUBMITTING CHANGES TO THE ISSUE--%>
                      <form method="post" name="selectIssueForm" action="${pageContext.request.contextPath}/IT/AddResolutionServlet" onsubmit="return validateChange()">
                          <%--display title, author and date--%>
                           <span class="title"><c:out value="${requestScope.selectedIssue.title}"></c:out></span><br />
                           <span class="author"><c:out value="${requestScope.selectedIssue.username}"></c:out></span>
                           <span class="date"><c:out value="${requestScope.selectedIssue.dateCreated}"></c:out></span><br />

                           <%--Hidden select section that keeps the status as what is already set and to allow JavaScript validation--%>
                           <select style="display:none" name="statusDropdown" class="dropdown">
                             <option value="${selectedIssue.status}"></option>
                           </select><br />


                           <h4>Description</h4>
                           <%--display issue description--%>
                           <p>
                             <c:out value="${requestScope.selectedIssue.content}"></c:out>
                           </p>

                           <%--Get and display all comments related to the issue stored on the database--%>
                           <h4>Comments</h4>
                           <div class="comments">
                             <c:forEach var="sComment" items="${selectedComment}">
                               <div class="comment">
                                 <span class="author"><c:out value="${sComment.author}"></c:out></span>
                                 <span class="date"><c:out value="${sComment.dateCreated}"></c:out></span><br />
                                 <p>
                                   <c:out value="${sComment.body}">></c:out>
                                 </p>
                               </div>
                             </c:forEach>
                           </div>

                           <%--
                             Since the issue has been completed and accepted by the user, staff are presented with
                             a comment section for adding resolution details to the issue, after resolution details are
                             added it can be added to the KB
                          --%>
                           <h4>Add Resolution</h4>
                           <textarea name="newComment" placeholder="Enter Resolution Details.."></textarea>
                           <input type="hidden" name="issueid" value="${selectedIssue.issueID}"/>
                           <input type="submit" class="button" name="submit" value="submit"/>
                         </form>

                    </c:when>
                    <%--
                      If the issue has not be accepted and resolved by both parties display the issue with options
                      to change the data, status and add comments to the issue until it has been resolved by the user and IT Staff
                    --%>
                    <c:otherwise>
                         <form method="post" name="selectIssueForm" action="${pageContext.request.contextPath}/Shared/AddCommentServlet" onsubmit="return validateChange()">
                              <%--display title, author and date--%>
                              <span class="title"><c:out value="${requestScope.selectedIssue.title}"></c:out></span><br />
                              <span class="author"><c:out value="${requestScope.selectedIssue.username}"></c:out></span>
                              <span class="date"><c:out value="${requestScope.selectedIssue.dateCreated}"></c:out></span><br />

                              <%--Select dropdown menu section that is used to select the action required on the issue and update its status --%>
                              <select name="statusDropdown" class="dropdown">
                                <option value="option">change status:</option>
                                <%--Updates status to completed--%>
                                <option value="completed">Issue Complete</option>
                                <%--Updates status to waiting on third party--%>
                                <option value="waiting3P">Waiting on third party</option>
                                <%--Updates status to waiting on reporter--%>
                                <option value="waitingReport">Waiting on reporter</option>
                                <%--Updates status to in progress--%>
                                <option value="in_progress">In Progress</option>
                              </select><br />

                              <h4>Description</h4>
                              <%--display issue description--%>
                              <p>
                                <c:out value="${requestScope.selectedIssue.content}"></c:out>
                              </p>


                              <%--Get and display all comments related to the issue stored on the database--%>
                              <h4>Comments</h4>
                              <div class="comments">
                                <c:forEach var="sComment" items="${selectedComment}">
                                  <div class="comment">
                                    <span class="author"><c:out value="${sComment.author}"></c:out></span>
                                    <span class="date"><c:out value="${sComment.dateCreated}"></c:out></span><br />
                                    <p>
                                      <c:out value="${sComment.body}">></c:out>
                                    </p>
                                  </div>
                                </c:forEach>
                              </div>

                              <%--ADD A NEW COMMENT SECTION--%>
                              <%--add a new general correspondance comment to the issue--%>
                              <h4>New Comment</h4>
                              <textarea name="newComment" placeholder="Enter comment"></textarea>
                              <input type="hidden" name="issueid" value="${selectedIssue.issueID}"/>
                              <input type="submit" class="button" name="submit" value="submit"/>
                            </form>
                    </c:otherwise>
                  </c:choose>
                </c:when>
                <%--If an issue has not been selected to be viewed or changed display a message to the IT Staff--%>
                <c:otherwise>
                  <p style="width: 100%">
                    Please select an active issue to view.<br />
                    To begin working on an issue, select the link of the issue you wish to view and it will appear here.
                  </p>
                  <p style="width: 100%">
                    To alert the user that you have started working on their issue, select a issue with the status<br />
                    'NEW' and update its status via the drop-down menu to 'IN PROGRESS'.
                  </p>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
      </div>
      <%--FOOTER--%>
    <footer>
      <span>T-issue Reporting System &copy; </span>
    </footer>

  </body>
</html>
