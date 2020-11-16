<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<div class="wallMessage">
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
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        </p>
    </div>
</div>