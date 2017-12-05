<%@include file="/WEB-INF/jspf/header.jspf" %>
<title>Manage Account</title>

</head>
<body>
    <h1>Manage Your Account</h1>
    <br>
    ${themessage}
    <br>
    <br>
    <table border="1">
        <tr>
            <td>Username</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Email</td>
            <td>Role</td>
            <td>Company</td>
            <td>Notes</td>
        </tr>
        <tr>
            <td>${selectedUser.username}</td>
            <td>${selectedUser.firstname}</td>
            <td>${selectedUser.lastname}</td>
            <td>${selectedUser.email}</td>
            <td>${selectedUser.role.roleName}</td>
            <td>${selectedUser.company.companyName}</td>
            <td>
                <ul>
                    <c:forEach var="note" items="${selectedUser.noteCollection}">
                        <li>${note.title}</li>
                        </c:forEach>
                </ul>
            </td>
        </tr>
    </table>

    <h3>Edit Account</h3>

    <form action="account" method="POST">
        username: ${selectedUser.username}<br>
        first name: <input type="text" name="firstname" value="${selectedUser.firstname}" maxlength="50"><br>
        last name: <input type="text" name="lastname" value="${selectedUser.lastname}" maxlength="50"><br>
        password: <input type="text" name="password" value="${selectedUser.password}" maxlength="30"><br>
        email: <input type="email" name="email" value="${selectedUser.email}" maxlength="30"><br>
        company: ${selectedUser.company.companyName}<br>
        active: <input type="checkbox" name="active" ${selectedUser.active ? "checked" : ""}><br>
        <input type="hidden" name="action" value="edit">
        <input type="submit" value="Save">
    </form>
    <br>

</body>
</html>
