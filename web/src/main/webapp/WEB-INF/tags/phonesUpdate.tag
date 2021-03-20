<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<fmt:setBundle basename="form" var="form"/>

<fmt:message key="button.delete" bundle="${label}" var="deleteText"/>
<fmt:message key="error.notPhone" bundle="${error}" var="notPhone"/>
<fmt:message key="error.tooLong" bundle="${error}" var="tooLong"/>
<fmt:message key="error.tooShort" bundle="${error}" var="tooShort"/>
<fmt:message key="error.duplicate" bundle="${error}" var="duplicate"/>
<script>
    init("${deleteText}", "${tooShort}", "${tooLong}", "${notPhone}", "${duplicate}");
</script>

<br><label><fmt:message key="form.privatePhones" bundle="${form}"/></label><br>
<div id="privatePhonesList">
    <c:forEach var="privatePhone" items="${privatePhones}">
        <c:if test="${not empty privatePhone.error}">
            <c:set var="privatePhoneError">
                <fmt:message key="${privatePhone.error}" bundle="${error}"/>
            </c:set>
        </c:if>
        <script> addPhone("${privatePhone.number}", "${privatePhoneError}", "private"); </script>
    </c:forEach>
    <div id="newPrivatePhoneDiv" class="newPhoneDiv">
        <input type="text" id="newPrivatePhone" onblur="checkPhone('newPrivatePhone');">
        <button onclick="addNewPhone('private')" type="button"><fmt:message key="button.add"
                                                                            bundle="${label}"/></button>
        <div id="newPrivatePhoneError"></div>
    </div>
    <form:errors path="privatePhones" element="div"/>
</div>


<br><label><fmt:message key="form.workPhones" bundle="${form}"/></label><br>
<div id="workPhonesList">
    <c:forEach var="workPhone" items="${workPhones}">
        <c:if test="${not empty workPhone.error}">
            <c:set var="workPhoneError">
                <fmt:message key="${workPhone.error}" bundle="${error}"/>
            </c:set>
        </c:if>
        <script> addPhone("${workPhone.number}", "${workPhoneError}", "work"); </script>
    </c:forEach>
    <div id="newWorkPhoneDiv" class="newPhoneDiv">
        <input type="text" id="newWorkPhone" onblur="checkPhone('newWorkPhone');">
        <button onclick="addNewPhone('work')" type="button"><fmt:message key="button.add" bundle="${label}"/></button>
        <div id="newWorkPhoneError"></div>
    </div>
    <form:errors path="workPhones" element="div"/>
</div>
<br>