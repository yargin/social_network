<%@ tag description="" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <link href="${context}/css/style.css" type="text/css" rel="stylesheet">
    <meta charset="UTF-8">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="${context}/webjars/jquery-ui/1.12.1/jquery-ui.theme.min.css">
    <link rel="stylesheet" href="${context}/webjars/jquery-ui/1.12.1/jquery-ui.structure.min.css">
</head>

<body>
<div class="wrapper">
    <div class="header">
        <img src="${context}/img/java.png" id="logo">
        <h1><fmt:message key="header"/></h1>
        <div style="min-width: 300px;">
            <c:set var="user" value="${sessionScope.user}"/>
            <c:if test="${not empty user}">
                <fmt:message key="layout.welcome"/>
                <a href="${context}/account/wall">${user.name}</a>
                <c:if test="${Role.ADMIN eq user.role}"><fmt:message key="label.isAdmin"/></c:if>
                <form action="${context}/search" id="search">
                    <input type="text" name="searchString" id="searchString"
                           placeholder="<fmt:message key="button.searchHolder"/>">
                    <button type="submit" id="searchButton"><fmt:message key="button.search"/></button>
                </form>
            </c:if>
        </div>
    </div>
</div>
<div class="wrapper">
    <div class="menu">
        <ul>
            <li><a href="${context}/account/wall"><fmt:message key="menu.homepage"/></a></li>
            <li><a href="${context}/account/friends"><fmt:message key="menu.friends"/></a></li>
            <li><a href="${context}/account/dialogs"><fmt:message key="menu.messages"/></a></li>
            <li><a href="${context}/account/groups"><fmt:message key="menu.groups"/></a></li>
            <li><a href="#"><fmt:message key="menu.settings"/></a></li>
            <li><a href="${context}/logout"><fmt:message key="menu.quit"/></a></li>
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