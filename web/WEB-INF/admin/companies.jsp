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
                        <input type="hidden" name="selectedCompany" value="${company.companyID}">
                    </form>
                </td>
            </tr>
            
        </c:forEach>
        
</body>
</html>
