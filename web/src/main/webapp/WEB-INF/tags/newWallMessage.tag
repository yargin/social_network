<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="wallMessage">
    <form action="${context}/postMessage" enctype="multipart/form-data">
        <%--        <input type="hidden" value="${user.getId()}" name="requesterId">--%>
        <input type="hidden" value="${sessionScope.userId}" name="receiverId">
        <textarea name="message" placeholder="asd">asdasd</textarea>
        <input type="file" name="photo" accept="image/*"
        <%--        <c:if test="${not empty photo}">--%>
        <%--               value="${photo}"--%>
        <%--        </c:if>--%>
               title="<fmt:message key="form.uploadImage" bundle="${label}"/>">
        <br>
        <button><fmt:message key="button.create" bundle="${label}"/></button>
    </form>
</div>