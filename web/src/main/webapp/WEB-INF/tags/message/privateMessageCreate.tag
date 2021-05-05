<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="wallMessage" style="margin-right: 40%">
    <form action="${context}/message/dialog/add" method="post" enctype="multipart/form-data" style="">
        <input type="hidden" value="${sessionScope.userId}" name="requesterId">
        <input type="hidden" value="${dialog.id}" name="receiverId">
        <input type="hidden" value="${type}" name="type">
        <div style=" border-bottom: none;">
            <textarea name="text" placeholder="<fmt:message key="form.inputText" bundle="${form}"/>"></textarea>
        </div>
        <br>
        <input type="file" name="image" accept="image/*"
               title="<fmt:message key="form.uploadImage" bundle="${form}"/>">
        <br>
        <button type="submit"><fmt:message key="button.send" bundle="${label}"/></button>
    </form>
    <%-- bottom border --%>
    <div>
    </div>
</div>