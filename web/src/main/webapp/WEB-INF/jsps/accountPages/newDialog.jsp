<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>

    <form action="${context}/createdialog" method="post" enctype="multipart/form-data">
        <input type="hidden" value="${sessionScope.userId}" name="requesterId">
        <input type="hidden" value="${id}" name="receiverId">
        <input type="hidden" value="${type}" name="type">
        <textarea name="text" placeholder="asd"></textarea>
        <br>
        <input type="file" name="image" accept="image/*"
               title="<fmt:message key="form.uploadImage"/>">
        <br>
        <button type="submit"><fmt:message key="button.send"/></button>
    </form>

</common:layout>