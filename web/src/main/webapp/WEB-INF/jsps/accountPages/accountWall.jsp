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
    admin ${admin}
    <br><br>
    owner ${owner}
    <br><br>
    friend ${friend}
</common:layout>
