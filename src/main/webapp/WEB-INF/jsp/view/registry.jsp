<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Create a User</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="ticketUser">
            <form:label path="username">Username</form:label><br/>
            <form:input type="text" path="username" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" /><br/><br/>
            <form:label path="roles">Roles</form:label><br/>
            <input type="submit" value="Create User"/>
        </form:form>
    </body>
</html>