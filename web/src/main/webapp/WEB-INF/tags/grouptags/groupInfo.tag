<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>
<c:set var="ownerObject" value="${group.getOwner()}"/>

<div class="info">
    <fmt:message key="form.groupName"/> : ${group.getName()}<br>
    <fmt:message key="form.description"/> : ${group.getDescription()}<br>
    <fmt:message key="label.groupCreationDate"/> : ${group.getCreationDate()}<br>
    <fmt:message key="label.creator"/> :
    <a href="${context}/wall?id=${ownerObject.getId()}">${ownerObject.getName()} ${ownerObject.getSurname()}</a>
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
    <a href="${context}/updategroup?id=${id}"><fmt:message key="label.updateInfo"/></a><br>
</c:if>
<c:if test="${not empty admin or not empty owner}">
    <a href="${context}/deletegroup?id=${id}"><fmt:message key="label.deleteGroup"/></a><br>
</c:if>
<c:if test="${not empty member and empty owner}">
    <form action="${context}/leavegroup" method="post">
        <input type="hidden" value="${sessionScope.userId}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.leaveGroup"/></button>
    </form>
</c:if>
