<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="error" var="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <div class="info">
        <form action="${context}/updategroup?id=${group.getId()}" method="post" enctype="multipart/form-data">
            <label><fmt:message key="form.groupName" bundle="${label}"/> : </label>
            <input type="text" name="name" value="${group.getName()}">
            <br>
            <c:if test="${not empty errname}"><fmt:message key="${errname}" bundle="${error}"/><br></c:if>
            <c:if test="${not empty nameDuplicate}"><fmt:message key="${nameDuplicate}" bundle="${error}"/><br></c:if>
            <br>

            <label><fmt:message key="form.description" bundle="${label}"/> : </label>
            <textarea name="description">${group.getDescription()}</textarea>
            <br>
            <c:if test="${not empty photo}">
                <img src="data:image/jpeg;base64, ${photo}">
                <br>
                <fmt:message key="form.chooseAnother" bundle="${label}"/>
                <br>
            </c:if>
            <input type="file" name="photo" accept="image/*"
                   title="<fmt:message key="form.uploadImage" bundle="${label}"/>">
            <br>
            <button type="submit" name="save" value="save"><fmt:message key="button.save" bundle="${label}"/></button>
            <button type="submit" name="save" value="cancel"><fmt:message key="button.cancel"
                                                                          bundle="${label}"/></button>
        </form>
    </div>
</common:layout>