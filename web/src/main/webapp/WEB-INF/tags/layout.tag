<%@ tag description="" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<fmt:setBundle basename="label"/>
<%--<fmt:setLocale value="ru"/>--%>
<!DOCTYPE html>
<html>
<head>
    <link href="${pageContext.servletContext.contextPath}/css/style.css"
          type = "text/css" rel="stylesheet">
    <meta charset="UTF-8">
    <title><fmt:message key="title"/></title>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <img src="${pageContext.servletContext.contextPath}/img/java.png" id="logo">
        <h1><fmt:message key="header"/></h1>
    </div>
</div>
<div class="wrapper">
    <div class="menu">
        <ul>
            <li><a href="#"><fmt:message key="menu.homepage"/></a></li>
            <li><a href="#"><fmt:message key="menu.friends"/></a></li>
            <li><a href="#"><fmt:message key="menu.messages"/></a></li>
            <li><a href="#"><fmt:message key="menu.groups"/></a></li>
            <li><a href="#"><fmt:message key="menu.photos"/></a></li>
            <li><a href="#"><fmt:message key="menu.settings"/></a></li>
            <li><a href="#"><fmt:message key="menu.quit"/></a></li>
        </ul>
    </div>
    <div class="content">
        <jsp:doBody/>
    </div>
</div>
<div class="wrapper">
    <div class="footer">
        <p><fmt:message key="footer"/> 2020</p>
    </div>
</div>
</body>
</html>