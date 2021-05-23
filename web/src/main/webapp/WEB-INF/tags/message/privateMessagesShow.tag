<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>



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



<%--<c:set var="author" value="${message.author}"/>--%>
<%--<jsp:useBean id="dataHandler" class="com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler"/>--%>
<%--<div class="wallMessage" style="margin-right: 40%;">--%>
<%--    <div>--%>
<%--        <p>date: ${message.date}</p>--%>
<%--        <p>author:--%>
<%--            <a href="${context}/account/wall?id=${author.id}">${author.name} ${author.surname}</a>--%>
<%--        </p>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <p>${message.text}</p>--%>
<%--        <c:if test="${not empty image}">--%>
<%--        <c:set var="image" value="${dataHandler.getHtmlPhoto(message.getImage())}"/>--%>
<%--            <img src="data:image/jpeg;base64, ${image}">--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--</div>--%>