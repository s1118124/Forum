<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <h2>Register as a user</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="forumUser">
            <form:label path="username">Username</form:label><br/>
            <form:input type="text" path="username" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" /><br/><br/>
            <form:label path="roles">Roles</form:label><br/>
            <form:checkbox path="roles" value="ROLE_USER" />confirm to register
            <br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>