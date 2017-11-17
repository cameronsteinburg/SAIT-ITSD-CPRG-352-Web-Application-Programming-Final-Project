<%@include file="/WEB-INF/jspf/header.jspf" %>



<title>Manage Users</title>

</head>
    <body>
        <h1>Manage Users</h1>
        <h2>Users</h2>

        <table border="1">
            <tr>
                <th>Username</th>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>E-mail</th>
                <th>Role</th>
                <th>Note</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.firstname}</td>
                    <td>${user.lastname}</td>
                    <td>${user.email}</td>
                    <td>${user.role.roleName}</td>
                    <td>
                        <ul>
                            <c:forEach var="note" items="${user.noteList}">
                                <li>${note.title}</li>
                                </c:forEach>
                        </ul>
                    </td>
                    <td>
                        <form action="admin" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedUsername" value="${user.username}">
                        </form>
                    </td>
                    <td>
                        <form action="admin" method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="view">
                            <input type="hidden" name="selectedUsername" value="${user.username}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${selectedUser == null}">
            <h3>Add User</h3>
            <form action="admin" method="POST">
                username: <input type="text" name="username"><br>
                first name: <input type="text" name="firstname"><br>
                last name: <input type="text" name="lastname"><br>
                password: <input type="password" name="password"><br>
                email: <input type="email" name="email"><br>
                active: <input type="checkbox" name="active"><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>
        <c:if test="${selectedUser != null}">
            <h3>Edit User</h3>
            <form action="admin" method="POST">
                username: <input type="text" name="username" value="${selectedUser.username}" readonly><br>
                first name: <input type="text" name="firstname" value="${selectedUser.firstname}"><br>
                last name: <input type="text" name="lastname" value="${selectedUser.lastname}"><br>
                password: <input type="password" name="password" value="${selectedUser.password}"><br>
                email: <input type="email" name="email" value="${selectedUser.email}"><br>
                active: <input type="checkbox" name="active" ${selectedUser.active ? "checked" : ""}><br>
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Save">
            </form>
        </c:if>
        <br>
        ${message}
    </body>
</html>
