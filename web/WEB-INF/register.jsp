<%-- 
    Document   : register
    Created on : Dec 2, 2017, 10:05:42 PM
    Author     : 734972
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>

    <body>
        <h2>Register a New Account</h2>
        <br>
        <br>
        <form action="register" method="POST">
            username: <input type="text" name="username" maxlength="30"><br>
            first name: <input type="text" name="firstname" maxlength="50"><br>
            last name: <input type="text" name="lastname" maxlength="50"><br>
            password: <input type="password" name="password" maxlength="30"><br>
            email: <input type="email" name="email" maxlength="30"><br>
            company:
            <select name="selectCompany">
                <c:forEach var="comps" items="${comps}">
                    <option value="${comps.companyID}">${comps.companyName}</option>
                </c:forEach>
            </select><br>
            <input type="hidden" name="action" value="register">
            <input type="submit" value="Save">
        </form>
    </body>
</html>
