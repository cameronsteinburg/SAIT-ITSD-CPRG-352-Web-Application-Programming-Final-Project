<%@include file="/WEB-INF/jspf/header.jspf" %>

<title>Manage Users</title>

</head>
<body>

    <h1>System Admin - Manage Users</h1>

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
            <th>Active?</th>
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
                <td>${user.active}</td>
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

            username: <input type="text" name="username" maxlength="30"><br>
            first name: <input type="text" name="firstname" maxlength="50"><br>
            last name: <input type="text" name="lastname" maxlength="50"><br>
            password: <input type="password" name="password" maxlength="30"><br>
            email: <input type="email" name="email" maxlength="30"><br>
            company: <select name="selectCompany">
                <c:forEach var="comps" items="${comps}">
                    <option value="${comps.companyID}">${comps.companyName}</option>
                </c:forEach>
            </select><br>
            role: <select name="selectRole">
                <c:forEach var="roles" items="${roles}">
                    <option value="${roles.roleID}">${roles.roleName}</option>
                </c:forEach>
            </select><br>
            active?: <input type="checkbox" name="active"><br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">

        </form>
    </c:if>

    <c:if test="${selectedUser != null}">

        <h3>Edit User</h3>

        <form action="admin" method="POST">

            username: ${selectedUser.username} <input type="hidden" maxlength="30" name="username" value="${selectedUser.username}" readonly><br>
            first name: <input type="text" maxlength="50" name="firstname" value="${selectedUser.firstname}"><br>
            last name: <input type="text" maxlength="50" name="lastname" value="${selectedUser.lastname}"><br>
            password: <input type="password" maxlength="30" name="password" maxlength="30" value="${selectedUser.password}"><br>
            email: <input type="email" name="email" maxlength="30" value="${selectedUser.email}"><br>
            company: <select name="selectCompany">
                <c:forEach var="comps" items="${comps}">
                    <option value="${comps.companyID}"${comps.companyID == selectedUser.company.companyID ? 'selected' : ''}>${comps.companyName}</option>
                </c:forEach>
            </select><br>
            role: <select name="selectRole">
                <c:forEach var="roles" items="${roles}">
                    <option value="${roles.roleID}"${roles.roleID == selectedUser.role.roleID ? 'selected' : ''}>${roles.roleName}</option>
                </c:forEach>
            </select><br>
            active?: <input type="checkbox" name="active" ${selectedUser.active ? "checked" : ""}><br>
            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Save">

        </form>
    </c:if>
    <c:if test="${publicNotes != null}">

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

    <c:forEach var="note" items="${publicNotes}">


    </c:forEach>

</c:if>
</body>
</html>
