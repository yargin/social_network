<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>

<spring:message code="label.saveUpdates" var="confirmText"/>
<div class="post">
    <%--@elvariable id="account" type="com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account"--%>
    <form:form action="${context}/registration" method="post" enctype="multipart/form-data"
               onsubmit="return acceptPhones()" modelAttribute="account">

        <spring:message code="form.name" var="formName"/>
        <form:input type="text" path="name" value="${account.name}" placeholder="${formName}" required="required"/> <br>
        <form:errors path="name" element="div"/>

        <spring:message code="form.surname" var="formSurname"/>
        <form:input type="text" path="surname" value="${account.surname}" placeholder="${formSurname}"
                    required="required"/> <br>
        <form:errors path="surname" element="div"/>

        <spring:message code="form.patronymic" var="formPatronymic"/>
        <form:input type="text" path="patronymic" value="${account.patronymic}" placeholder="${formPatronymic}"/> <br>

        <label><spring:message code="form.sex"/></label>
        <select name="sex" required>
            <option value="${male}" <c:if test="${Sex.MALE eq account.sex}">selected</c:if>>
                <spring:message code="form.male"/></option>
            <option value="${female}" <c:if test="${Sex.FEMALE eq account.sex}">selected</c:if>>
                <spring:message code="form.female"/></option>
        </select>
        <br>

        <spring:message code="form.email" var="formEmail"/>
        <form:input type="email" path="email" value="${account.email}"
                    placeholder="${formEmail}" required="required"/> <br>
        <form:errors path="email" element="div"/>

        <spring:message code="form.additionalEmail" var="formAdditionalEmail"/>
        <form:input type="email" path="additionalEmail" value="${account.additionalEmail}"
                    placeholder="${formAdditionalEmail}" required="required"/> <br>
        <form:errors path="additionalEmail" element="div"/>
        <c:if test="${not empty emailDuplicate}"><spring:message code="${emailDuplicate}"/><br></c:if>

        <input type="password" name="password" placeholder='<spring:message code="form.password"/>'
               required> <br>

        <input type="password" name="confirmPassword"
               placeholder='<spring:message code="form.confirmPassword"/>' required> <br>
        <c:if test="${not empty passNotMatch}"><spring:message code="${passNotMatch}"/><br></c:if>


        <label><spring:message code="form.birthdate"/></label>
        <form:input type="date" path="birthDate" value="${account.birthDate}"/> <br>
        <form:errors path="birthDate" element="div"/>

        <spring:message code="form.icq" var="formIcq"/>
        <form:input type="text" path="icq" value="${account.icq}" placeholder="${formIcq}"/> <br>

        <spring:message code="form.skype" var="formSkype"/>
        <form:input type="text" path="skype" value="${account.skype}" placeholder="${formSkype}"/> <br>


        <spring:message code="form.country" var="formCountry"/>
        <form:input type="text" path="country" value="${account.country}"
                    placeholder="${formCountry}" required="required"/> <br>
        <form:errors path="country" element="div"/>

        <spring:message code="form.city" var="formCity"/>
        <form:input type="text" path="city" value="${account.city}" placeholder="${formCity}" required="required"/> <br>
        <form:errors path="city" element="div"/>

        <common:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <spring:message code="form.chooseAnother"/>
            <br>
        </c:if>
        <input type="file" placeholder="asd" name="photo" accept="image/*"
               title="<spring:message code="form.uploadImage"/>">
        <br>

        <button><spring:message code="button.save"/></button>
    </form:form>
</div>