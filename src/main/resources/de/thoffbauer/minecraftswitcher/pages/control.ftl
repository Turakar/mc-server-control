<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.thoffbauer.minecraftswitcher.pages.ControlView -->

<#include "base.ftl">

<html>
<head>
    <@headers/>
    <meta charset="utf-8"/>
    <title>Server Status</title>
</head>
<body>
    <@error_banner/>
    <#if user??><form action="/" method="post"></#if>
        <table>
            <th>
                Server
            </th>
            <th>
                Status
            </th>
            <#if user??>
                <th>
                    Actions
                </th>
            </#if>
            <#assign runningServers = minecraftController.runningServers>
            <#list minecraftController.serverNames as server>
                <#assign running=runningServers?seq_contains(server)>
                <tr>
                    <td>
                        ${server}
                    </td>
                    <td>
                        ${running?string("Running", "Stopped")}
                    </td>
                    <#if user??>
                        <td>
                            <#if user.servers?seq_contains(server) || user.admin>
                                <#if running>
                                    <button name="button" value="${server}.stop" type="submit">Stop</button>
                                <#else>
                                    <button name="button" value="${server}.start" type="submit">Start</button>
                                </#if>
                            <#else>
                                No Access
                            </#if>
                        </td>
                    </#if>
                </tr>
            </#list>
        </table>
    <#if user??></form></#if>
</body>
</html>