<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <div class="post">
        <account:accountInfoUpdate/>
    </div>
    <c:if test="${not empty uploadError}">
        <fmt:message key="${uploadError}" bundle="${error}"/>
    </c:if>
    <form method="post" action="${context}/account/upload?id=${id}" enctype="multipart/form-data">
        <label><fmt:message key="label.uploadAccount" bundle="${label}"/></label>
        <input type="file" name="accountDataXml" accept="application/xml" title="lalala"/>
        <button type="submit"><fmt:message key="button.upload" bundle="${label}"/></button>
    </form>
</common:layout>