<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<%@ taglib prefix="message" tagdir="/WEB-INF/tags/message" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:layout>
    <account:accountInfo/>
    <account:accountMenu/>

    <c:if test="${not empty owner or not empty friend or not empty admin}">
        <message:messageCreate/>
        <message:messagesShow/>
    </c:if>
</common:layout>
