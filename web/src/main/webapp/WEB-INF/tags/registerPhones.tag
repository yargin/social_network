<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<c:forEach var="privatePhone" items="${sessionScope.privatePhones}">
    <input type="text"
           name="${privatePhone.paramName}" value="${privatePhone.value}"
           placeholder="<fmt:message key="form.privatePhone" bundle="${label}"/>">
    <br>
    <c:if test="${not empty privatePhone.error}"><fmt:message key="${privatePhone.error}" bundle="${error}"/><br></c:if>
</c:forEach>

<c:forEach var="workPhone" items="${sessionScope.workPhones}">
    <input type="text"
           name="${workPhone.paramName}" value="${workPhone.value}"
           placeholder="<fmt:message key="form.workPhone" bundle="${label}"/>">
    <br>
    <c:if test="${not empty workPhone.error}"><fmt:message key="${workPhone.error}" bundle="${error}"/><br></c:if>
</c:forEach>
<c:if test="${not empty phoneDuplicate}"><fmt:message key="${phoneDuplicate}" bundle="${error}"/><br></c:if>
<br>
