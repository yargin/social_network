var tooShortError;
var tooLongError;
var notPhoneError;
var duplicateError;

var privatePhones = [];
var workPhones = [];
const ERROR = 'Error';
const DELETE = 'Delete';
const BR = 'br';

function confirmation(confirmMessage) {
    if (submit()) {
        return confirm(confirmMessage);
    }
    return false;
}

function init(deleteText, tooShortErr, tooLongErr, notPhoneErr, duplicateErr) {
    document.deleteText = deleteText;
    document.tooShortError = tooShortErr;
    document.tooLongError = tooLongErr;
    document.notPhoneError = notPhoneErr;
    document.duplicateError = duplicateErr;
}

function addNewPhone(type) {
    var newPhoneId = type === 'private' ? 'newPrivatePhone' : 'newWorkPhone';
    var checked = checkPhone(newPhoneId);
    if (checked) {
        addPhone(document.getElementById(newPhoneId).value, "", type);
        document.getElementById(newPhoneId).value = "";
    }
}

function checkPhone(elementId) {
    var newPrivatePhone = document.getElementById(elementId);
    var value = newPrivatePhone.value;
    var errorDiv = document.getElementById(elementId + ERROR);
    while (errorDiv.firstChild) {
        errorDiv.removeChild(errorDiv.firstChild);
    }

    if (value.length < 3) {
        errorDiv.appendChild(document.createTextNode(tooShortError));
        return false;
    }
    if (value.length > 13) {
        errorDiv.appendChild(document.createTextNode(tooLongError));
        return false;
    }
    if (privatePhones.includes(value) || workPhones.includes(value)) {
        errorDiv.appendChild(document.createTextNode(duplicateError));
        return false;
    }
    return true;
}

function changePhone(elementId, type) {
    var newPhone = document.getElementById(elementId);
    var value = newPhone.value;

    if (elementId === value) {
        return;
    }

    if (!checkPhone(elementId)) {
        newPhone.focus();
        return;
    }

    //change current field
    var errorDiv = document.getElementById(elementId + ERROR);
    errorDiv.setAttribute('id', value + ERROR);

    var deleteButton = document.getElementById(elementId + DELETE);
    deleteButton.setAttribute('id', value + DELETE);
    deleteButton.addEventListener('click', function () {
        deletePhone(value);
    });
    document.getElementById(elementId + BR).setAttribute('id', value + BR);

    newPhone.setAttribute('id', value);
    newPhone.setAttribute('value', value);

    if (type === 'private') {
        privatePhones = privatePhones.filter(function (oldValue, index, arr) {
            return oldValue !== elementId;
        });
        privatePhones.push(value);
    } else {
        workPhones = workPhones.filter(function (oldValue, index, arr) {
            return oldValue !== elementId;
        });
        workPhones.push(value);
    }
}

//add phone to array & to page
function addPhone(value, error, type) {
    var listId = '';
    var divId = '';
    if (type !== 'work') {
        privatePhones.push(value);
        listId = 'privatePhonesList';
        divId = 'newPrivatePhoneDiv';
    } else {
        workPhones.push(value);
        listId = 'workPhonesList';
        divId = 'newWorkPhoneDiv';
    }

    //add to list new phone
    var inputtedPhone = document.createElement('input');
    inputtedPhone.setAttribute('id', value);
    inputtedPhone.setAttribute('value', value);
    inputtedPhone.setAttribute('type', 'text');
    inputtedPhone.addEventListener('blur', function () {
        changePhone(inputtedPhone.getAttribute('id'), type);
    });
    var phonesList = document.getElementById(listId);
    var newPhoneDiv = document.getElementById(divId);
    phonesList.insertBefore(inputtedPhone, newPhoneDiv);

    var deletePhoneButton = document.createElement('button');
    var deleteText = document.createTextNode(document.deleteText);
    deletePhoneButton.setAttribute('id', value + DELETE);
    deletePhoneButton.setAttribute('type', 'button');
    deletePhoneButton.addEventListener('click', function () {
        deletePhone(value, type);
    });
    deletePhoneButton.appendChild(deleteText);
    phonesList.insertBefore(deletePhoneButton, newPhoneDiv);
    var br = document.createElement(BR);
    br.setAttribute('id', value + BR);
    phonesList.insertBefore(br, newPhoneDiv);

    //create error
    if (typeof error === 'undefined') {
        error = "";
    }
    var errorText = document.createTextNode(error);
    var errorDiv = document.createElement('div');
    errorDiv.appendChild(errorText);
    errorDiv.setAttribute('id', value + ERROR);
    phonesList.insertBefore(errorDiv, newPhoneDiv);
}

function deletePhone(valueToDelete, type) {
    if (type === 'private') {
        privatePhones = privatePhones.filter(function (value, index, arr) {
            return value !== valueToDelete;
        });
    } else {
        workPhones = workPhones.filter(function (value, index, arr) {
            return value !== valueToDelete;
        });
    }
    document.getElementById(valueToDelete).remove();
    document.getElementById(valueToDelete + DELETE).remove();
    document.getElementById(valueToDelete + ERROR).remove();
    document.getElementById(valueToDelete + BR).remove();
}

function submit() {
    // alert('work phones: ' + workPhones + ', private phones: ' + privatePhones);
    let i;
    checkForErrorAddName(privatePhones, 'privatePhone');
    checkForErrorAddName(workPhones, 'workPhone');
    // alert('success');
    return false;
}

function checkForErrorAddName(phones, type) {
    for (i = 0; i < phones.length; i++) {
        var errorDiv = document.getElementById(phones[i] + ERROR);
        if (errorDiv.firstChild != null && errorDiv.firstChild.textContent !== '') {
            document.getElementById(phones[i]).focus();
            return false;
        }
        document.getElementById(phones[i]).setAttribute('name', type + i);
    }
}