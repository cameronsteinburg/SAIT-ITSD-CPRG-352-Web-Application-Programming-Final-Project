<%@include file="/WEB-INF/jspf/header.jspf" %>

<title>Manage Users</title>
</head>
<body>
    <h1>Company Admin - Manage Users</h1>
    <br>
    <br>
    ${message}
    <br>
    <br>
    <table border="1">
        <tr>
            <th>Username</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>E-mail</th>
            <th>Role</th>
            <th>Note</th>
            <th>Company</th>
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
                        <c:forEach var="note" items="${user.noteCollection}">
                            <li>${note.title}</li>
                            </c:forEach>
                    </ul>
                </td>
                <td>${user.company.companyName}</td>
                <td>
                    <form action="companyAdmin" method="post" >
                        <input type="submit" value="Delete">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="selectedUsername" value="${user.username}">
                    </form>
                </td>
                <td>
                    <form action="companyAdmin" method="get">
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
        <form action="companyAdmin" method="POST">
            username: <input type="text" name="username" maxlength="30"><br>
            first name: <input type="text" name="firstname" maxlength="50"><br>
            last name: <input type="text" name="lastname" maxlength="50"><br>
            password: <input type="password" name="password" maxlength="30"><br>
            email: <input type="email" name="email" maxlength="30"><br>
            company: ${thisCompany}<br>
            active?: <input type="checkbox" name="active"><br>
            role: <select name="selectRole">
                <c:forEach var="roles" items="${roles}">
                    <option value="${roles.roleID}">${roles.roleName}</option>
                </c:forEach>
            </select><br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">
        </form>
    </c:if>
    <c:if test="${selectedUser != null}">
        <h3>Edit User</h3>
        <form action="companyAdmin" method="POST">
            username: <input type="text" name="username" value="${selectedUser.username}" readonly><br>
            first name: <input type="text" name="firstname" value="${selectedUser.firstname}" maxlength="50"><br>
            last name: <input type="text" name="lastname" value="${selectedUser.lastname}" maxlength="50"><br>
            password: <input type="password" name="password" value="${selectedUser.password}" maxlength="30"><br>
            email: <input type="email" name="email" value="${selectedUser.email}" maxlength="30"><br>
            company: ${thisCompany}<br>
            active?: <input type="checkbox" name="active" ${selectedUser.active ? "checked" : ""}><br>

            <c:choose>

                <c:when test="${selectedUser.role.roleID=='1'}">
                    role: ${thisRole}<br>
                </c:when>

                <c:otherwise>
                    role: <select name="selectRole" >
                        <c:forEach var="roles" items="${roles}">
                            <option value="${roles.roleID}"${roles.roleID == selectedUser.role.roleID ? 'selected' : ''}>${roles.roleName}</option>
                        </c:forEach>
                    </select><br>

                </c:otherwise>
            </c:choose>

            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Save">
        </form>
    </c:if>

</body>
</html>
