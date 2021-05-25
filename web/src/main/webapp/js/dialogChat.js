var stompClient = null;
var context;
var sessionUserId;
var storedMessages;
var dialogId;
var connected;

function initDialogScript(contextAttr, sessionUserIdAttr, storedMessagesAttr, dialogIdAttr) {
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
    $('#send').click(function () {
        sendMessage();
    });
});

function connect() {
    var socket = new SockJS(context + '/connection');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe(`/dialog/messages/add?id=${dialogId}`, function (message) {
            var messageBody = JSON.parse(message.body);
            showMessage(messageBody);
        });
        stompClient.subscribe(`/dialog/messages/delete?id=${dialogId}`, function (message) {
            var messageBody = JSON.parse(message.body);
            deleteMessage(messageBody);
        });
    });
    connected = true;
}

function deleteMessage(message) {
    $(`#${message}`).remove();
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
}

function sendOverStomp(file) {
    var text = $('#text');
    stompClient.send(`${context}/message/dialog/add`, {}, JSON.stringify({
        'text': text.val(),
        'image': file,
        'authorId': $('#requesterId').val(),
        'dialogId': dialogId
    }))
    text.val('');
    $('#image').val('');
}

function showMessage(message) {
    var messageElement = $('#dialogMessage');
    if (sessionUserId === message.authorId) {
        messageElement.prop('style', 'margin-right: 40%;');
        $('#delete').click(function () {
            sendDeleteRequest(message.id);
        });
    } else {
        messageElement.prop('style', 'margin-left: 40%;');
        $('#delete').remove();
    }
    messageElement.prop('id', message.id);
    $('#dateLabel').append(message.stringPosted);
    var authorLink = $('#authorLink');
    authorLink.append(message.authorName + ' ' + message.authorSurname);
    authorLink.prop('href', `${context}/account/wall?id=${message.authorId}`);


    $('#messageText').append(message.text);
    if (message.image !== '') {
        $('#messageImage').prop('src', `data:image/jpeg;base64, ${message.image}`);
    }

    var template = document.querySelector('#template');
    var newMessageDiv = document.importNode(template.content, true);
    $('#messagesShow').prepend(newMessageDiv);
}

function sendDeleteRequest(id) {
    if (connected === true) {
        stompClient.send(`${context}/message/dialog/delete`, {}, id);
    }
}