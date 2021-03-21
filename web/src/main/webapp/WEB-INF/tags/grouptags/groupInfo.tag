<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.id}"/>
<c:set var="ownerObject" value="${group.owner}"/>

<div class="info">
    <fmt:message key="form.groupName" bundle="${form}"/> : ${group.name}<br>
    <fmt:message key="form.description" bundle="${form}"/> : ${group.description}<br>
    <fmt:message key="label.groupCreationDate" bundle="${label}"/> : ${group.creationDate}<br>
    <fmt:message key="label.creator" bundle="${label}"/> :
    <a href="${context}/account/wall?id=${ownerObject.id}">${ownerObject.name} ${ownerObject.surname}</a>
</div>
<div class="info">
    <c:choose>
        <c:when test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
        </c:when>
        <c:otherwise>
            <img src="${context}/img/emptyAvatar.jpg">
        </c:otherwise>
    </c:choose>
</div>
<br>
<c:if test="${not empty moderator or not empty admin or not empty owner}">
    <a href="${context}/group/update?id=${id}"><fmt:message key="label.updateInfo" bundle="${label}"/></a><br>
</c:if>
<c:if test="${not empty admin or not empty owner}">
    <a href="${context}/group/delete?id=${id}"><fmt:message key="label.deleteGroup" bundle="${label}"/></a><br>
</c:if>
<c:if test="${not empty member and empty owner}">
    <form action="${context}/group/leave" method="post">
        <input type="hidden" value="${sessionScope.userId}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.leaveGroup" bundle="${label}"/></button>
    </form>
</c:if>
