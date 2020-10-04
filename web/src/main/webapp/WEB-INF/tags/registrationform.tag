<%@ tag import="com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<div class="post">
    <form action="${pageContext.servletContext.contextPath}/registration" method="post">

        <input type="text" name="name"
               <c:if test="${not empty name}">value="${name}"</c:if>
               placeholder="<fmt:message key="form.name" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errname}"><fmt:message key="${errname}" bundle="${error}"/><br></c:if>

        <input type="text" name="surname"
               <c:if test="${not empty surname}">value="${surname}"</c:if>
               placeholder="<fmt:message key="form.surname" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errsurname}"><fmt:message key="${errsurname}" bundle="${error}"/><br></c:if>

        <input type="text" name="patronymic"
               <c:if test="${not empty patronymic}">value="${patronymic}"</c:if>
               placeholder="<fmt:message key="form.patronymic" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errpatronymic}"><fmt:message key="${errpatronymic}" bundle="${error}"/><br></c:if>

        <select name="sex" required>
            <option value="" disabled <c:if test="${empty sex}">selected</c:if>>
                <fmt:message key="form.sex" bundle="${label}"/></option>
            <option value="${male}" <c:if test="${Sex.MALE eq sex}">selected</c:if>>
                <fmt:message key="form.male" bundle="${label}"/></option>
            <option value="${female}" <c:if test="${Sex.FEMALE eq sex}">selected</c:if>>
                <fmt:message key="form.female" bundle="${label}"/></option>
        </select>
        <br>

        <input type="email" name="email"
               <c:if test="${not empty email}">value="${email}"</c:if>
               placeholder="<fmt:message key="form.email" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty erremail}"><fmt:message key="${erremail}" bundle="${error}"/><br></c:if>

        <input type="email" name="additionalEmail"
               <c:if test="${not empty additionalEmail}">value="${additionalEmail}"</c:if>
               placeholder="<fmt:message key="form.additionalEmail" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty erradditionalEmail}"><fmt:message key="${erradditionalEmail}"
                                                                  bundle="${error}"/><br></c:if>

        <input type="password" name="password" placeholder="<fmt:message key="form.password" bundle="${label}"/>"
               required>
        <br>
        <c:if test="${not empty errpassword}"><fmt:message key="${errpassword}" bundle="${error}"/><br></c:if>

        <input type="password" name="confirmPassword"
               placeholder="<fmt:message key="form.confirmPassword" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errconfirmPassword}"><fmt:message key="${errconfirmPassword}"
                                                                  bundle="${error}"/><br></c:if>
        <c:if test="${not empty passNotMatch}"><fmt:message key="${passNotMatch}" bundle="${error}"/><br></c:if>

        <input type="date" name="birthDate"
        <fmt:parseDate value="${birthDate}" pattern="yyyy-MM-dd" var="bd" type="date"/>
        <%--               value="2001-01-01"--%>
               <c:if test="${not empty birthDate}">value="<fmt:parseDate value="${birthDate}" pattern="yyyy-MM-dd"/>"
        </c:if>
               placeholder="<fmt:message key="form.birthdate" bundle="${label}"/>">
        <br>
        <c:if test="${not empty errbirthDate}"><fmt:message key="${errbirthDate}" bundle="${error}"/><br></c:if>
        <%--        <fmt:formatDate pattern="dd/MMM/yyyy" value="${bd}"/>--%>

        <input type="text" name="icq"
               <c:if test="${not empty icq}">value="${icq}"</c:if>
               placeholder="<fmt:message key="form.icq" bundle="${label}"/>">
        <br>
        <c:if test="${not empty erricq}"><fmt:message key="${erricq}" bundle="${error}"/><br></c:if>

        <input type="text" name="skype"
               <c:if test="${not empty skype}">value="${skype}"</c:if>
               placeholder="<fmt:message key="form.skype" bundle="${label}"/>">
        <br>
        <c:if test="${not empty errskype}"><fmt:message key="${errskype}" bundle="${error}"/><br></c:if>

        <input type="text" name="country"
               <c:if test="${not empty country}">value="${country}"</c:if>
               placeholder="<fmt:message key="form.country" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errcountry}"><fmt:message key="${errcountry}" bundle="${error}"/><br></c:if>

        <input type="text" name="city"
               <c:if test="${not empty city}">value="${city}"</c:if>
               placeholder="<fmt:message key="form.city" bundle="${label}"/>" required>
        <br>
        <c:if test="${not empty errcity}"><fmt:message key="${errcity}" bundle="${error}"/><br></c:if>

        <common:phonesedition/>

        <button><fmt:message key="button.register" bundle="${label}"/></button>
    </form>
</div>