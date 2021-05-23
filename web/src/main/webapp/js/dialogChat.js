var stompClient = null;
var context;
var sessionUserId;
var storedMessages;
var dialogId;

function initValues(contextAttr, sessionUserIdAttr, storedMessagesAttr, dialogIdAttr) {
    context = contextAttr;
    sessionUserId = '' + sessionUserIdAttr;
    storedMessages = JSON.parse(storedMessagesAttr);
    dialogId = dialogIdAttr;
}

function drawStoredMessages() {
    storedMessages.forEach((m) => {
        showMessage(m)
    });
    storedMessages = null;
}

$(document).ready(function() {
    connect();
    drawStoredMessages();
    $("#send").click(function() {
        sendMessage();
    });
});

function connect() {
    var socket = new SockJS(context + '/connection');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // console.log('trying to subscribe: ' + context + '/dialog/messages?id=' + dialogId)
        // stompClient.subscribe(context + '/dialog/messages?id=' + dialogId, function (message) {
        console.log('trying to subscribe: /dialog/messages')
        stompClient.subscribe('/dialog/messages', function (message) {
            console.log('subscribed on ' + context + '/dialog/messages' );
            var messageBody = JSON.parse(message.body);
            showMessage(messageBody);
        });
    });
}

function sendMessage() {
    var file = $("#image").prop('files')[0];
    if (file !== undefined) {
        const reader = new FileReader();
        var strFile = "";
        reader.addEventListener('load', (event) => {
            strFile = btoa(event.target.result);
            console.log(strFile);
        });
        reader.addEventListener('loadend', (event) => {
            sendOverStomp(strFile);
        });
        reader.readAsBinaryString(file);
    } else {
        sendOverStomp("");
    }
}

function sendOverStomp(file) {
    stompClient.send(context + "/message/dialog/add", {}, JSON.stringify({
        'text': $("#text").val(),
        'image' : file,
        'authorId': $("#requesterId").val(),
        'dialogId' : dialogId
    }))
}

function showMessage(messageBody) {
    var newMessageDiv = $("<div class='wallMessage' id='" + messageBody.id + "'>");
    if (sessionUserId === messageBody.authorId) {
        newMessageDiv.prop('style', 'margin-left: 40%;');
    } else {
        newMessageDiv.prop('style', 'margin-right: 40%;');
    }

    var messageHeaderDiv = $('<div>');
    messageHeaderDiv.append('<p>DATETOFIX: ' + messageBody.stringPosted + '</p>');
    messageHeaderDiv.append("<p>AUTHORFIX: <a href='" + context + "/account/wall?id=" + messageBody.author + "'>" +
        messageBody.authorName + " " + messageBody.authorSurname + "</a></p>");
    newMessageDiv.append(messageHeaderDiv);

    var messageContentDiv = $('<div>');
    messageContentDiv.append('<p>' + messageBody.text + '</p>');
    if (messageBody.image !== "") {
        messageContentDiv.append("<img src='data:image/jpeg;base64, /9j/" + messageBody.image + "'>");
    }
    newMessageDiv.append(messageContentDiv);


    $("#messagesShow").append(newMessageDiv);
}