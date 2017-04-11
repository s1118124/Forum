<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <p>Login failed.</p>
        </c:if>
        <h2>User Registry</h2>
        <form action="regstry" method="POST">
            <label for="username">Username:</label><br/>
            <input type="text" id="username" name="username" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password" name="password" /><br/><br/>
            <label for="password-confirm">Confirm password:</label><br/>
            <input type="password" id="password-confirm" name="password-confirm" /><br/><br/>
            
            <!--input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/-->
            <input type="submit" value="Registry"/>
        </form>
        <a href="<c:url value="/index" />">Return to index</a>
    </body>
</html>