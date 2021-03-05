<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.getId()}"/>

<c:forEach var="moderatorVar" items="${moderators}">
    <a href="${context}/account/wall?id=${moderatorVar.getId()}">
            ${moderatorVar.getName()} ${moderatorVar.getSurname()}
    </a>
    <form action="${context}/group/moderators/remove" method="post">
        <input type="hidden" value="${moderatorVar.getId()}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit"><fmt:message key="button.delete"/></button>
    </form>
    <br>
</c:forEach>