<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<fmt:message key="button.delete" bundle="${label}" var="deleteText"/>
<fmt:message key="error.notPhone" bundle="${error}" var="notPhone"/>
<fmt:message key="error.tooLong" bundle="${error}" var="tooLong"/>
<fmt:message key="error.tooShort" bundle="${error}" var="tooShort"/>
<fmt:message key="error.duplicate" bundle="${error}" var="duplicate"/>

<script>
    init("${deleteText}", "${tooShort}", "${tooLong}", "${notPhone}", "${duplicate}");
</script>

<br><label><fmt:message key="form.privatePhones" bundle="${label}"/></label><br>
<div id="privatePhonesList">
    <c:forEach var="privatePhone" items="${sessionScope.privatePhones}">
        <c:if test="${not empty privatePhone.error}">
            <c:set var="privatePhoneError">
                <fmt:message key="${privatePhone.error}" bundle="${error}"/>
            </c:set>
        </c:if>
        <script> addPhone("${privatePhone.value}", "${privatePhoneError}", "private"); </script>
    </c:forEach>
    <div id="newPrivatePhoneDiv" class="newPhoneDiv">
        <input type="text" id="newPrivatePhone" onblur="checkPhone('newPrivatePhone');">
        <button onclick="addNewPhone('private')" type="button"><fmt:message key="button.add"
                                                                            bundle="${label}"/></button>
        <div id="newPrivatePhoneError"></div>
    </div>
    <c:if test="${not empty phoneDuplicate}"><fmt:message key="${phoneDuplicate}" bundle="${error}"/><br></c:if>
</div>


<br><label><fmt:message key="form.workPhones" bundle="${label}"/></label><br>
<div id="workPhonesList">
    <c:forEach var="workPhone" items="${sessionScope.workPhones}">
        <c:if test="${not empty workPhone.error}">
            <c:set var="workPhoneError">
                <fmt:message key="${workPhone.error}" bundle="${error}"/>
            </c:set>
        </c:if>
        <script> addPhone("${workPhone.value}", "${workPhoneError}", "work"); </script>
    </c:forEach>
    <div id="newWorkPhoneDiv" class="newPhoneDiv">
        <input type="text" id="newWorkPhone" onblur="checkPhone('newWorkPhone');">
        <button onclick="addNewPhone('work')" type="button"><fmt:message key="button.add" bundle="${label}"/></button>
        <div id="newWorkPhoneError"></div>
    </div>
    <c:if test="${not empty phoneDuplicate}"><fmt:message key="${phoneDuplicate}" bundle="${error}"/><br></c:if>
</div>
<br>