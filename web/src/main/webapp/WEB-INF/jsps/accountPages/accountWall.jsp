<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<common:layout>
    <common:accountInfo/>
    <common:accountMenu/>

    <div class="message">
        <div>
            <p> date: 01-01-2001</p>
            <p> author: <a href="#">User</a></p>
                <%--    </div>--%>
                <%--    <div class="post">--%>
        </div>
        <div>
            <p>
                <img src="${context}/img/emptyAvatar.jpg">
                asdasdddddddasdasdasdasdasd
                asdasdasdasdasdasdasdasd
                asdasdasdasdasdasd
                asdasdasdasdasd
                asdasdasddddddddddddddddddddd
                aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            </p>
        </div>
    </div>
</common:layout>
