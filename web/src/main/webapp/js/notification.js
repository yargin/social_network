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
    $("#notification").hide();
});