<%-- 
    Document   : reset
    Created on : Nov 22, 2017, 1:34:15 PM
    Author     : 734972
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <a href="login">Login Page</a>
        <title>Reset Password</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <br>
        <form action="reset" method="post">
            Email Address: <input type="text" name="email">
            <br>
            <br>
            <input type="submit" value="Submit">
        </form>
        <br>
       ${message}
    </body>
</html>
