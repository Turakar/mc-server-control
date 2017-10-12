<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.thoffbauer.minecraftswitcher.pages.users.UserCreateView -->

<#include "../base.ftl">

<html>
<head>
    <meta charset="utf-8"/>
    <title>User Creation</title>
</head>
<body>
    <form method="post" action="/user/${user.name}">
        Name: ${user.name}<br>
        Password: <input type="password" name="password" placeholder="leave blank for no change"><br>
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
        <button type="submit" name="button" value="update">Update</button>
    </form>
</body>
</html>