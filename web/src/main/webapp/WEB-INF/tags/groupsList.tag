<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<c:forEach items="${groups}" var="group">
    <c:if test="${empty allgroups}">
        <a href="${context}/group?groupId=${group.id}">${group.name}</a>
        <c:if test="${not empty owner or not empty admin}">
            <form action="${context}/leavegroup" method="post">
                <input type="hidden" name="requesterId" value="${id}">
                <input type="hidden" name="receiverId" value="${group.id}">
                <button type="submit"><fmt:message key="button.leaveGroup"/></button>
            </form>
        </c:if>
        <br>
    </c:if>
    <c:if test="${not empty allgroups}">
        <a href="${context}/group?groupId=${group.id}">${group.name}</a>
        <c:if test="${not empty owner or not empty admin}">
            <form action="${context}/joingroup" method="post">
                <input type="hidden" name="requesterId" value="${id}">
                <input type="hidden" name="receiverId" value="${group.id}">
                <button type="submit"><fmt:message key="button.joinGroup"/></button>
            </form>
        </c:if>
        <br>
    </c:if>
</c:forEach>