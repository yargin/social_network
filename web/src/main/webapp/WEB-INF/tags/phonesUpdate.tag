<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<fmt:message key="button.delete" bundle="${label}" var="deleteText"/>

<script>
    init("${deleteText}");
</script>

<br><label><fmt:message key="form.privatePhones" bundle="${label}"/></label><br>
<div id="privatePhonesList">
    <c:forEach var="privatePhone" items="${sessionScope.privatePhones}">
        <c:if test="${not empty privatePhone.error}">
            <c:set var="privatePhoneError">
                <fmt:message key="${privatePhone.error}" bundle="${error}"/>
            </c:set>
        </c:if>
        <script> addPrivatePhone("${privatePhone.value}", "${privatePhoneError}"); </script>
    </c:forEach>
    <div id="newPrivatePhoneDiv">
        <input type="text" id="newPrivatePhone" onblur="checkPhone('newPrivatePhone');">
        <button onclick="addNewPrivatePhone()" type="button">add</button>
        <div id="newPrivatePhoneError"></div>
    </div>
    <c:if test="${not empty phoneDuplicate}"><fmt:message key="${phoneDuplicate}" bundle="${error}"/><br></c:if>
</div>


<br><label><fmt:message key="form.workPhones" bundle="${label}"/></label><br>
<c:forEach var="workPhone" items="${sessionScope.workPhones}">
    <input type="text" name="${workPhone.paramName}" value="${workPhone.value}">
    <br>
    <c:if test="${not empty workPhone.error}"><fmt:message key="${workPhone.error}" bundle="${error}"/><br></c:if>
</c:forEach>
<c:if test="${not empty phoneDuplicate}"><fmt:message key="${phoneDuplicate}" bundle="${error}"/><br></c:if>
<button type="button">add</button>
<br>