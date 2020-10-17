<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:layout/>

<% response.sendRedirect(request.getContextPath() + "/mywall"); %>
