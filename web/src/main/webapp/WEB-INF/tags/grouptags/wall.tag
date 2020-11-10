<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>

<div class="post">
    <c:if test="${empty member or empty moderator or empty owner}">
        <a href="${context}/joingroup"><fmt:message key="button.joinGroup"/></a><br><br>
    </c:if>
    <c:if test="${not empty requster}">
        <label><fmt:message key="label.requestSent"/></label><br><br>
    </c:if>
    <c:if test="${not empty owner or not empty admin or not empty member or not empty moderator}">
        <%--        show messages--%>
        TODO!
    </c:if>
</div>