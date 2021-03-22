<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<c:forEach var="message" items="${messages}">
    <c:set var="author" value="${message.author}"/>
    <jsp:useBean id="dataHandler" class="com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler"/>
    <div class="wallMessage"
            <c:choose>
                <c:when test="${author.id == sessionScope.userId}">
                    style="margin-right: 40%;"
                </c:when>
                <c:otherwise>
                    style="margin-left: 40%;"
                </c:otherwise>
            </c:choose>>
        <div>
            <p><fmt:message key="label.date"/>: <fmt:formatDate value="${message.date}" type="both"/></p>
            <p><fmt:message key="label.author"/>:
                <a href="${context}/account/wall?id=${author.id}">${author.name} ${author.surname}</a>
            </p>
            <div style="min-width: 50px; border-bottom: none;">
                <c:if test="${not empty admin or author.id == sessionScope.userId}">
                    <form action="${context}/message/delete" method="post" style="margin-bottom: 5px;">
                        <input type="hidden" value="${id}" name="receiverId">
                        <input type="hidden" value="${author.id}" name="requesterId">
                        <input type="hidden" value="${message.id}" name="id">
                        <input type="hidden" value="${type}" name="type">
                        <button type="submit"><fmt:message key="button.delete"/></button>
                    </form>
                </c:if>
            </div>
        </div>
        <div>
            <p>${message.text}</p>
            <c:set var="image" value="${dataHandler.getHtmlPhoto(message.getImage())}"/>
            <c:if test="${not empty image}">
                <img src="data:image/jpeg;base64, ${image}">
            </c:if>
        </div>
    </div>
</c:forEach>