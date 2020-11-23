<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <common:accountInfo/>
    <common:accountMenu/>

    <c:forEach var="dialog" items="${dialogs}">
        <c:choose>
            <c:when test="${dialog.getFirstAccount() eq user}">
                <c:set value="${dialog.getFirstAccount()}" var="receiver"/>
                <c:set value="${dialog.getSecondAccount()}" var="requester"/>
            </c:when>
            <c:otherwise>
                <c:set value="${dialog.getSecondAccount()}" var="receiver"/>
                <c:set value="${dialog.getFirstAccount()}" var="requester"/>
            </c:otherwise>
        </c:choose>

        <a href="${context}/dialog?requester=${requester.getId()}&receiver=${receiver.getId()}">
                ${requester.getName()} ${requester.getSurname()}
        </a>
    </c:forEach>
</common:layout>
