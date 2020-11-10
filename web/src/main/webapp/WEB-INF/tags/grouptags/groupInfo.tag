<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="info">
    <fmt:message key="form.groupName"/> : ${group.getName()}<br>
    <fmt:message key="form.description"/> : ${group.getDescription()}<br>
    <fmt:message key="label.groupCreationDate"/> : ${group.getCreationDate()}<br>
    <fmt:message key="label.creator"/> :
    <a href="${context}/mywall?id=${owner.getId()}">${owner.getName()} ${owner.getSurname()}</a>
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
<c:if test="${not empty moderator or not empty admin or not empty owner}">
    <a href="${context}/updategroup?id=${group.getId()}"><fmt:message key="label.updateInfo"/></a><br>
</c:if>
<c:if test="${not empty admin or not empty owner}">
    <a href="${context}/deletegroup?id=${group.getId()}"><fmt:message key="label.deleteGroup"/></a>
</c:if>
