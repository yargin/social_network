<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<fmt:setBundle basename="error" var="error"/>
<c:if test="${not empty concurrentError}">
    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="${context}/js/notification.js" defer></script>
    <div id="notification">
        <fmt:message key="${concurrentError}" bundle="${error}"/>
    </div>
</c:if>

<common:layout>
    <div class="info">
            <%--@elvariable id="group" type="com.getjavajob.training.yarginy.socialnetwork.common.models.Group"--%>
        <form:form action="${context}/group/update?id=${group.id}" method="post" enctype="multipart/form-data"
                   modelAttribute="group">
            <label><spring:message code="form.groupName"/></label>
            <form:input path="name" value="${group.name}" required="required"/> <br>
            <form:errors path="name" element="div"/> <br>

            <label><spring:message code="form.description"/></label>
            <form:textarea path="description"/> <br>
            <c:if test="${not empty photo}">
                <img src="data:image/jpeg;base64, ${photo}">
                <br>
                <spring:message code="form.chooseAnother"/>
                <br>
            </c:if>
            <spring:message code="form.uploadImage" var="uploadImage"/>
            <form:input type="file" path="photo" accept="image/*" title="${uploadImage}"/> <br>
            <button type="submit" name="save" value="save"><spring:message code="button.save"/></button>
            <button type="submit" name="save" value="cancel" formnovalidate><spring:message
                    code="button.cancel"/></button>
        </form:form>
    </div>
</common:layout>
