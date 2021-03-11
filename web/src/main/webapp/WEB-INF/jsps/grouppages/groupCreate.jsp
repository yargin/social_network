<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
-
<common:layout>
    <div class="post">
        <form action="${context}/group/create" method="post" enctype="multipart/form-data">
            <input type="text" name="name"
                   <c:if test="${not empty group.name}">value="${group.name}"</c:if>
                   placeholder="<fmt:message key="form.groupName" bundle="${form}"/>" required>
            <br>
            <c:if test="${not empty nameDuplicate}"><fmt:message key="${nameDuplicate}" bundle="${error}"/><br></c:if>
            <textarea name="description">${group.description}</textarea>
            <br>

            <c:if test="${not empty photo}">
                <img src="data:image/jpeg;base64, ${photo}">
                <br>
                <fmt:message key="form.chooseAnother" bundle="${form}"/>
                <br>
            </c:if>
            <input type="file" name="photo" accept="image/*"
                   title="<fmt:message key="form.uploadImage" bundle="${form}"/>">
            <br>
            <button type="submit" name="save" value="save"><fmt:message key="button.save" bundle="${label}"/></button>
            <button type="submit" name="save" value="cancel"><fmt:message key="button.cancel"
                                                                          bundle="${label}"/></button>
        </form>
    </div>
</common:layout>