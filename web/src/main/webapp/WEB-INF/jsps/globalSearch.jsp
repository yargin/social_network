<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="label"/>
<c:set var="context" value="${pageContext.servletContext.contextPath}"/>

<script src="https://code.jquery.com/jquery-2.2.4.js"></script>

<common:layout>
    <div id="results"></div>

    <script>
        const notFound = '<fmt:message key="label.nothingFound"/>';
        const user = '<fmt:message key="label.user"/>' + ' : ';
        const group = '<fmt:message key="label.group"/>' + ' : ';
        const context = '${context}';

        jQuery(document).ready(function () {
            $('#searchString').val("${searchString}");
            getRequest();
            $('#search').submit(() => getRequest());
        });

        function getRequest(page) {
            if (page === undefined) {
                page = 1;
            }
            $.ajax({
                url: context + '/find?string=' + $('#searchString').val() + '&page=' + page,
                dataType: 'json',
                success: function (result) {
                    drawResults(result, page);
                },
                fail: function () {
                    $(location).attr('href', context + '/account/wall');
                }
            });
            return false;
        }

        function drawResults(results, page) {
            var res = $('#results');
            res.empty();
            if (results.searchAbles.length === 0) {
                res.append("<div>" + notFound + "</div>");
            }

            results.searchAbles.forEach(e => {
                appendLink(e, res);
            });

            var pages = results.pages;
            pages.forEach(e => {
                if (e !== page) {
                    res.append("<a href=# onclick='getRequest(" + e + ")'>" + e + "</a> ");
                } else if (pages.length > 1) {
                    res.append(e + ' ');
                }
            });
        }

        function appendLink(e, res) {
            var link;
            if (e.type === 'ACCOUNT') {
                link = context + '/account/wall?id=' + e.id;
                res.append("<a href='" + link + "'>" + user + e.name + "</a><br>");
            } else {
                link = context + '/group/wall?id=' + e.id;
                res.append("<a href='" + link + "'>" + group + e.name + "</a><br>");
            }
        }
    </script>
</common:layout>
