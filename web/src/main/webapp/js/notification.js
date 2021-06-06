var onLoad;

function callOnLoad() {
    onLoad = true;
}

$(function () {
    let notificationDiv = $('#notification');
    notificationDiv.dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog('close');
            }
        },
        position: {
            my: 'middle top',
            at: 'middle top'
        }
    });
    if (onLoad) {
        callNotification(notificationDiv);
    } else {
        notificationDiv.hide();
    }
});

function callNotification(notificationDiv) {
    notificationDiv.dialog('open');
}