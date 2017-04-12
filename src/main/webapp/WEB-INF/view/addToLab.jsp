<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Create a Topic</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="topicForm">
            <form:label path="title">Title</form:label><br/>
            <form:input type="text" path="title" /><br/><br/>
            <form:label path="textContent">Text Content</form:label><br/>
            <form:textarea path="textContent" rows="5" cols="30" /><br/><br/>
            <b>Attachments</b><br/>
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form:form>
        <a href= "<c:url value="/lab" />">Return to list topics</a>
    </body>
</html>