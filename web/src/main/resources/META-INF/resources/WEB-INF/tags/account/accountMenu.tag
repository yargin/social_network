<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div>
    <c:if test="${not empty friend or not empty admin or not empty owner}">
        <c:choose>
            <c:when test="${tab eq 'wall'}">
                <strong>
                    <fmt:message key="button.wall"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/account/wall?id=${id}">
                    <button type="button" name="wall"><fmt:message key="button.wall"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${empty friend and empty owner and empty requester}">
        <c:choose>
            <c:when test="${tab eq 'addFriend'}">
                <strong>
                    <fmt:message key="button.addToFriends"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/friendship/add?requesterId=${sessionScope.userId}&receiverId=${id}">
                    <button type="submit" name="addFriend"><fmt:message key="button.addToFriends"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty admin or not empty owner}">
        <c:choose>
            <c:when test="${tab eq 'friendshipRequestsList'}">
                <strong>
                    <fmt:message key="button.friendshipRequests"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/account/requests?id=${id}">
                    <button type="submit" name="requests"><fmt:message key="button.friendshipRequests"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty admin or not empty owner or not empty friend}">
        <c:choose>
            <c:when test="${tab eq 'friendsList'}">
                <strong>
                    <fmt:message key="button.friends"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/account/friends?id=${id}">
                    <button type="submit" name="friends"><fmt:message key="button.friends"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty friend or not empty admin and empty owner}">
        <form action="${context}/dialog/new" method="post" style="display: inline">
            <input type="hidden" name="requesterId" value="${sessionScope.userId}">
            <input type="hidden" name="receiverId" value="${id}">
            <input type="hidden" name="type" value="accountPrivate">
            <button type="submit"><fmt:message key="button.privateMessage"/></button>
        </form>
    </c:if>

    <c:if test="${not empty admin or not empty owner}">
        <c:choose>
            <c:when test="${tab eq 'dialogs'}">
                <strong>
                    <fmt:message key="button.dialogs"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/account/dialogs?id=${id}">
                    <button type="submit" name="dialogs"><fmt:message key="button.dialogs"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty admin or not empty owner or not empty friend}">
        <c:choose>
            <c:when test="${tab eq 'groups'}">
                <strong>
                    <fmt:message key="menu.groups"/>
                </strong>
            </c:when>
            <c:otherwise>
                <a href="${context}/account/groups?id=${id}">
                    <button type="submit" name="dialogs"><fmt:message key="menu.groups"/></button>
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>