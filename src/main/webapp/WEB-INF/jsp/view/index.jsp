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
                <input type="submit" value="Log in" />
            </form>
            <c:url var="regUrl" value="/registry"/>
            <form action="${regUrl}" method="get">
                <input type="submit" value="Registry" />
            </form>

        </security:authorize>
        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
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


        <h1>Course Discussion Forum</h1>
        <p>There is polling area</p>
        <ul><h2>Catergories:</h2>
            <li><a href="<c:url value="/lecture" />">Lecture</a></li>
            <li><a href="<c:url value="/lab" />">Lab</a></li>
            <li><a href="<c:url value="/other" />">Other</a></li>
        </ul>
    </body>
</html>