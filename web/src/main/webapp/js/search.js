var notFound;
var userLabel;
var groupLabel;
var context;
var searchString;

jQuery(document).ready(function () {
    $('#searchString').val(searchString);
    search();
    $('#search').submit(() => search());
});

function initSearchScript(notFoundLabel, userLabelText, groupLabelText, contextAddr, searchParam) {
    notFound = notFoundLabel;
    userLabel = userLabelText;
    groupLabel = groupLabelText;
    context = contextAddr;
    searchString = searchParam;
}

function search(page) {
    if (page === undefined) {
        page = 1;
    }
    var searchString = $('#searchString').val();
    $.ajax({
        url: `${context}/find?string=${searchString}&page=${page}`,
        dataType: 'json',
        success: function (result) {
            drawResults(result, page);
        },
        fail: function () {
            $(location).attr('href', `${context}/account/wall`);
        }
    });
    return false;
}

function drawResults(result, page) {
    var res = $('#results');
    res.empty();
    if (result.searchAbles.length === 0) {
        res.append(`<div>${notFound}</div>`);
    }

    result.searchAbles.forEach(e => {
        appendLink(e, res);
    });

    var pages = result.pages;
    pages.forEach(e => {
        if (e !== page) {
            res.append(`<a href=# onclick="${search(e)}">${e}</a>`);
        } else if (pages.length > 1) {
            res.append(e + ' ');
        }
    });
}

function appendLink(e, res) {
    var link;
    if (e.type === 'ACCOUNT') {
        link = `${context}/account/wall?id=${e.id}`;
        res.append(`<a href="${link}">${userLabel} : ${e.name}</a><br>`);
    } else {
        link = `${context}/group/wall?id=${e.id}`;
        res.append(`<a href="${link}">${groupLabel} : ${e.name}</a><br>`);
    }
}
