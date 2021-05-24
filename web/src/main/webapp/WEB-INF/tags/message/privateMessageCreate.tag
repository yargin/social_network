<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<%--move to jsp--%>
<div class="wallMessage" style="margin-right: 40%">
    <form>
        <input type="hidden" value="${sessionScope.userId}" id="requesterId">
        <div style=" border-bottom: none;">
            <textarea id="text" placeholder="<fmt:message key="form.inputText" bundle="${form}"/>"></textarea>
        </div>
        <br>
        <input type="file" id="image" accept="image/*"
               title="<fmt:message key="form.uploadImage" bundle="${form}"/>">
        <br>
        <button type="button" id="send"><fmt:message key="button.send" bundle="${label}"/></button>
    </form>
    <%-- bottom border --%>
    <div>
    </div>
</div>