package com.getjavajob.training.yarginy.socialnetwork.common.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

public abstract class NullEntitiesFactory {
    private NullEntitiesFactory() {
    }

    public static Account getNullAccount() {
        Account nullAccount = new Account();
        nullAccount.setId(-1);
        nullAccount.setName("doesn't exist");
        nullAccount.setSurname("doesn't exist");
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
        Message message = new Message();
        message.setAuthor(getNullAccount());
        return message;
    }

    public static Dialog getNullDialog() {
        Dialog dialog = new Dialog();
        dialog.setId(-1);
        dialog.setFirstAccount(getNullAccount());
        dialog.setSecondAccount(getNullAccount());
        return dialog;
    }
}
