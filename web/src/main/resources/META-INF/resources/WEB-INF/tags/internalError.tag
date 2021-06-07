<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="error"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<h2><fmt:message key="error.internalError"/></h2>
<br>
<img src="${context}/img/broken.jpg">
