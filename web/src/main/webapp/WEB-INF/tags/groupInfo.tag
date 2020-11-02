<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="info">
    <fmt:message key="form.name"/> : ${group.getName()}<br>
    <fmt:message key="form.surname"/> : ${group.getDescription()}<br>
    <fmt:message key="form.patronymic"/> : ${user.getCreationTime()}<br>
</div>
<div class="info">
    <c:choose>
        <c:when test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
        </c:when>
        <c:otherwise>
            <img src="${context}/img/emptyGroup.jpg">
        </c:otherwise>
    </c:choose>
</div>
<br>
