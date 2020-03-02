<%--
Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
Student No: 3291441, 3276255, 3088810 , 3189731
Date: 08/06/2019
Course: SENG2050
Assignment: Assignment 3

Description: Account. jsp displays the users account info such as name, email and contact number from the database.
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


        <h1>My Account</h1>
        <%--gets name, email and contact number stored in that users profile in the database--%>
        <div class="details">
            <span>Name : <c:out value="${myAccount.firstName}"></c:out> <c:out value="${myAccount.lastName}"></c:out></span><br />
            <span>Email : <c:out value="${myAccount.email}"></c:out></span><br />
            <span>Phone : <c:out value="${myAccount.contactNumber}"></c:out></span><br />
        </div>
      </div>
    </div>

    <%--FOOTER--%>
    <footer>
      <span>T-issue Reporting System &copy; </span>
    </footer>

  </body>

</html>
