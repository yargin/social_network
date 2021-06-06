var execContext;

function initExec(contextAddr) {
    execContext = contextAddr;
}

function tryToExecute(targetUrl) {
    $.ajax({
        url: targetUrl,
        dataType: 'text',
        success: function (successString) {
            if (successString === "") {
                callNotification();
            } else {
                $(location).attr('href', execContext + successString);
            }
        },
        fail: function () {
            $(location).attr('href', execContext + '/account/wall');
        }
    });
}