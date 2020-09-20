<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="errors"/>
<%@ attribute name="error" %>

<c:if test="${not empty error}">
    <p id="error">
        <fmt:message key="${error}"/>
    </p>
</c:if>
