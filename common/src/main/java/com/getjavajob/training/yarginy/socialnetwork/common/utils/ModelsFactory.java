package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ModelsFactory implements Serializable {
    public Password getPassword(Account account, String stringPassword) {
        return new Password(account, stringPassword);
    }

    public Account getAccount(long id) {
        return new Account(id);
    }

    public Group getGroup(long id) {
        return new Group(id);
    }

    public Dialog getDialog(long id) {
        return new Dialog(id);
    }
}
