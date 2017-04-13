<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <div id="head_bar">
            <security:authorize access="isAnonymous()">
                <c:url var="loginUrl" value="/login"/>
                <form action="${loginUrl}" method="get">
                    <input type="submit" value="Login" />
                </form>
            </security:authorize>
            <security:authorize access="hasAnyRole('ADMIN','USER')">
                <c:url var="logoutUrl" value="/logout"/>
                <form action="${logoutUrl}" method="post">
                    <input type="submit" value="Logout" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </security:authorize>
        </div>

        <h2>Lab</h2>
        <security:authorize access="hasRole('ADMIN')">    
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
        </security:authorize>
        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <a href="<c:url value="/post/create?type=lab" />">Create a Topic</a><br /><br />
        </security:authorize>

        <c:choose>
            <c:when test="${fn:length(ticketDatabase) == 0}">
                <i>There are no posts in this board.</i>
            </c:when>
            <c:otherwise>
                <c:set var="count" value="0" scope="page" />
                <table>
                    <tr><th>Thread ID</th> <th>Subject</th><th>Post by</th><th></th><th></th></tr>
                            <c:forEach items="${ticketDatabase}" var="ticket">
                                <c:if test = "${ticket.type eq'lab'}">
                                    <c:set var="count" value="${count + 1}" scope="page"/>

                            <tr>
                                <td>${ticket.id}</td>
                                <td><a href="<c:url value="/post/view/${ticket.id}" />">
                                        <c:out value="${ticket.subject}" /></a></td>
                                <td><c:out value="${ticket.customerName}" /></td>
                                <td>
                                    <security:authorize access="hasAnyRole('ADMIN','USER')">
                                        <security:authorize access="hasRole('ADMIN') or principal.username=='${ticket.customerName}'">            

                                            <security:authorize access="hasRole('ADMIN')">            
                                                [<a href="<c:url value="/post/edit/${ticket.id}" />">Edit</a>]
                                            </security:authorize>
                                        </security:authorize>
                                    </security:authorize>
                                </td>
                                <td>
                                    <security:authorize access="hasRole('ADMIN')">            
                                        [<a href="<c:url value="/post/delete/${ticket.id}" />">Delete</a>]
                                    </security:authorize>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <p>Total post:<c:out value="${count}" /> </p>

            </c:otherwise>
        </c:choose>
        <br>
        <a href="<c:url value="/" />">Back</a>
    </body>
</html>