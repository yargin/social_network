package com.getjavajob.training.yarginy.socialnetwork.common.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;

public abstract class NullModelsFactory {
    private NullModelsFactory() {
    }

    public static Account getNullAccount() {
        Account nullAccount = new Account(-1);
        nullAccount.setName("doesn't exist");
        nullAccount.setSurname(null);
        nullAccount.setEmail("email@doesnt.exist");
        return nullAccount;
    }

    public static Group getNullGroup() {
        Group nullGroup = new Group();
        nullGroup.setId(-1);
        nullGroup.setName("group doesn't exist");
        nullGroup.setOwner(getNullAccount());
        return nullGroup;
    }

    public static Phone getNullPhone() {
        Phone nullPhone = new Phone();
        nullPhone.setNumber("000000");
        nullPhone.setOwner(getNullAccount());
        return nullPhone;
    }

    public static Password getNullPassword() {
        Password nullPassword = new Password();
        nullPassword.setStringPassword("nullPassword0");
        nullPassword.setAccount(getNullAccount());
        return nullPassword;
    }

    public static Message getNullMessage() {
        Message message = new AccountWallMessage();
        message.setAuthor(getNullAccount());
        return message;
    }

    public static AccountWallMessage getNullAccountWallMessage() {
        AccountWallMessage accountWallMessage = new AccountWallMessage();
        accountWallMessage.setAuthor(getNullAccount());
        return accountWallMessage;
    }

    public static DialogMessage getNullDialogMessage() {
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setAuthor(getNullAccount());
        return dialogMessage;
    }

    public static GroupWallMessage getNullGroupWallMessage() {
        GroupWallMessage groupWallMessage = new GroupWallMessage();
        groupWallMessage.setAuthor(getNullAccount());
        return groupWallMessage;
    }

    public static Dialog getNullDialog() {
        Dialog dialog = new Dialog();
        dialog.setId(-1);
        dialog.setFirstAccount(getNullAccount());
        dialog.setSecondAccount(getNullAccount());
        return dialog;
    }
}
