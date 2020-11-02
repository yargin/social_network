<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<input type="hidden" value="${group}" name="group"/>

<common:layout>
    <div class="post">
        <form action="${context}/creategroup" method="post" enctype="multipart/form-data">
            <input type="text" name="name"
                   <c:if test="${not empty name}">value="${name}"</c:if>
                   placeholder="<fmt:message key="form.groupName" bundle="${label}"/>" required>
            <br>
            <c:if test="${not empty errname}"><fmt:message key="${errname}" bundle="${error}"/><br></c:if>
            <br>
            <textarea name="description"
                      <c:if test="${not empty description}">value="${description}"</c:if>
                      placeholder="<fmt:message key="form.description" bundle="${label}"/>"></textarea>
            <br>
            <c:if test="${not empty photo}">
                <img src="data:image/jpeg;base64, ${photo}">
                <br>
                <fmt:message key="form.chooseAnother" bundle="${label}"/>
                <br>
            </c:if>
            <input type="file" name="photo" accept="image/*"
            <c:if test="${not empty photo}">
                   value="${photo}"
            </c:if>
                   title="<fmt:message key="form.uploadImage" bundle="${label}"/>">
            <br>
            <button><fmt:message key="button.create" bundle="${label}"/></button>
        </form>

    </div>
</common:layout>