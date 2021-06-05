const notificationDiv = $("#notification");

$(function () {
    notificationDiv.dialog({
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
    alert("hiding");
    notificationDiv.hide();
});

function callNotification() {
    notificationDiv.dialog('open');
}