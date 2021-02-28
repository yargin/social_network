function confirmation(confirmMessage, innerChecks) {
    // alert('in confirmation function' + innerChecks);
    if (innerChecks !== undefined) {
        if (innerChecks) {
            return confirm(confirmMessage);
        }
        return false;
    }
    return false;
}