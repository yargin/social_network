<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<fmt:message key="label.author" var="author"/>
<fmt:message key="label.date" var="date"/>

<div id="messagesShow"></div>
<%--                <c:if test="${not empty admin or author.id == sessionScope.userId}">--%>
<%--                    <form action="${context}/message/dialog/delete" method="post" style="margin-bottom: 5px;">--%>
<%--                        <input type="hidden" value="${id}" name="receiverId">--%>
<%--                        <input type="hidden" value="${author.id}" name="requesterId">--%>
<%--                        <input type="hidden" value="${message.id}" name="id">--%>
<%--                        <input type="hidden" value="${type}" name="type">--%>
<%--                        <button type="submit"><fmt:message key="button.delete"/></button>--%>
<%--                    </form>--%>
<%--                </c:if>--%>

<template id="template">
    <div class="wallMessage">
        <div>
            <p id="dateLabel">${date}: </p>
            <p id="authorLabel">${author}:
                <a id="authorLink" href="${context}/account/wall?id="></a>
            </p>
        </div>
        <div>
            <p id="messageText">${message.text}</p>
            <img id="messageImage">
        </div>
    </div>
</template>
