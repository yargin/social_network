<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>

<c:forEach var="memberVar" items="${members}">
    <a href="${context}/mywall?id=${memberVar.getId()}">
            ${memberVar.getName()} ${memberVar.getSurname()}
    </a>
    <form action="${context}/deletemember" method="post">
        <input type="hidden" value="${memberVar.getId()}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.delete"/></button>
    </form>
    <c:if test="${not empty admin or not empty owner or not empty moderator}">
        <form action="${context}/addmoderator" method="post">
            <input type="hidden" value="${memberVar.getId()}" name="requesterId">
            <input type="hidden" value="${id}" name="receiverId">
            <button type="submit"><fmt:message key="button.makeModerator"/></button>
        </form>
    </c:if>
    <br>
</c:forEach>