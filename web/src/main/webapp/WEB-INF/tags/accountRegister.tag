<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>

<spring:message code="label.saveUpdates" var="confirmText"/>
<div class="post">
    <%--@elvariable id="registrationMvcModel" type="com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel"--%>
    <form:form action="${context}/registration" method="post" enctype="multipart/form-data"
               onsubmit="return acceptPhones()" modelAttribute="registrationMvcModel">
        <%--@elvariable id="account" type="com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account"--%>
        <spring:message code="form.name" var="formName"/>
        <form:input type="text" path="account.name" value="${account.name}" placeholder="${formName}"
                    required="required"/> <br>
        <form:errors path="account.name" element="div"/>

        <spring:message code="form.surname" var="formSurname"/>
        <form:input type="text" path="account.surname" value="${account.surname}" placeholder="${formSurname}"
                    required="required"/> <br>
        <form:errors path="account.surname" element="div"/>

        <spring:message code="form.patronymic" var="formPatronymic"/>
        <form:input type="text" path="account.patronymic" value="${account.patronymic}"
                    placeholder="${formPatronymic}"/> <br>

        <label><spring:message code="form.sex"/></label>
        <form:select path="account.sex">
            <spring:message code="form.male" var="male"/>
            <form:option value="${Sex.MALE}" label="${male}"/>
            <spring:message code="form.female" var="female"/>
            <form:option value="${Sex.FEMALE}" label="${female}"/>
        </form:select><br>

        <spring:message code="form.email" var="formEmail"/>
        <form:input type="email" path="account.email" value="${account.email}"
                    placeholder="${formEmail}" required="required"/> <br>
        <form:errors path="account.email" element="div"/>

        <spring:message code="form.additionalEmail" var="formAdditionalEmail"/>
        <form:input type="email" path="account.additionalEmail" value="${account.additionalEmail}"
                    placeholder="${formAdditionalEmail}" required="required"/> <br>
        <form:errors path="account.additionalEmail" element="div"/>

        <spring:message code="form.password" var="formPassword"/>
        <form:input type="password" path="password.stringPassword" placeholder="${formPassword}"/> <br>
        <form:errors path="password.stringPassword" element="div"/>

        <spring:message code="form.confirmPassword" var="formConfirmPassword"/>
        <form:input type="password" path="confirmPassword.stringPassword" placeholder="${formConfirmPassword}"/> <br>
        <form:errors path="confirmPassword.stringPassword" element="div"/>

        <label><spring:message code="form.birthdate"/></label>
        <form:input type="date" path="account.birthDate" value="${account.birthDate}"/> <br>
        <form:errors path="account.birthDate" element="div"/>

        <spring:message code="form.icq" var="formIcq"/>
        <form:input type="text" path="account.icq" value="${account.icq}" placeholder="${formIcq}"/> <br>

        <spring:message code="form.skype" var="formSkype"/>
        <form:input type="text" path="account.skype" value="${account.skype}" placeholder="${formSkype}"/> <br>


        <spring:message code="form.country" var="formCountry"/>
        <form:input type="text" path="account.country" value="${account.country}"
                    placeholder="${formCountry}" required="required"/> <br>
        <form:errors path="account.country" element="div"/>

        <spring:message code="form.city" var="formCity"/>
        <form:input type="text" path="account.city" value="${account.city}" placeholder="${formCity}"
                    required="required"/> <br>
        <form:errors path="account.city" element="div"/>

        <common:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <spring:message code="form.chooseAnother"/>
            <br>
        </c:if>
        <spring:message code="form.uploadImage" var="photoLabel"/>
        <form:input type="file" path="account.photo" title="${photoLabel}"/>
        <br>

        <button><spring:message code="button.save"/></button>
    </form:form>
</div>