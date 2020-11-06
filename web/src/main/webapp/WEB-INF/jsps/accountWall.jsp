<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <common:accountInfo/>
    <c:if test="${not empty owner or not empty admin}">
        <div>
            <a href="${context}/updateInfo?id=${id}"><fmt:message key="label.updateInfo"/></a><br>
            <a href="${context}/deleteAccount?id=${id}"><fmt:message key="label.deleteAccount"/></a>
        </div>
    </c:if>
    <div>
        <c:if test="${not empty friend or not empty admin or not empty owner}">
            <a href="#">
                <button type="submit" name="wall">Wall</button>
            </a>
        </c:if>
        <c:if test="${empty friend and empty owner}">
            <a href="${context}/addfriend?id1=${sessionScope.userId}&id2=${id}">
                <button type="submit" name="addFriend">Add to friends</button>
            </a>
        </c:if>
        <c:if test="${not empty admin or not empty owner}">
            <a href="#">
                <button type="submit" name="requests">Friendship requests</button>
            </a>
        </c:if>
        <c:if test="${not empty friend or not empty admin and empty owner}">
            <a href="#">
                <button type="submit" name="message">Private message</button>
            </a>
        </c:if>
        <c:if test="${not empty admin or not empty owner or not empty friend}">
            <a href="#">
                <button type="submit" name="friends">Friends list</button>
            </a>
        </c:if>
        <c:if test="${not empty admin or not empty owner}">
            <a href="#">
                <button type="submit" name="dialogs">Private dialogs</button>
            </a>
        </c:if>
    </div>

    <%--        <c:forEach items="${posts}" var="post">--%>
    <%--            <div class="post">--%>
    <%--                <common:wallMessage/>--%>
    <%--            </div>--%>
    <%--        </c:forEach>--%>
</common:layout>
