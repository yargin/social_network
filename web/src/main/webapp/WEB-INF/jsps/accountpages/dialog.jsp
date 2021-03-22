<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="message" tagdir="/WEB-INF/tags/message" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <message:privateMessageCreate/>
    <message:privateMessagesShow/>
</common:layout>