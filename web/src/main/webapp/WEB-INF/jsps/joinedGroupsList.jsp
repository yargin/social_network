<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <div class="post">
        <c:choose>
            <c:when test="${empty allgroups}">
                <strong><fmt:message key="label.joinedGroups"/></strong>
                <a href="${context}/groups?allgroups=true"><fmt:message key="label.allGroups"/></a>
            </c:when>
            <c:otherwise>
                <a href="${context}/groups"><fmt:message key="label.joinedGroups"/></a>
                <strong><fmt:message key="label.allGroups"/></strong>
            </c:otherwise>
        </c:choose>
    </div>
</common:layout>