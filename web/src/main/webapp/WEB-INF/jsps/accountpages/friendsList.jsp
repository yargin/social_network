<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <common:accountInfo/>
    <common:accountMenu/>

    <br><br>
    <c:forEach items="${friends}" var="friend">
        <div class="post">
            <form action="${context}/removefriendship" method="post">
                <a href="${context}/account/wall?id=${friend.getId()}">
                        ${friend.getName()} ${friend.getSurname()}
                </a>
                <input type="hidden" value="${friend.getId()}" name="requesterId">
                <input type="hidden" value="${user.getId()}" name="receiverId">
                <button type="submit" name="accept"><fmt:message key="button.delete"/></button>
            </form>
        </div>
        <br>
    </c:forEach>

</common:layout>