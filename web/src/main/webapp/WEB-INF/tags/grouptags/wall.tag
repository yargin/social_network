<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>

<div class="post">
    <c:if test="${empty member and empty owner and empty requester}">
        <form action="${context}/joingroup" method="post">
            <input type="hidden" value="${sessionScope.userId}" name="requesterId">
            <input type="hidden" value="${id}" name="receiverId">
            <button type="submit"><fmt:message key="button.joinGroup"/></button>
        </form>
        <br><br>
    </c:if>
    <c:if test="${not empty requester}">
        <label><fmt:message key="label.requestSent"/></label><br><br>
    </c:if>
    <c:if test="${not empty owner or not empty admin or not empty member or not empty moderator}">
        <%--        show messages--%>
        TODO!
    </c:if>
</div>