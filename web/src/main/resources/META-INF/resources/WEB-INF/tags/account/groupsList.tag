<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<c:forEach items="${groups}" var="group">
    <c:if test="${empty allgroups}">
        <a href="${context}/group/wall?id=${group.id}">${group.name}</a>
        <c:if test="${not empty owner or not empty admin}">
            <form action="${context}/group/leave" method="post">
                <input type="hidden" name="requesterId" value="${id}">
                <input type="hidden" name="receiverId" value="${group.id}">
            </form>
        </c:if>
        <br>
    </c:if>
    <c:if test="${not empty allgroups}">
        <%--        hashmap--%>
        <c:set var="groupVar" value="${group.getKey()}"/>
        <a href="${context}/group/wall?id=${groupVar.id}">${groupVar.name}</a>
        <c:choose>
            <c:when test="${group.getValue()}">
                <br>
                <label><fmt:message key="label.requestSent"/></label>
            </c:when>
            <c:otherwise>
                <c:if test="${not empty owner or not empty admin}">
                    <form action="${context}/group/join?" method="post">
                        <input type="hidden" name="requesterId" value="${id}">
                        <input type="hidden" name="receiverId" value="${groupVar.id}">
                        <button type="submit"><fmt:message key="button.joinGroup"/></button>
                    </form>
                </c:if>
            </c:otherwise>
        </c:choose>
        <br>
        <br>
    </c:if>
</c:forEach>