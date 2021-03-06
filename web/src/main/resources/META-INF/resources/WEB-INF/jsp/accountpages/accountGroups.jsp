<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>


<common:layout>
    <account:accountInfo/>
    <account:accountMenu/>

    <c:if test="${not empty friend or not empty owner or not empty admin}">
        <a href="${context}/group/create">
            <button><fmt:message key="label.createGroup"/></button>
        </a><br>
        <div class="post">
            <c:choose>
                <c:when test="${empty allgroups}">
                    <strong><fmt:message key="label.joinedGroups"/></strong>
                    <a href="${context}/account/groups?id=${id}&allgroups=true"><fmt:message key="label.allGroups"/></a>
                    <br>
                </c:when>
                <c:otherwise>
                    <a href="${context}/account/groups?id=${id}"><fmt:message key="label.joinedGroups"/></a>
                    <strong><fmt:message key="label.allGroups"/></strong>
                    <br>
                </c:otherwise>
            </c:choose>
        </div>
        <account:groupsList/>
    </c:if>

</common:layout>