<%--@elvariable id="user" type="com.getjavajob.training.yarginy.socialnetwork.common.models.Account"--%>
<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Sex" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="form" var="form"/>
<fmt:setBundle basename="label" var="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script src="${context}/js/phonesEdit.js" defer></script>
<fmt:message key="label.areYouSure" var="confirmText" bundle="${label}"/>

<div class="info">
    <fmt:message key="form.name" bundle="${form}"/> : ${user.name}<br>
    <fmt:message key="form.surname" bundle="${form}"/> : ${user.surname}<br>
    <fmt:message key="form.patronymic" bundle="${form}"/> : ${user.patronymic}<br>
    <fmt:message key="form.sex" bundle="${form}"/> :
    <c:choose>
        <c:when test="${Sex.MALE eq user.sex}"><fmt:message key="form.male" bundle="${form}"/></c:when>
        <c:when test="${Sex.FEMALE eq user.sex}"><fmt:message key="form.female" bundle="${form}"/></c:when>
    </c:choose>
    <br>
    <fmt:message key="form.email" bundle="${form}"/> : ${user.email}<br>
    <fmt:message key="form.registrationDate" bundle="${form}"/> : ${user.registrationDate}<br>
    <fmt:message key="form.birthdate" bundle="${form}"/> : ${user.birthDate}<br>
    <fmt:message key="form.privatePhones" bundle="${form}"/> :
    <c:forEach var="phone" items="${privatePhones}">
        <br>
        ${phone.number}
    </c:forEach>
    <br>
    <fmt:message key="form.workPhones" bundle="${form}"/> :
    <c:forEach var="phone" items="${workPhones}">
        <br>
        ${phone.number}
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
        <a href="${context}/account/update?id=${id}"><fmt:message key="label.updateInfo" bundle="${label}"/></a><br>
        <a href="${context}/account/delete?id=${id}" onclick='return confirmation("${confirmText}")'><fmt:message
                key="label.deleteAccount" bundle="${label}"/></a><br>
        <a href="${context}/account/savexml?id=${id}" download="account id ${id}.xml">
            <fmt:message key="label.saveAccountInfo" bundle="${label}"/> </a>
    </div>
</c:if>
