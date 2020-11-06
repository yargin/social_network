<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <common:accountInfo/>
    <c:if test="${not empty writer}">
        <div>
            <a href="${context}/updateInfo?id=${id}"><fmt:message key="label.updateInfo"/></a><br>
            <a href="${context}/deleteAccount?id=${id}"><fmt:message key="label.deleteAccount"/></a>
        </div>
    </c:if>
    <c:if test="${not empty reader}">
        <div>
            <a href="#">Wall</a><br>
            <a href="#">Private message</a><br>
        </div>
        <c:forEach items="${posts}" var="post">
            <div class="post">
                <common:wallMessage/>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty reader}">
        <a href="#">Add To friends</a>
    </c:if>
</common:layout>
