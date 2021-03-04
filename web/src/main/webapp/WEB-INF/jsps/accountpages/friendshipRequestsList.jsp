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
    <div class="post">
        <c:forEach items="${requesters}" var="requester">
            <form action="${context}/friendship/accept" method="post">
                <a href="${context}/account/wall?id=${requester.getId()}">
                        ${requester.getName()} ${requester.getSurname()}
                </a>
                <input type="hidden" value="${requester.getId()}" name="requesterId">
                <input type="hidden" value="${user.getId()}" name="receiverId">
                <button type="submit" name="accept" value="true"><fmt:message key="button.accept"/></button>
                <button type="submit" name="accept" value="false"><fmt:message key="button.decline"/></button>
            </form>
            <br>
        </c:forEach>
    </div>

</common:layout>