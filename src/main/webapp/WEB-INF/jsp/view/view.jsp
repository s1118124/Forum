<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <div id="head_bar">
            <security:authorize access="isAnonymous()">
                <c:url var="loginUrl" value="/login"/>
                <form action="${loginUrl}" method="get">
                    <input type="submit" value="Login" />
                </form>
                <c:url var="regUrl" value="/registry"/>
                <form action="${regUrl}" method="get">
                    <input type="submit" value="Registry" />
                </form>

            </security:authorize>
            <security:authorize access="hasAnyRole('ADMIN','USER')">
                <c:url var="logoutUrl" value="/logout"/>
                <form action="${logoutUrl}" method="post">
                    <input type="submit" value="Logout" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </security:authorize>

            <security:authorize access="hasRole('ADMIN')">            
                <c:url var="listUrl" value="/post/list"/>
                <form action="${listUrl}" method="post">
                    <input type="submit" value="Post List" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </security:authorize>

        </div>

        <h2>Thread #${ticket.id}: <c:out value="${ticket.subject}" /></h2>
        <!-- security:authorize access="hasRole('ADMIN') or principal.username=='${ticket.customerName}'" -->            
            <!--[<a href="<c:url value="/ticket/edit/${ticket.id}" />">Edit</a>] -->
        <!--/security:authorize -->
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="/post/delete/${ticket.id}" />">Delete</a>]
        </security:authorize>
        <table>
            <tr><th>Post by</th><th>Comment</th></tr>
            <tr><td rowspan = 2><c:out value="${ticket.customerName}" /></td><td rowspan = 2><c:out value="${ticket.body}" />
                    <security:authorize access="hasAnyRole('ADMIN','USER')">

                    <c:if test="${fn:length(ticket.attachments) > 0}">
                        <br /><br />
                        Attachments:<br />
                        <c:forEach items="${ticket.attachments}" var="attachment"
                                   varStatus="status">
                            <c:if test="${!status.first}">, </c:if>
                            <a href="<c:url value="/post/${ticket.id}/attachment/${attachment.name}" />">
                                <c:out value="${attachment.name}" /></a><br />
                        </c:forEach><br /><br />
                    </c:if>
            </security:authorize>
                </td></tr>
            <!--  Reserved Space for reply -->

            <table>

                <a href="<c:url value="/${ticket.type}" />">Return to board</a>
                </body>
                </html>