<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="group" tagdir="/WEB-INF/tags/grouptags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>

    <group:groupInfo/>
    <group:groupMenu/>

    <c:choose>
        <c:when test="${tab eq 'wall'}">
            <group:groupWall/>
        </c:when>
        <c:when test="${tab eq 'requests'}">
            <group:requests/>
        </c:when>
        <c:when test="${tab eq 'members'}">
            <group:members/>
        </c:when>
        <c:when test="${tab eq 'moderators'}">
            <group:moderators/>
        </c:when>
        <c:otherwise>
            <group:groupWall/>
        </c:otherwise>
    </c:choose>

</common:layout>