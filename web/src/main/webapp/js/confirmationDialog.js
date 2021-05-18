var additionalChecks;
var okButtonText;
var cancelButtonText;
var callback;

function initDialog(okButtonLabel, cancelButtonLabel, callbackFunction, additionalChecksFunction) {
    okButtonText = okButtonLabel;
    cancelButtonText = cancelButtonLabel;
    callback = callbackFunction;
    additionalChecks = additionalChecksFunction;
}

$(function () {
    $("#confirmDialog").dialog({
        autoOpen: false,
        modal: true,
        buttons: [{
            text: okButtonText,
            click: function () {
                callback();
                $(this).dialog("close");
            }
        }, {
            text: cancelButtonText,
            click: function () {
                $(this).dialog("close");
            }
        }],
        position: {
            my: "middle top",
            at: "middle top"
        }
    });

    $("#confirmDialog.ui-dialog-titlebar").hide();

    $("#opener").click(function (e) {
        e.preventDefault();
        if (additionalChecks === undefined || additionalChecks()) {
            $("#confirmDialog").dialog("open");
        }
    });
});

function skip() {
    $("#confirmAbleForm").submit();
}