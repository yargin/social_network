<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="wallMessage" style="margin-left: 10%; margin-right: 10%">
    <form action="${context}/message/${type}/add" method="post" enctype="multipart/form-data">
        <input type="hidden" value="${sessionScope.userId}" name="author.id">
        <input type="hidden" value="${sessionScope.userId}" name="requesterId">
        <input type="hidden" value="${id}" name="receiver.id">
        <input type="hidden" value="${id}" name="receiverId">
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
