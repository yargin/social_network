<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<common:layout>
    <common:myInfo/>
    <c:forEach items="${posts}" var="post">
        <div class="post">
            <common:wallMessage/>
        </div>
    </c:forEach>
</common:layout>
