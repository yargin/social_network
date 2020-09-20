<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label"/>

<common:layout>
    <div class="post">
        <form action="${pageContext.servletContext.contextPath}/login" method="post">
            <common:error/>
            <input type="email" name="email" placeholder="<fmt:message key="form.email"/>"
                   value="<c:if test="${not empty email}">${email}</c:if>"
                   required>
            <input type="password" name="password" placeholder="<fmt:message key="form.password"/>" required>
            <button type="submit"><fmt:message key="button.login"/></button>
            <p><fmt:message key="label.notRegistered"/> <a
                    href="${pageContext.servletContext.contextPath}/registration">
                <fmt:message key="button.register"/></a>
            </p>
        </form>
    </div>
</common:layout>

