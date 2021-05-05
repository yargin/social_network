<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <div class="wallMessage" style="margin-right: 40%">
        <form action="${context}/dialog/create" method="post" enctype="multipart/form-data">
            <input type="hidden" value="${sessionScope.userId}" name="requesterId">
            <input type="hidden" value="${id}" name="receiverId">
            <input type="hidden" value="${type}" name="type">
            <div style=" border-bottom: none;">
                <textarea name="text"></textarea>
            </div>
            <br>
            <input type="file" name="imageUpload" accept="image/*"
                   title="<fmt:message key="form.uploadImage"/>">
            <br>
            <button type="submit"><fmt:message key="button.send"/></button>
        </form>
            <%-- bottom border --%>
        <div>
        </div>
    </div>
</common:layout>