<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="message" tagdir="/WEB-INF/tags/message" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<script src="${context}/webjars/jquery/2.2.4/jquery.min.js"></script>
<script src="${context}/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="${context}/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
<script type="text/javascript" src="${context}/js/dialogChat.js"></script>

<script>initValues('${context}', '${sessionScope.userId}', '${messages}', '${dialog.id}')</script>
<common:layout>
    <message:privateMessageCreate/>
    <message:privateMessagesShow/>
</common:layout>