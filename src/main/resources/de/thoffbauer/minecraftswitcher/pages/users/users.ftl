<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.thoffbauer.minecraftswitcher.pages.users.UsersView -->

<html>
<head>
    <meta charset="utf-8"/>
    <title>Users</title>
</head>
<body>
    <ul>
        <#list users as user>
            <li>
                <a href="/user/${user.name}">${user.name}</a>
            </li>
        </#list>
    </ul>
    <form method="get" action="/users/create">
        <button type="submit">New User</button>
    </form>
</body>
</html>