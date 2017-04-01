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
        <h2>Topic #${topicId}</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="topicForm">   
            <form:label path="title">Title</form:label><br/>
            <form:input type="text" path="title" /><br/><br/>
            <form:label path="textContent">Text Content</form:label><br/>
            <form:textarea path="textContent" rows="5" cols="30" /><br/><br/>
            <c:if test="${topic.numberOfAttachments > 0}">
                <b>Attachments:</b><br/>
                <ul>
                    <c:forEach items="${topic.attachments}" var="attachment">
                        <li>
                            <c:out value="${attachment.name}" />
                            [<a href="<c:url value="/other/${topicId}/delete/${attachment.name}" />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add attachments</b><br />
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Save"/><br/><br/>
        </form:form>
        <a href="<c:url value="/other/view/${topicId}" />">Return to the topic</a>
    </body>
</html>