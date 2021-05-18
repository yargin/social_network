import * as notification from './notification.js'

var execContext;
var failMessage;
var confirmationMessage;

function initExec(contextAddr, failErr, confirmationMess) {
    execContext = contextAddr;
    failMessage = failErr;
    confirmationMessage = confirmationMess;
}

function tryToExecute(targetUrl) {
    $.ajax({
        url: targetUrl,
        dataType: 'text',
        success: function (successUrl) {
            if (successUrl === "") {
                notification.showNotification();
            } else {
                $(location).attr('href', execContext + successUrl);
            }
        },
        fail: function () {
            $(location).attr('href', execContext + '/account/wall');
        }
    });
}