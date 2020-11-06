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
            <div class="post">
                <a href="${context}/mywall?id=${requester.getId()}">
                        ${requester.getName()} ${requester.getSurname()}
                </a>
                <a href="#">
                    <button name="accept" value="true"><fmt:message key="button.accept"/></button>
                </a>
                <a href="#">
                    <button name="accept" value="false"><fmt:message key="button.decline"/></button>
                </a>
            </div>
        </c:forEach>
    </div>
</common:layout>