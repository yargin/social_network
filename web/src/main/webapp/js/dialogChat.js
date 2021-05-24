var stompClient = null;
var context;
var sessionUserId;
var storedMessages;
var dialogId;
var dateLabel;
var authorLabel;

function initValues(contextAttr, sessionUserIdAttr, storedMessagesAttr, dialogIdAttr, dateText, authorText) {
    context = contextAttr;
    sessionUserId = '' + sessionUserIdAttr;
    storedMessages = JSON.parse(storedMessagesAttr);
    dialogId = dialogIdAttr;
    dateLabel = dateText;
    authorLabel = authorText;
}

function drawStoredMessages() {
    storedMessages.forEach((m) => {
        showMessage2(m)
    });
    storedMessages = null;
}

$(document).ready(function() {
    connect();
    drawStoredMessages();
    $('#send').click(function () {
        sendMessage();
    });
});

function connect() {
    var socket = new SockJS(context + '/connection');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/dialog/messages?id=' + dialogId, function (message) {
            var messageBody = JSON.parse(message.body);
            showMessage(messageBody);
        });
    });
}

function sendMessage() {
    var imageInput = $('#image');
    var file = imageInput.prop('files')[0];
    if (file !== undefined) {
        const reader = new FileReader();
        var strFile = '';
        reader.addEventListener('load', (event) => {
            strFile = btoa(event.target.result);
        });
        reader.addEventListener('loadend', (event) => {
            sendOverStomp(strFile);
        });
        reader.readAsBinaryString(file);
    } else {
        sendOverStomp('');
    }
    $('#text').val('');
    imageInput.val('');
}

function sendOverStomp(file) {
    stompClient.send(context + '/message/dialog/add', {}, JSON.stringify({
        'text': $('#text').val(),
        'image': file,
        'authorId': $("#requesterId").val(),
        'dialogId': dialogId
    }))
}

function showMessage(message) {
    var newMessageDiv = $(`<div class="wallMessage" id="${message.id}">`);
    if (sessionUserId === message.authorId) {
        newMessageDiv.prop('style', 'margin-right: 40%;');
    } else {
        newMessageDiv.prop('style', 'margin-left: 40%;');
    }

    var messageHeaderDiv = $('<div>');
    messageHeaderDiv.append(`<p>${dateLabel}: ${message.stringPosted}</p>`);
    messageHeaderDiv.append(`<p>${authorLabel}: <a href="` + context +
        `/account/wall?id=${message.authorId}">${message.authorName} ${message.authorSurname}</a></p>`);
    newMessageDiv.append(messageHeaderDiv);

    var messageContentDiv = $('<div>');
    messageContentDiv.append(`<p>${message.text}</p>`);
    if (message.image !== '') {
        messageContentDiv.append(`<img src="data:image/jpeg;base64, ${message.image}">`);
    }
    newMessageDiv.append(messageContentDiv);

    $('#messagesShow').prepend(newMessageDiv);
}

function showMessage2(message) {
    var newMessageDiv = $('#template').content.clone(true);
    newMessageDiv.prop('id').val(message.authorId);
    if (sessionUserId === message.authorId) {
        newMessageDiv.prop('style', 'margin-right: 40%;');
    } else {
        newMessageDiv.prop('style', 'margin-left: 40%;');
    }
    $('#dateLabel').append(message.stringPosted);
    $('#authorLabel').append(message.authorName + ' ' + message.authorSurname);
    $('#authorLink').prop('href').append(message.authorId);
    $('#messageText').append(message.text);
    $('#messageImage').append(message.image);

    $('#messagesShow').prepend(newMessageDiv);
}