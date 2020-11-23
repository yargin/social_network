package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;

import java.util.Collection;

public interface DialogService {
    Dialog select(long dialogId);

    Dialog select(Dialog dialog);

    boolean create(Dialog dialog, Message message);

    boolean delete(Dialog dialog);

    Dialog selectByAccounts(long firstAccountId, long secondAccountId);

    boolean isTalker(long accountId, long dialogId);

    Collection<Dialog> getAccountDialogs(long accountId);

    Dialog getNullDialog();
}
