<% String error = (String) request.getAttribute("error"); %><p>${error}</p>
<form action="${pageContext.servletContext.contextPath}/login" method="post">
    <input type="email" name="email" placeholder="email" required>
    <input type="password" name="password" placeholder="password" required>
    <button type="submit">Login</button>
    <p>not registered? <a href="${pageContext.servletContext.contextPath}/registration">register</a>
    </p>
</form>