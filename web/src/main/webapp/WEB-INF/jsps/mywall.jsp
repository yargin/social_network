<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<common:layout>
    <c:forEach items="${posts}" var="post">
        <div class="post">
            <common:wallmessage/>
        </div>
    </c:forEach>
</common:layout>
