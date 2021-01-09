function confirmation(confirmMessage) {
    return confirm(confirmMessage);
}

function init(deleteText) {
    document.deleteText = deleteText;
}

//array
var privatePhones;
const ERROR = 'Error';
const DELETE = 'Delete';
const BR = 'br';

function addNewPrivatePhone() {
    //todo inline doesn't work WHY??
    var checked = checkPhone('newPrivatePhone');
    if (checked) {
        addPrivatePhone(document.getElementById('newPrivatePhone').value, "");
        document.getElementById('newPrivatePhone').value = "";
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
        errorDiv.appendChild(document.createTextNode('too short'));
        return false;
    }
    if (value.length > 13) {
        errorDiv.appendChild(document.createTextNode('too long'));
        return false;
    }
    if (privatePhones.includes(value)) {
        errorDiv.appendChild(document.createTextNode('already exists in list'));
        return false;
    }
    return true;
}

function validatePhone(elementId) {
    if (!checkPhone(elementId)) {
        return;
    }
    var newPrivatePhone = document.getElementById(elementId);
    var value = newPrivatePhone.value;
    var errorDiv = document.getElementById(elementId + ERROR);
    errorDiv.setAttribute('id', value + ERROR);

    var deleteButton = document.getElementById(elementId + DELETE);
    deleteButton.setAttribute('id', value + DELETE);
    deleteButton.addEventListener('click', function () {
        deletePrivatePhone(value);
    });
    document.getElementById(elementId).setAttribute('id', value + BR);


    privatePhones = privatePhones.filter(function (oldValue, index, arr) {
        return oldValue !== elementId;
    })
    newPrivatePhone.setAttribute('id', value);
    newPrivatePhone.setAttribute('value', value);
    privatePhones.push(value);
}

//add phone to array & to page
function addPrivatePhone(value, error) {
    if (typeof privatePhones === 'undefined') {
        privatePhones = [value];
    } else {
        privatePhones.push(value);
    }

    //add to list new phone
    var inputtedPhone = document.createElement('input');
    inputtedPhone.setAttribute('id', value);
    inputtedPhone.setAttribute('value', value);
    inputtedPhone.setAttribute('type', 'text');
    inputtedPhone.addEventListener('blur', function () {
        validatePhone(inputtedPhone.getAttribute('id'));
    });


    var phonesList = document.getElementById('privatePhonesList');
    var newPrivatePhoneDiv = document.getElementById('newPrivatePhoneDiv');
    phonesList.insertBefore(inputtedPhone, newPrivatePhoneDiv);

    var deletePhoneButton = document.createElement('button');
    var deleteText = document.createTextNode(document.deleteText);
    deletePhoneButton.setAttribute('id', value + DELETE);
    deletePhoneButton.setAttribute('type', 'button');
    deletePhoneButton.addEventListener('click', function () {
        deletePrivatePhone(value);
    });
    deletePhoneButton.appendChild(deleteText);
    phonesList.insertBefore(deletePhoneButton, newPrivatePhoneDiv);
    var br = document.createElement(BR);
    br.setAttribute('id', value + BR);
    phonesList.insertBefore(br, newPrivatePhoneDiv);

    //create error
    if (typeof error === 'undefined') {
        error = "";
    }
    var errorText = document.createTextNode(error);
    var errorDiv = document.createElement('div');
    errorDiv.appendChild(errorText);
    errorDiv.setAttribute('id', value + ERROR);
    phonesList.insertBefore(errorDiv, newPrivatePhoneDiv);
}

function deletePrivatePhone(valueToDelete) {
    privatePhones = privatePhones.filter(function (value, index, arr) {
        return value !== valueToDelete;
    })
    document.getElementById(valueToDelete).remove();
    document.getElementById(valueToDelete + DELETE).remove();
    document.getElementById(valueToDelete + ERROR).remove();
    document.getElementById(valueToDelete + BR).remove();
}
