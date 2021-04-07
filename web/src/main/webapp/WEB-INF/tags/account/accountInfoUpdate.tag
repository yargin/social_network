<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Sex" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>
<script type="text/javascript" src="${context}/js/confirmationDialog.js"></script>

<div class="info">
    <spring:message code="label.saveUpdates" var="confirmText"/>
    <%--@elvariable id="accountInfoMvcModel" type="com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel"--%>
    <form:form action="${context}/account/update?id=${id}" method="post" enctype="multipart/form-data"
               onsubmit='return confirmation("${confirmText}", acceptPhones())'
               id="accountUpdate" modelAttribute="accountInfoMvcModel">

        <%--@elvariable id="account" type="com.getjavajob.training.yarginy.socialnetwork.common.models.Account"--%>
        <label><spring:message code="form.name"/></label>
        <form:input type="text" path="account.name" value="${account.name}" required="required"/> <br>
        <form:errors path="account.name" element="div"/>

        <label><spring:message code="form.surname"/></label>
        <form:input type="text" path="account.surname" value="${account.surname}" required="required"/> <br>
        <form:errors path="account.surname" element="div"/>

        <label><spring:message code="form.patronymic"/></label>
        <form:input type="text" path="account.patronymic" value="${account.patronymic}"/> <br>

        <label><spring:message code="form.sex"/></label>
        <form:select path="account.sex">
            <spring:message code="form.male" var="male"/>
            <form:option value="${Sex.MALE}" label="${male}"/>
            <spring:message code="form.female" var="female"/>
            <form:option value="${Sex.FEMALE}" label="${female}"/>
        </form:select><br>

        <label><spring:message code="form.additionalEmail"/></label>
        <form:input type="email" path="account.additionalEmail" value="${account.additionalEmail}"/> <br>
        <form:errors path="account.additionalEmail" element="div"/>

        <label><spring:message code="form.birthdate"/></label>
        <form:input type="date" path="account.birthDate" value="${account.birthDate}"/> <br>
        <form:errors path="account.birthDate" element="div"/>

        <label><spring:message code="form.icq"/></label>
        <form:input type="text" path="account.icq" value="${account.icq}"/> <br>
        <form:errors path="account.icq" element="div"/>

        <label><spring:message code="form.skype"/></label>
        <form:input type="text" path="account.skype" value="${account.skype}"/> <br>
        <form:errors path="account.skype" element="div"/>

        <label><spring:message code="form.country"/></label>
        <form:input type="text" path="account.country" value="${account.country}" required="required"/> <br>
        <form:errors path="account.country" element="div"/>

        <label><spring:message code="form.city"/></label>
        <form:input type="text" path="account.city" value="${account.city}" required="required"/> <br>
        <form:errors path="account.city" element="div"/>

        <account:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <spring:message code="form.chooseAnother"/>
            <br>
        </c:if>
        <spring:message code="form.uploadImage" var="uploadImage"/>
        <form:input type="file" path="account.photo" accept="image/*" title="${uploadImage}"/> <br>

        <button type="submit" name="save" value="save"><spring:message code="button.save"/></button>
        <button type="submit" name="save" value="cancel" onclick="skipConfirmation()" formnovalidate>
            <spring:message code="button.cancel"/></button>
    </form:form>
</div>
