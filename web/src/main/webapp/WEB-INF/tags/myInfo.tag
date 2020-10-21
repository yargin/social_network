<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<c:if test="${not empty user}">
    <div class="info">
        <fmt:message key="form.name"/> : ${user.getName()}<br>
        <fmt:message key="form.surname"/> : ${user.getSurname()}<br>
        <fmt:message key="form.patronymic"/> : ${user.getPatronymic()}<br>
        <fmt:message key="form.sex"/> : <c:if test="${not empty user.getSex()}">
            ${user.getSex().toString().toLowerCase()}
        </c:if><br>
        <fmt:message key="form.email"/> : ${user.getEmail()}<br>
        <fmt:message key="form.registrationDate"/> : ${user.getRegistrationDate()}<br>
        <fmt:message key="form.birthdate"/> : ${user.getBirthDate()}<br>
        <fmt:message key="form.privatePhones"/> :
        <c:forEach var="phone" items="${privatePhones}">
            <br>
            ${phone.getNumber()}
        </c:forEach>
        <br>
        <fmt:message key="form.workPhones"/> :
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
    <div>
        <c:if test="${not empty updateAble}">
            <a href="${context}/updateInfo"><fmt:message key="label.updateInfo"/></a><br>
            <a href="${context}/deleteAccount"><fmt:message key="label.deleteAccount"/></a>
        </c:if>
    </div>
</c:if>