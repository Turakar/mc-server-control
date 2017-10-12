<#macro headers>
    <link href="/static/style.css" rel="stylesheet" type="text/css">
</#macro>

<#macro error_banner>
    <#if error??>
        <h5>${error}</h5>
    </#if>
</#macro>