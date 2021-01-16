var tooShortError;
var tooLongError;
var notPhoneError;
var duplicateError;
var deleteText;

var privatePhones = [];
var workPhones = [];
var deleteListeners = new Map();
var blurListeners = new Map();
const ERROR = 'Error';
const DELETE = 'Delete';

function confirmation(confirmMessage) {
    if (submit()) {
        return confirm(confirmMessage);
    }
    return false;
}

function init(deleteButtonTex, tooShortErr, tooLongErr, notPhoneErr, duplicateErr) {
    deleteText = deleteButtonTex;
    tooShortError = tooShortErr;
    tooLongError = tooLongErr;
    notPhoneError = notPhoneErr;
    duplicateError = duplicateErr;
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
    //todo add regular expression
    return true;
}

function changePhone(elementId) {
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

    deleteButton.removeEventListener('click', deleteListeners.get(elementId));
    deleteListeners.delete(elementId);
    addDeleteListener(deleteButton, value);

    newPhone.setAttribute('id', value);
    newPhone.setAttribute('value', value);
    newPhone.removeEventListener('blur', blurListeners.get(elementId));
    blurListeners.delete(elementId);
    addBlurListener(newPhone, value);

    var replaced = replaceValue(elementId, value, privatePhones);
    if (!replaced) {
        replaceValue(elementId, value, workPhones);
    }
}

function replaceValue(oldValue, newValue, array) {
    var size = array.length;
    for (var i = 0; i < size; i++) {
        if (array[i] === oldValue) {
            array[i] = newValue;
            return true;
        }
    }
    return false;
}

function addDeleteListener(button, value) {
    var listener = function () {
        deletePhone(value);
    };
    deleteListeners.set(value, listener);
    button.addEventListener('click', listener);
}

function addBlurListener(input, value) {
    var listener = function () {
        changePhone(value);
    };
    blurListeners.set(value, listener);
    input.addEventListener('blur', listener);
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

    addBlurListener(inputtedPhone, value);

    var phonesList = document.getElementById(listId);
    var newPhoneDiv = document.getElementById(divId);
    phonesList.insertBefore(inputtedPhone, newPhoneDiv);

    var deletePhoneButton = createDeleteButton(value);
    phonesList.insertBefore(deletePhoneButton, newPhoneDiv);

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

function createDeleteButton(value) {
    var deletePhoneButton = document.createElement('button');
    deletePhoneButton.setAttribute('id', value + DELETE);
    deletePhoneButton.setAttribute('type', 'button');

    addDeleteListener(deletePhoneButton, value);

    deletePhoneButton.textContent = deleteText;
    return deletePhoneButton;
}

function deletePhone(valueToDelete) {
    privatePhones = privatePhones.filter(function (value, index, arr) {
        return value !== valueToDelete;
    });
    workPhones = workPhones.filter(function (value, index, arr) {
        return value !== valueToDelete;
    });
    document.getElementById(valueToDelete).remove();
    document.getElementById(valueToDelete + DELETE).remove();
    document.getElementById(valueToDelete + ERROR).remove();
}

function submit() {
    var checked = checkForErrorAddName(privatePhones, 'privatePhone');
    if (!checked) {
        return false;
    }
    checked = checkForErrorAddName(workPhones, 'workPhone');
    return checked;
}

function checkForErrorAddName(phones, type) {
    for (var i = 0; i < phones.length; i++) {
        var errorDiv = document.getElementById(phones[i] + ERROR);
        if (errorDiv.firstChild != null && errorDiv.firstChild.textContent !== '') {
            document.getElementById(phones[i]).focus();
            return false;
        }
        document.getElementById(phones[i]).setAttribute('name', type + i);
    }
    return true;
}