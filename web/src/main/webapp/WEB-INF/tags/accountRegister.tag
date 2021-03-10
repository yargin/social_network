<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<fmt:setBundle basename="form" var="form"/>


<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script type="text/javascript" src="${context}/js/phonesEdit.js"></script>

<fmt:message key="label.saveUpdates" var="confirmText" bundle="${label}"/>
<div class="post">
    <form action="${context}/registration" method="post" enctype="multipart/form-data"
          onsubmit="return acceptPhones()">

        <input type="text" name="name" value="${account.name}"
               placeholder='<fmt:message key="form.name" bundle="${form}"/>' required>
        <br>

        <input type="text" name="surname" value="${account.surname}"
               placeholder='<fmt:message key="form.surname" bundle="${form}"/>' required>
        <br>

        <input type="text" name="patronymic" value="${account.patronymic}"
               placeholder='<fmt:message key="form.patronymic" bundle="${form}"/>'>
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

        <input type="email" name="email" value="${account.email}"
               placeholder='<fmt:message key="form.email" bundle="${form}"/>' required>
        <br>

        <input type="email" name="additionalEmail" value="${account.additionalEmail}"
               placeholder='<fmt:message key="form.additionalEmail" bundle="${form}"/>' required>
        <br>
        <c:if test="${not empty emailDuplicate}"><fmt:message key="${emailDuplicate}" bundle="${error}"/><br></c:if>

        <input type="password" name="password" placeholder="<fmt:message key="form.password" bundle="${form}"/>"
               required>
        <br>

        <input type="password" name="confirmPassword"
               placeholder='<fmt:message key="form.confirmPassword" bundle="${form}"/>' required>
        <br>
        <c:if test="${not empty passNotMatch}"><fmt:message key="${passNotMatch}" bundle="${error}"/><br></c:if>


        <label><fmt:message key="form.birthdate" bundle="${form}"/></label>
        <input type="date" name="birthDate" value="${account.birthDate}">
        <br>

        <input type="text" name="icq" value="${account.icq}"
               placeholder='<fmt:message key="form.icq" bundle="${form}"/>'>
        <br>

        <input type="text" name="skype" value="${account.skype}"
               placeholder='<fmt:message key="form.skype" bundle="${form}"/>'>
        <br>

        <input type="text" name="country" value="${account.country}"
               placeholder='<fmt:message key="form.country" bundle="${form}"/>' required>
        <br>

        <input type="text" name="city" value="${account.city}"
               placeholder='<fmt:message key="form.city" bundle="${form}"/>' required>
        <br>

        <common:phonesUpdate/>

        <c:if test="${not empty photo}">
            <img src="data:image/jpeg;base64, ${photo}">
            <br>
            <fmt:message key="form.chooseAnother" bundle="${form}"/>
            <br>
        </c:if>
        <input type="file" placeholder="asd" name="photo" accept="image/*"
               title="<fmt:message key="form.uploadImage" bundle="${form}"/>">
        <br>

        <button><fmt:message key="button.register" bundle="${label}"/></button>
    </form>
</div>