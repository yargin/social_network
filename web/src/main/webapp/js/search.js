var notFound;
var user;
var group;
var context;
var searchString;

jQuery(document).ready(function () {
    $('#searchString').val(searchString);
    getRequest();
    $('#search').submit(() => getRequest());
});

function init(notFoundLabel, userLabel, groupLabel, contextAddr, searchParam) {
    notFound = notFoundLabel;
    user = userLabel;
    group = groupLabel;
    context = contextAddr;
    searchString = searchParam;
}

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