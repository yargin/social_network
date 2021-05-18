$(function () {
    $("#notification").dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        },
        position: {
            my: "middle top",
            at: "middle top"
        }
    });

    $("#notification.ui-dialog-titlebar").hide();
});

function showNotification() {
    $("#notification").dialog("open");
}