<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <textContent>
        <security:authorize access="isAnonymous()">
            <c:url var="loginUrl" value="/login"/>
            <form action="${loginUrl}" method="get">
                <input type="submit" value="Log in" />
            </form>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <h2>Topic #${topicId}: <c:out value="${topic.title}" /></h2>
        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/lab/edit/${topicId}" />">Edit</a>]
            [<a href="<c:url value="/lab/delete/${topicId}" />">Delete</a>]
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            [<a href="<c:url value="/lab/reply/${topicId}" />">Reply</a>]
        </security:authorize>
        <br /><br />
        <i>User Name - <c:out value="${topic.userID}" /></i><br /><br />
        <c:out value="${topic.textContent}" /><br /><br />
        <c:if test="${topic.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${topic.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/lab/${topicId}/attachment/${attachment.name}" />
                    </security:authorize>
                    <security:authorize access="isAnonymous()">
                        <c:url value="/login" />
                    </security:authorize>
                "><c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/lab" />">Return to list topics of lab</a>
        <br />
        <c:choose>
            <c:when test="${fn:length(replyDatabase) == 0}">
                <i>There are no replies in this topic.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${replyDatabase}" var="entry">
                    ...
                    <br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </textContent>
</html>