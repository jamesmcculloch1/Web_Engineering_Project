<%--
Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
Student No: 3291441, 3276255, 3088810 , 3189731
Date: 08/06/2019
Course: SENG2050
Assignment: Assignment 3

Description: LoginPage jsp for logging into the reporting system as a user of staff member
--%>


  <%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html lang="en-au">
      <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>LoginPage</title>
        <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet">
        <script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
      </head>
        <body>
        <div class="page-container">
          <div class="logo">
            <span class="logo">T-issue</span>
          </div>
            <%--LOGIN FORM --%>
            <div class="content-div">
              <div class="login-container">
                <h1 id="Login">Login</h1>
                <form class="login" name="j_username" action="j_security_check" method="POST">
                  <h3>Enter Details</h3><input type="text" class="textIn" id="username" placeholder="username..." name="j_username">
                  <%--ENTER USERNAME--%>
                  <br/>
                  <%--ENTER PASSWORD--%>
                  <input type="password" class="textIn" id="password" placeholder="password..." name="j_password"/><br/>
                  <input type="submit" class="button" name="submit" value="submit"/>
                  <input type="reset" class="button" name="reset" value="reset"/>
                </form>
              </div>
            </div>
          </div>

          <footer>
            <span>T-issue Reporting System &copy;</span>
          </footer>

        </body>
      </html>
