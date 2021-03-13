<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>
<script type="text/javascript" src="${context}/js/confirmationDialog.js"></script>

<div class="info">
    <spring:message code="label.saveUpdates" var="confirmText"/>
    <%--@elvariable id="account" type="com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account"--%>
    <form:form action="${context}/account/update?id=${id}" method="post" enctype="multipart/form-data"
               id="accountUpdate" onsubmit='return confirmation("${confirmText}", acceptPhones())'
               modelAttribute="account">

        <label><spring:message code="form.name"/></label>
        <form:input type="text" path="name" value="${account.name}" required="required"/> <br>
        <form:errors path="name" element="div"/>

        <label><spring:message code="form.surname"/></label>
        <form:input type="text" path="surname" value="${account.surname}" required="required"/> <br>
        <form:errors path="surname" element="div"/>

        <label><spring:message code="form.patronymic"/></label>
        <form:input type="text" path="patronymic" value="${account.patronymic}"/> <br>

        <label><spring:message code="form.sex"/></label>
        <select name="sex" required>
            <option value="${male}" <c:if test="${Sex.MALE eq account.sex}">selected</c:if>>
                <spring:message code="form.male"/></option>
            <option value="${female}" <c:if test="${Sex.FEMALE eq account.sex}">selected</c:if>>
                <spring:message code="form.female"/></option>
        </select>
        <br>

        <label><spring:message code="form.additionalEmail"/></label>
        <form:input type="email" path="additionalEmail" value="${account.additionalEmail}"/> <br>
        <form:errors path="additionalEmail" element="div"/>
        <c:if test="${not empty emailDuplicate}"><spring:message code="${emailDuplicate}"/><br></c:if>

        <label><spring:message code="form.birthdate"/></label>
        <form:input type="date" path="birthDate" value="${account.birthDate}"/> <br>
        <form:errors path="birthDate" element="div"/>

        <label><spring:message code="form.icq"/></label>
        <form:input type="text" path="icq" value="${account.icq}"/> <br>
        <form:errors path="icq" element="div"/>

        <label><spring:message code="form.skype"/></label>
        <form:input type="text" path="skype" value="${account.skype}"/> <br>
        <form:errors path="skype" element="div"/>

        <label><spring:message code="form.country"/></label>
        <form:input type="text" path="country" value="${account.country}" required="required"/> <br>
        <form:errors path="country" element="div"/>

        <label><spring:message code="form.city"/></label>
        <form:input type="text" path="city" value="${account.city}" required="required"/> <br>
        <form:errors path="city" element="div"/>

        <common:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <spring:message code="form.chooseAnother"/>
            <br>
        </c:if>
        <spring:message code="form.uploadImage" var="uploadImage"/>
        <form:input type="file" path="photo" accept="image/*" title="${uploadImage}"/> <br>

        <button type="submit" name="save" value="save"><spring:message code="button.save"/></button>
        <%--        todo--%>
        <button type="button" onclick="window.location.href='${context}/account/wall?id=${id}'">
            <spring:message code="button.cancel"/>
        </button>
        <%--        <button type="submit" name="save" value="cancel" formnovalidate><spring:message--%>
        <%--                code="button.cancel"/></button>--%>

    </form:form>
</div>
