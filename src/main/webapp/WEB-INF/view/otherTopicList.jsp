<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <security:authorize access="isAnonymous()">
            <c:url var="loginUrl" value="/login"/>
            <form action="${loginUrl}" method="get">
                <input type="submit" value="Log in" />
            </form>
        </security:authorize>
        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <h2>A set of topics</h2>
        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <a href="<c:url value="/other/create" />">Create a Topic</a><br /><br />
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(topicDatabase) == 0}">
                <i>There are no topics in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${topicDatabase}" var="entry">
                    Topic ${entry.key}:
                    <a href="<c:url value="/other/view/${entry.key}" />">
                        <c:out value="${entry.value.title}" /></a>
                    (user: <c:out value="${entry.value.userID}" />)
                    <security:authorize access="hasRole('ADMIN')">  
                        [<a href="<c:url value="/other/edit/${entry.key}" />">Edit</a>]
                        [<a href="<c:url value="/other/delete/${entry.key}" />">Delete</a>]
                    </security:authorize>
                    <br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br />
        <a href="<c:url value="/index" />">Return to the index</a>
    </body>
</html>