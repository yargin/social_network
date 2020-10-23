<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <c:if test="${not empty user}">
    <common:myInfo/>
        <div>
            <c:if test="${not empty updateAble}">
                <a href="${context}/updateInfo"><fmt:message key="label.updateInfo"/></a><br>
                <a href="${context}/deleteAccount"><fmt:message key="label.deleteAccount"/></a>
            </c:if>
        </div>
    </c:if>
    <c:forEach items="${posts}" var="post">
        <div class="post">
            <common:wallMessage/>
        </div>
    </c:forEach>
</common:layout>
