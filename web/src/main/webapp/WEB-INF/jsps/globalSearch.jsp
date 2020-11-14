<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>

    <c:choose>
        <c:when test="${empty found}">
            <fmt:message key="label.nothingFound"/>
        </c:when>
        <c:otherwise>
            <c:forEach var="foundItem" items="${found}">
                <c:choose>
                    <c:when test="${foundItem.type.getTypeName() eq 'account'}">
                        <a href="${context}/mywall?id=${foundItem.id}">
                            <fmt:message key="label.user"/>: ${foundItem.name}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${context}/group?id=${foundItem.id}">
                            <fmt:message key="label.group"/>: ${foundItem.name}</a>
                    </c:otherwise>
                </c:choose>
                <br>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <c:forEach items="${allPages}" var="pageNumber">
        <c:choose>
            <c:when test="${pageNumber != page}">
                <a href="${context}/search?searchString=${searchString}&page=${pageNumber}">${pageNumber}</a>
            </c:when>
            <c:otherwise>
                ${pageNumber}
            </c:otherwise>
        </c:choose>
    </c:forEach>

</common:layout>