<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<fmt:setBundle basename="form" var="form"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>
<script type="text/javascript" src="${context}/js/confirmationDialog.js"></script>

<div class="info">
    <fmt:message key="label.saveUpdates" var="confirmText" bundle="${label}"/>

    <form action="${context}/account/update?id=${id}" method="post" enctype="multipart/form-data" id="accountUpdate"
          onsubmit='return confirmation("${confirmText}", acceptPhones())'>

        <label><fmt:message key="form.name" bundle="${form}"/></label>
        <input type="text" name="name" value="${account.name}" required>
        <br>

        <label><fmt:message key="form.surname" bundle="${form}"/></label>
        <input type="text" name="surname" value="${account.surname}" required>
        <br>

        <label><fmt:message key="form.patronymic" bundle="${form}"/></label>
        <input type="text" name="patronymic" value="${account.patronymic}">
        <br>

        <label>
            <fmt:message key="form.sex" bundle="${form}"/>
        </label>
        <select name="sex" required>
            <option value="${male}" <c:if test="${Sex.MALE eq account.sex}">selected</c:if>>
                <fmt:message key="form.male" bundle="${form}"/></option>
            <option value="${female}" <c:if test="${Sex.FEMALE eq account.sex}">selected</c:if>>
                <fmt:message key="form.female" bundle="${form}"/></option>
        </select>
        <br>

        <label><fmt:message key="form.additionalEmail" bundle="${form}"/></label>
        <input type="email" name="additionalEmail" value="${account.additionalEmail}">
        <br>
        <c:if test="${not empty emailDuplicate}"><fmt:message key="${emailDuplicate}" bundle="${error}"/><br></c:if>

        <label><fmt:message key="form.birthdate" bundle="${form}"/></label>
        <input type="date" name="birthDate" value="${account.birthDate}">
        <br>

        <label><fmt:message key="form.icq" bundle="${form}"/></label>
        <input type="text" name="icq" value="${account.icq}">
        <br>

        <label><fmt:message key="form.skype" bundle="${form}"/></label>
        <input type="text" name="skype" value="${account.skype}">
        <br>

        <label><fmt:message key="form.country" bundle="${form}"/></label>
        <input type="text" name="country" value="${account.country}" required>
        <br>

        <label><fmt:message key="form.city" bundle="${form}"/></label>
        <input type="text" name="city" value="${account.city}" required>
        <br>

        <common:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <fmt:message key="form.chooseAnother" bundle="${form}"/>
            <br>
        </c:if>
        <input type="file" name="photo" accept="image/*"
        title="<fmt:message key="form.uploadImage" bundle="${form}"/>">
        <br>

        <button type="submit" name="save" value="save"><fmt:message key="button.save" bundle="${label}"/></button>
        <button type="button" onclick="window.location.href='${context}/account/wall?id=${id}'">
            <fmt:message key="button.cancel" bundle="${label}"/>
        </button>
    </form>
</div>
