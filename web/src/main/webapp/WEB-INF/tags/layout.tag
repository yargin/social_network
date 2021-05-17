<%@ tag description="" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="label"/>

<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <link href="${context}/css/style.css"
          type="text/css" rel="stylesheet">
    <meta charset="UTF-8">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="./jqueryui_1.12.1/jquery-ui.css">

    <%--===================================================================--%>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function () {
            $("#dialog-1").dialog({
                autoOpen: false,
                modal: true,
                buttons: {
                    Ok: function () {
                        alert("you pressed ok");
                        $(this).dialog("close");
                    },
                    Cancel: function () {
                        alert("you pressed Cancel");
                        $(this).dialog("close");
                    }
                },
                title: "Success",
                position: {
                    my: "middle top",
                    at: "middle top"
                }
            });
            $("#dialog-1.ui-dialog-titlebar").hide();
            $("#opener").click(function () {
                $("#dialog-1").dialog("open");
            });
        });
    </script>
    <style>
        .ui-widget-header {
            /*background:wheat !important;*/
            /*border: 2px solid black;*/
            /*display: flex;*/
            /*justify-content: space-around;*/
            /*color: red;*/
            /*font-weight: bold;*/
            /*padding: 3px;*/
            height: 0px;
        }

        .ui-widget-content.ui-dialog {
            margin-top: 5%;
            background: wheat !important;
            border: 2px solid black;
            padding: 3px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
        }

        .ui-dialog-buttonpane {
            text-align: center;
            float: none !important;
            /*display: flex;*/
            /*justify-content: space-around;*/
            /*flex-direction: row;*/
            /*align-items: center;*/
            padding: 3px;
            border: 2px solid black;
        }
    </style>
    <%--    ===================================================================--%>
</head>
<body>

<div id="dialog-1"
     title="Dialog Title goes here...">This my first jQuery UI Dialog!
</div>
<button id="opener">BUTTON</button>

<div class="wrapper">
    <div class="header">
        <img src="${context}/img/java.png" id="logo">
        <h1><fmt:message key="header"/></h1>
        <div style="min-width: 300px;">
            <c:if test="${not empty sessionScope.user}">
                <fmt:message key="layout.welcome"/>
                <a href="${context}/account/wall">${sessionScope.user.name}</a>
                <form action="${context}/search" id="search">
                    <input type="text" name="searchString" id="searchString"
                           placeholder="<fmt:message key="button.searchHolder"/>">
                    <button type="submit" id="searchButton"><fmt:message key="button.search"/></button>
                </form>
            </c:if>
        </div>
    </div>
</div>
<div class="wrapper">
    <div class="menu">
        <ul>
            <li><a href="${context}/account/wall"><fmt:message key="menu.homepage"/></a></li>
            <li><a href="${context}/account/friends"><fmt:message key="menu.friends"/></a></li>
            <li><a href="${context}/account/dialogs"><fmt:message key="menu.messages"/></a></li>
            <li><a href="${context}/account/groups"><fmt:message key="menu.groups"/></a></li>
            <li><a href="#"><fmt:message key="menu.settings"/></a></li>
            <li><a href="${context}/logout"><fmt:message key="menu.quit"/></a></li>
        </ul>
    </div>
    <div class="content">
        <jsp:doBody/>
    </div>
</div>
<div class="wrapper">
    <div class="footer">
        <p><fmt:message key="footer"/> 2020</p>
    </div>
</div>
</body>
</html>