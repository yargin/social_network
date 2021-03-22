<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.id}"/>


<%--if not a member don't show buttons--%>
<c:choose>
    <c:when test="${tab eq 'wall'}">
        <strong>
            <fmt:message key="label.wall"/>
        </strong>
    </c:when>
    <c:otherwise>
        <a href="${context}/group/wall?id=${id}">
            <button type="button"><fmt:message key="button.wall"/></button>
        </a>
    </c:otherwise>
</c:choose>

<%--move request status to wall tag--%>
<c:if test="${not empty admin or not empty owner or not empty moderator}">
    <c:choose>
        <c:when test="${tab eq 'requests'}">
            <strong>
                <fmt:message key="label.requests"/>
            </strong>
        </c:when>
        <c:otherwise>
            <a href="${context}/group/requests?id=${id}">
                <button type="button"><fmt:message key="label.requests"/></button>
            </a>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${not empty admin or not empty owner or not empty moderator or not empty member}">
    <c:choose>
        <c:when test="${tab eq 'members'}">
            <strong>
                <fmt:message key="label.members"/>
            </strong>
        </c:when>
        <c:otherwise>
            <a href="${context}/group/members?id=${id}">
                <button type="button"><fmt:message key="label.members"/></button>
            </a>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${not empty admin or not empty owner or not empty moderator}">
    <c:choose>
        <c:when test="${tab eq 'moderators'}">
            <strong>
                <fmt:message key="label.moderators"/>
            </strong>
        </c:when>
        <c:otherwise>
            <a href="${context}/group/moderators?id=${id}">
                <button type="button"><fmt:message key="label.moderators"/></button>
            </a>
        </c:otherwise>
    </c:choose>
</c:if>
<br>
<br>
