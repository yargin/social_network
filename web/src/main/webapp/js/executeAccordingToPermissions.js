var execContext;

function initExec(contextAddr) {
    execContext = contextAddr;
}

function tryToExecute(targetUrl) {
    //add dialog here
    $.ajax({
        url: targetUrl,
        dataType: 'text',
        success: function (successString) {
            if (successString === "") {
                $("#notification").dialog("open");
            } else {
                $(location).attr('href', execContext + successString);
            }
        },
        fail: function () {
            $(location).attr('href', execContext + '/account/wall');
        }
    });
}