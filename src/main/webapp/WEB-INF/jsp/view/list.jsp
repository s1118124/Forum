<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Tickets</h2>
        <security:authorize access="hasRole('ADMIN')">    
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
        </security:authorize>
        <a href="<c:url value="/post/create" />">Create a Ticket</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(ticketDatabase) == 0}">
                <i>There are no tickets in the system.</i>
            </c:when>
            <c:otherwise>
                    <table>
                        <tr><th>Ticket</th> <th>Subject</th><th>User</th><th>Catagory</th><th></th><th></th></tr>
                        <c:forEach items="${ticketDatabase}" var="ticket">
                        <tr>
                            <td>${ticket.id}</td>
                            <td><a href="<c:url value="/ticket/view/${ticket.id}" />">
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
    </body>
</html>