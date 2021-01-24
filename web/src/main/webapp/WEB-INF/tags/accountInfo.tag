<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="form" var="form"/>
<fmt:setBundle basename="label" var="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script src="${context}/js/phonesEdit.js" defer></script>
<fmt:message key="label.areYouSure" var="confirmText" bundle="${label}"/>

<div class="info">
    <fmt:message key="form.name" bundle="${form}"/> : ${user.getName()}<br>
    <fmt:message key="form.surname" bundle="${form}"/> : ${user.getSurname()}<br>
    <fmt:message key="form.patronymic" bundle="${form}"/> : ${user.getPatronymic()}<br>
    <fmt:message key="form.sex" bundle="${form}"/> : <c:if test="${not empty user.getSex()}">
    ${user.getSex().toString().toLowerCase()}</c:if>
    <br>
    <fmt:message key="form.email" bundle="${form}"/> : ${user.getEmail()}<br>
    <fmt:message key="form.registrationDate" bundle="${form}"/> : ${user.getRegistrationDate()}<br>
    <fmt:message key="form.birthdate" bundle="${form}"/> : ${user.getBirthDate()}<br>
    <fmt:message key="form.privatePhones" bundle="${form}"/> :
    <c:forEach var="phone" items="${privatePhones}">
        <br>
        ${phone.getNumber()}
    </c:forEach>
    <br>
    <fmt:message key="form.workPhones" bundle="${form}"/> :
    <c:forEach var="phone" items="${workPhones}">
        <br>
        ${phone.getNumber()}
    </c:forEach>
</div>

<div class="info">
    <c:choose>
        <c:when test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
        </c:when>
        <c:otherwise>
            <img src="${context}/img/emptyAvatar.jpg">
        </c:otherwise>
    </c:choose>
</div>
<br>
<c:if test="${not empty owner or not empty admin}">
    <div>
        <a href="${context}/updateinfo?id=${id}"><fmt:message key="label.updateInfo" bundle="${label}"/></a><br>
        <a href="${context}/deleteAccount?id=${id}" onclick='return confirmation("${confirmText}")'><fmt:message
                key="label.deleteAccount" bundle="${label}"/></a>
    </div>
</c:if>
