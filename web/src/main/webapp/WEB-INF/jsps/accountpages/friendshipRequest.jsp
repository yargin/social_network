<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<common:layout>
    <common:accountInfo/>
    <common:accountMenu/>

    <br><br>
    <c:if test="${not empty error}"><fmt:message key="${error}" bundle="${error}"/></c:if>
    <c:choose>
        <c:when test="${created eq true}">
            <fmt:message key="label.requestSent" bundle="${label}"/>
        </c:when>
        <c:otherwise>
            <fmt:message key="label.requestAlreadySent" bundle="${label}"/>
        </c:otherwise>
    </c:choose>
    <br><br>

</common:layout>
