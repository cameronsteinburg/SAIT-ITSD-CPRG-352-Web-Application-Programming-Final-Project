<%@include file="/WEB-INF/jspf/header.jspf" %>
<title>Manage Account</title>

</head>
<body>
    <h1>Manage Account</h1>


    <table border="1">
        <tr>
            <td>Username</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Email</td>
            <td>Role</td>
            <td>Notes</td>
        </tr>
        <tr>
            <td>${selectedUser.username}</td>
            <td>${selectedUser.firstname}</td>
            <td>${selectedUser.lastname}</td>
            <td>${selectedUser.email}</td>
            <td>${selectedUser.role.roleName}</td>
            <td>
                <ul>
                    <c:forEach var="note" items="${selectedUser.noteList}">
                        <li>${note.title}</li>
                        </c:forEach>
                </ul>
            </td>
        </tr>
    </table>
            
    <h3>Edit Account</h3>
    <form action="account" method="POST">
        username: <input type="text" name="username" value="${selectedUser.username}" readonly><br>
        first name: <input type="text" name="firstname" value="${selectedUser.firstname}"><br>
        last name: <input type="text" name="lastname" value="${selectedUser.lastname}"><br>
        password: <input type="password" name="password" value="${selectedUser.password}"><br>
        email: <input type="email" name="email" value="${selectedUser.email}"><br>
        active: <input type="checkbox" name="active" ${selectedUser.active ? "checked" : ""}><br>
        <input type="hidden" name="action" value="edit">
        <input type="submit" value="Save">
    </form>
    <br>
    ${themessage}

</body>
</html>
