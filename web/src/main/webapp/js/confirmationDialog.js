var skip = false;

function skipConfirmation() {
    skip = true;
}

function confirmation(confirmMessage, innerChecks) {
    if (skip) {
        return true;
    }
    // alert('in confirmation function' + innerChecks);
    if (innerChecks !== undefined) {
        if (innerChecks) {
            return confirm(confirmMessage);
        }
        return false;
    }
    return false;
}