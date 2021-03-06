<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${group.id}"/>

<c:forEach var="requesterVar" items="${requesters}">
    <a href="${context}/account/wall?id=${requesterVar.id}">
            ${requesterVar.name} ${requesterVar.surname}
    </a>
    <form action="${context}/group/accept" method="post">
        <input type="hidden" value="${requesterVar.id}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <button type="submit" name="accept" value="true"><fmt:message key="button.accept"/></button>
        <button type="submit" name="accept" value="false"><fmt:message key="button.decline"/></button>
    </form>
    <br>
</c:forEach>