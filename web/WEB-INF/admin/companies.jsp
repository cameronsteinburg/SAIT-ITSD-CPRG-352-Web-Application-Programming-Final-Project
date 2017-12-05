<%@include file="/WEB-INF/jspf/header.jspf" %>

<title>Manage Companies</title>

</head>
<body>

    <h1>System Admin - Manage Companies</h1>

    <br>
    ${message} 
    <br>
    <br>

    <table border="1" width="400">

        <tr>
            <th>Company Name</th>
            <th>Company ID</th>
            <th>Edit</th>
        </tr>

        <c:forEach var="company" items="${companies}">

            <tr>
                <td>&nbsp;${company.companyName}</td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;${company.companyID}</td>
                <td align="center">
                    <form action="companies" method="GET">
                        <br>
                        <input type="submit" value="Edit">
                        <input type="hidden" name="action" value="view">
                        <input type="hidden" name="selectedCompanyID" value="${company.companyID}">
                    </form>
                </td>
            </tr>

        </c:forEach>
    </table>

    <br>
    <br>

    <c:if test="${selectedCompany == null}">

        <h3>Add Company</h3>

        <form action="companies" method="POST">

            Company Name: <input type="text" name="companyname"><br>
            Company ID: ${newID}<br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">
        </form>
    </c:if>
        
    <c:if test="${selectedCompany != null}">

        <h3>Edit User</h3>

        <form action="companies" method="POST">

            Company Name: <input type="text" maxlength="25" name="companyname" value="${selectedCompany.companyName}"><br>
            Company ID: ${selectedCompany.companyID}<br>
            <input type="hidden" value="${selectedCompany.companyID}" name="id">
            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Save">
        </form>
    </c:if>
</body>
</html>
