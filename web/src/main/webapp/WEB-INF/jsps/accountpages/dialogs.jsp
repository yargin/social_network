<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <account:accountInfo/>
    <account:accountMenu/>

    <br>
    <c:forEach var="dialog" items="${dialogs}">
        <c:choose>
            <c:when test="${dialog.firstAccount eq user}">
                <c:set value="${dialog.secondAccount}" var="talker"/>
            </c:when>
            <c:otherwise>
                <c:set value="${dialog.firstAccount}" var="talker"/>
            </c:otherwise>
        </c:choose>

        <a href="${context}/dialog/show?id=${dialog.id}">
                ${talker.name} ${talker.surname}
        </a>
        <br><br>
    </c:forEach>
</common:layout>
