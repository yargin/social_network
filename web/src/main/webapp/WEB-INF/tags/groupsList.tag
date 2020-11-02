<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<c:forEach items="${groups}" var="group">
    <a href="${context}/group?id=${group.id}">${group.name}</a><br>
</c:forEach>