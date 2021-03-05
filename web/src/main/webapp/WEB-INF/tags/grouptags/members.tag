<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>

<c:forEach var="memberItem" items="${members}">
    <c:set var="memberVar" value="${memberItem.getKey()}"/>
    <c:set var="isModerator" value="${memberItem.getValue()}"/>
    <a href="${context}/account/wall?id=${memberVar.getId()}">
            ${memberVar.getName()} ${memberVar.getSurname()}
    </a>
    <form action="${context}/leavegroup" method="post">
        <input type="hidden" value="${memberVar.getId()}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.delete"/></button>
    </form>
    <c:if test="${not isModerator}">
        <c:if test="${not empty admin or not empty owner or not empty moderator}">
            <form action="${context}/group/moderators/add" method="post">
                <input type="hidden" value="${memberVar.getId()}" name="requesterId">
                <input type="hidden" value="${id}" name="receiverId">
                <button type="submit"><fmt:message key="button.makeModerator"/></button>
            </form>
        </c:if>
    </c:if>
    <br>
</c:forEach>