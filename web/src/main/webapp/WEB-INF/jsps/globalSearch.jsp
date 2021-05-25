<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<script src="${context}/webjars/jquery/2.2.4/jquery.min.js"></script>
<script src="${context}/js/search.js"></script>
<fmt:message key="label.nothingFound" var="nothingFound"/>
<fmt:message key="label.user" var="user"/>
<fmt:message key="label.group" var="group"/>
<script>
    init('${nothingFound}', '${user}' + ' : ', '${group}' + ' : ', '${context}', '${searchString}');
</script>

<common:layout>
    <div id="results"></div>
</common:layout>
