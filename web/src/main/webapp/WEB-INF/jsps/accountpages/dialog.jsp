<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="message" tagdir="/WEB-INF/tags/message" %>
<fmt:setBundle basename="label" var="label"/>
<fmt:setBundle basename="form" var="form"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<fmt:message key="label.author" var="author" bundle="${label}"/>
<fmt:message key="label.date" var="date" bundle="${label}"/>

<script src="${context}/webjars/jquery/2.2.4/jquery.min.js"></script>
<script src="${context}/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="${context}/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
<script src="${context}/js/dialogChat.js"></script>

<script>initDialogProperties('${context}', '${sessionScope.userId}', '${messages}', '${dialog.id}')</script>
<common:layout>
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

    <div id="messagesShow"></div>

    <template id="template">
        <div class="wallMessage" id="dialogMessage" style="display: none">
            <div>
                <p id="dateLabel">${date}: </p>
                <p id="authorLabel">${author}: <a id="authorLink"></a></p>
                <button id="delete" style="margin-bottom: auto"><fmt:message key="button.delete"
                                                                             bundle="${label}"/></button>
            </div>
            <div>
                <p id="messageText"></p>
                <img id="messageImage">
            </div>
        </div>
    </template>
</common:layout>