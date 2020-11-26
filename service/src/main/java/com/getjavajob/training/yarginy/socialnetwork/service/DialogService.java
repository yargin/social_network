package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;

import java.util.Collection;

public interface DialogService {
    Dialog get(long dialogId);

    Dialog get(Dialog dialog);

    boolean create(Dialog dialog, Message message);

    boolean delete(Dialog dialog);

    Dialog getByTalkers(long firstAccountId, long secondAccountId);

    boolean isTalker(long accountId, long dialogId);

    Collection<Dialog> getAccountDialogs(long accountId);

    Dialog getNullDialog();
}
