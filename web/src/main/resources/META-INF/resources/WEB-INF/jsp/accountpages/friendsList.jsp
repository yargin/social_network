<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/account" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <account:accountInfo/>
    <account:accountMenu/>

    <br><br>
    <c:forEach items="${friends}" var="friend">
        <div class="post">
            <form action="${context}/friendship/remove" method="post">
                <a href="${context}/account/wall?id=${friend.id}">${friend.name} ${friend.surname}</a>
                <input type="hidden" value="${friend.id}" name="requesterId">
                <input type="hidden" value="${user.id}" name="receiverId">
                <button type="submit" name="accept"><fmt:message key="button.delete"/></button>
            </form>
        </div>
        <br>
    </c:forEach>
</common:layout>