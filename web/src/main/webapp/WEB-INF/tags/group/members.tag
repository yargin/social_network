<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.id}"/>

<c:forEach var="memberItem" items="${members}">
    <c:set var="memberVar" value="${memberItem.key}"/>
    <c:set var="isModerator" value="${memberItem.value}"/>
    <a href="${context}/account/wall?id=${memberVar.id}">
            ${memberVar.name} ${memberVar.surname}
    </a>
    <form action="${context}/group/leave" method="post">
        <input type="hidden" value="${memberVar.id}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.delete"/></button>
    </form>
    <c:if test="${not isModerator}">
        <c:if test="${not empty admin or not empty owner or not empty moderator}">
            <form action="${context}/group/moderators/add" method="post">
                <input type="hidden" value="${memberVar.id}" name="requesterId">
                <input type="hidden" value="${id}" name="receiverId">
                <button type="submit"><fmt:message key="button.makeModerator"/></button>
            </form>
        </c:if>
    </c:if>
    <br>
</c:forEach>