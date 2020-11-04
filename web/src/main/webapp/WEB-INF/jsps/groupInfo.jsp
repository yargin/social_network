<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <div class="info">
        <fmt:message key="form.groupName"/> : ${group.getName()}<br>
        <fmt:message key="form.description"/> : ${group.getDescription()}<br>
        <fmt:message key="label.groupCreationDate"/> : ${group.getCreationDate()}<br>
        <fmt:message key="label.creator"/> :
        <a href="${context}/mywall?userId=${owner.getId()}">${owner.getName()} ${owner.getSurname()}</a>
    </div>

    <div class="info">
        <c:choose>
            <c:when test="${not empty group.getHtmlPhoto()}">
                <img src="data:image/jpeg;base64, ${group.getHtmlPhoto()}">
            </c:when>
            <c:otherwise>
                <img src="${context}/img/emptyAvatar.jpg">
            </c:otherwise>
        </c:choose>
    </div>
    <br>
    <c:if test="${not empty moderator}">
        <a href="${context}/updategroup?groupId=${group.getId()}"><fmt:message key="label.updateInfo"/></a><br>
        <a href="${context}/deletegroup?groupId=${group.getId()}"><fmt:message key="label.deleteGroup"/></a>
    </c:if>
</common:layout>
