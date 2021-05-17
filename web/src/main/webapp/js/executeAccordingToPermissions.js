var execContext;
var failMessage;
var confirmationMessage;

function initExec(contextAddr, failErr, confirmationMess) {
    execContext = contextAddr;
    failMessage = failErr;
    confirmationMessage = confirmationMess;
}

function tryToExecute(targetUrl) {
    if (confirm(confirmationMessage)) {
        $.ajax({
            url: targetUrl,
            dataType: 'text',
            success: function (successUrl) {
                if (successUrl === "") {
                    alert(failMessage);
                } else {
                    $(location).attr('href', execContext + successUrl);
                }
            },
            fail: function () {
                $(location).attr('href', execContext + '/account/wall');
            }
        });
    }
}