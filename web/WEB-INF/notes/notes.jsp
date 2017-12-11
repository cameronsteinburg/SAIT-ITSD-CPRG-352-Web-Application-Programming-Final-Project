<%@include file="/WEB-INF/jspf/header.jspf" %>

<title>Manage Users</title>

</head>
<body>
    <h1>Manage Your Notes</h1>
    <br>
    ${message}
    <table border="1">


        <ul>
            <c:forEach var="note" items="${notess}">
                <tr><td>
                    <li>${note.title}<br><br><ul><li>${note.dateCreated}</li><li>${note.contents}</li></ul></li>
                    <br>
                    Visibility:  <c:if test="${note.visibility == 0}">Private</c:if><c:if test="${note.visibility == 1}">Public</c:if>
                    <br>
                    <br>
                </td></tr>
            </c:forEach>
            <br>
        </ul>


    </table>
    <br>

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
            title: <input type="text" name="title" maxlength="30"><br>
            contents: <input type="text" name="contents" maxlength="20000"><br>
            public? <input type="checkbox" name="publicOrPrivate"><br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">
        </form>
    </c:if>
    <c:if test="${selectedNote != null}">
        <h3>Edit Note</h3>
        <form action="notes" method="POST">
            <input type="hidden" name="editor" value="${selectedNote.noteID}">
            title: <input type="text" name="title" maxlength="30" value="${selectedNote.title}"><br>
            date created: ${selectedNote.dateCreated}<br>
            contents: <input type="text" name="contents" maxlength="20000" value="${selectedNote.contents}"><br>
            public? <input type="checkbox" name="publicOrPrivate"<c:if test="${selectedNote.visibility == 1}">checked</c:if>><br>
            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Save">
        </form>
    </c:if>
    <c:if test="${publicNotes != null}">
        <br>
        <h2>Public Notes For Your Company</h2>
        <table border="1">

            <tr>
                <th>Owner</th>
                <th>Title</th>
                <th>Contents</th>
                <th>Date/Time Created</th>
            </tr>

            <c:forEach var="note" items="${publicNotes}">

                <tr>
                    <td width="100">${note.owner.username}</td>
                    <td width="300">${note.title}</td>
                    <td width="200">${note.contents}</td>
                    <td width="400">${note.dateCreated}</td>
                </tr>

            </c:forEach>
        </table>
    </c:if>
</body>
</html>
