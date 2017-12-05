<%-- 
    Document   : login
    Created on : 10-Nov-2017, 1:28:11 PM
    Author     : awarsyle, 734972
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    
    <body>
        <h1>Welcome to NotesKeepr, Please Login</h1>
        
        <form action="login" method="post">
            username: <input type="text" name="username" maxlength="30"><br>
            password: <input type="password" name="password" maxlength="30"><br>
            
            <input type="submit" value="Login">
        </form>    
        <br>
        <a href="register">Don't have an account? Register here</a>
        <br>
        <br>
        ${message}
    </body>
</html>
