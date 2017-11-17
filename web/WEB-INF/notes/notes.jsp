<%@include file="/WEB-INF/jspf/header.jspf" %>

<title>Manage Users</title>

</head>
<body>

    <h1>Manage Notes</h1>
    
    <ul>
        <c:forEach var="note" items="${notess}">

            <li>${note.title}<br><ul><li>${note.dateCreated}</li><li>${note.contents}</li></ul></li>

        </c:forEach>
    </ul>


    <form action="notes" method="post" >
        <select name="deleteselect">
            <c:forEach var="note" items="${notess}">
                <option value="${note.noteID}">
                    ${note.title}
                </option>
            </c:forEach>
        </select>
        <input type="submit" value="Delete">
        <input type="hidden" name="action" value="delete">
    </form><br>
    <form action="notes" method="get">
        <select name="editselect">
            <c:forEach var="note" items="${notess}">
                <option value="${note.noteID}">
                    ${note.title}
                </option>
            </c:forEach>
        </select>
        <input type="submit" value="Edit">
        <input type="hidden" name="action" value="view">
    </form>
    <c:if test="${selectedNote == null}">
        <h3>Add Note</h3>
        <form action="notes" method="POST">
            title: <input type="text" name="title"><br>
            contents: <input type="text" name="contents"><br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">
        </form>
    </c:if>
    <c:if test="${selectedNote != null}">
        <h3>Edit Note</h3>
        <form action="notes" method="POST">
            <input type="hidden" name="editor" value="${selectedNote.noteID}">
            title: <input type="text" name="title" value="${selectedNote.title}"><br>
            date: <input type="text" name="date" value="${selectedNote.dateCreated}"><br>
            contents: <input type="text" name="contents" value="${selectedNote.contents}"><br>
            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Save">
        </form>
    </c:if>
        ${message}
</body>
</html>
