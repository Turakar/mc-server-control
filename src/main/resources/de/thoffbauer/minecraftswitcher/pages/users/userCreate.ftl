<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.thoffbauer.minecraftswitcher.pages.users.UserCreateView -->

<#include "../base.ftl">

<html>
<head>
    <@headers/>
    <meta charset="utf-8"/>
    <title>User Creation</title>
</head>
<body>
    <@error_banner/>
    <form method="post" action="/users/create">
        Name: <input type="text" name="name" maxlength="30" value="${user.name}"><br>
        Password: <input type="password" name="password"><br>
        Admin: <input type="checkbox" name="admin" ${user.admin?string('checked','')}><br>
        <br>
        <table>
            <tr>
                <th>Server</th>
                <th>Access</th>
            </tr>
            <#list minecraftController.serverNames as server>
                <tr>
                    <td>${server}</td>
                    <td>
                        <input type="checkbox" name="access.${server}" ${user.servers?seq_contains(server)?string('checked','')}>
                    </td>
                </tr>
            </#list>
        </table>
        <br>
        <button type="submit" name="button" value="create">Create</button>
    </form>
</body>
</html>