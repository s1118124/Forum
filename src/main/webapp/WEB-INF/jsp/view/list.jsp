<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <div id="head_bar">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Logout" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <security:authorize access="hasRole('ADMIN')">    
                <c:url var="userPanel" value="/user"/>
                <form action="${userPanel}" method="post">
                    <input type="submit" value="User Control Panel" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </security:authorize>
        </div>

        <h2>Posts</h2>
        <c:choose>
            <c:when test="${fn:length(ticketDatabase) == 0}">
                <i>There are no post in the system.</i>
            </c:when>
            <c:otherwise>
                <table>
                    <tr><th>Ticket</th> <th>Subject</th><th>User</th><th>Catagory</th><th></th><th></th></tr>
                            <c:forEach items="${ticketDatabase}" var="ticket">
                        <tr>
                            <td>${ticket.id}</td>
                            <td><a href="<c:url value="/post/view/${ticket.id}" />">
                                    <c:out value="${ticket.subject}" /></a></td>
                            <td><c:out value="${ticket.customerName}" /></td>
                            <td><c:out value="${ticket.type}" /></td>
                            <td>
                                <security:authorize access="hasRole('ADMIN') or principal.username=='${ticket.customerName}'">            
                                    [<a href="<c:url value="/post/edit/${ticket.id}" />">Edit</a>]
                                </security:authorize>
                            </td>
                            <td>
                                <security:authorize access="hasRole('ADMIN')">            
                                    [<a href="<c:url value="/post/delete/${ticket.id}" />">Delete</a>]
                                </security:authorize>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </c:otherwise>
        </c:choose>
        <br><br>
        <a href="<c:url value="/" />">Return to Homepage</a>
    </body>
</html>