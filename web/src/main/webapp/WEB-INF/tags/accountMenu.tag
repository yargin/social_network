<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value="${user.getId()}"/>

<div>
    <c:if test="${tab ne 'wall'}">
        <c:if test="${not empty friend or not empty admin or not empty owner}">
            <a href="#">
                <button type="submit" name="wall"><fmt:message key="button.wall"/></button>
            </a>
        </c:if>
    </c:if>

    <c:if test="${tab ne 'addFriend'}">
        <c:if test="${empty friend and empty owner}">
            <a href="${context}/addfriend?id1=${sessionScope.userId}&id2=${id}">
                <button type="submit" name="addFriend"><fmt:message key="button.addToFriends"/></button>
            </a>
        </c:if>
    </c:if>

    <c:if test="${tab ne 'friendshipRequestsList'}">
        <c:if test="${not empty admin or not empty owner}">
            <a href="${context}/friendsrequests?id=${id}">
                <button type="submit" name="requests"><fmt:message key="button.friendshipRequests"/></button>
            </a>
        </c:if>
    </c:if>

    <c:if test="${not empty friend or not empty admin and empty owner}">
        <a href="#">
            <button type="submit" name="message"><fmt:message key="button.privateMessage"/></button>
        </a>
    </c:if>

    <c:if test="${not empty admin or not empty owner or not empty friend}">
        <a href="#">
            <button type="submit" name="friends"><fmt:message key="button.friends"/></button>
        </a>
    </c:if>

    <c:if test="${not empty admin or not empty owner}">
        <a href="#">
            <button type="submit" name="dialogs"><fmt:message key="button.dialogs"/></button>
        </a>
    </c:if>
</div>