<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="post">
    <form action="${context}/login" method="post">
        <c:if test="${not empty logerror}"><fmt:message key="${logerror}" bundle="${error}"/><br></c:if>
        <input type="email" name="email" placeholder="<fmt:message key="form.email" bundle="${form}"/>"
               value="<c:if test="${not empty email}">${email}</c:if>"
               required>
        <br>
        <input type="password" name="password" placeholder="<fmt:message key="form.password" bundle="${form}"/>"
               required>
        <br>
        <fmt:message key="form.rememberMe" bundle="${form}"/><input type="checkbox" name="rememberMe">
        <br>
        <button type="submit"><fmt:message key="button.login" bundle="${label}"/></button>
        <br>
        <fmt:message key="label.notRegistered" bundle="${label}"/> <a
            href="${context}/registration">
        <fmt:message key="button.register" bundle="${label}"/></a>

    </form>
</div>