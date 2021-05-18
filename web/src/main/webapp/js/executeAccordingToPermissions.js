var execContext;
var failMessage;
var confirmationMessage;

function initExec(contextAddr, failErr, confirmationMess) {
    execContext = contextAddr;
    failMessage = failErr;
    confirmationMessage = confirmationMess;
}

function tryToExecute(targetUrl) {
    //add dialog here
    alert("here");
    $.ajax({
        url: targetUrl,
        dataType: 'text',
        success: function (successUrl) {
            if (successUrl === "") {
                $("#notification").dialog("open");
            } else {
                $(location).attr('href', execContext + successUrl);
            }
        },
        fail: function () {
            $(location).attr('href', execContext + '/account/wall');
        }
    });
}