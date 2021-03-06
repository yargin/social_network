package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;

import java.io.Serializable;

public interface DialogService extends Serializable {
    Dialog get(long dialogId);

    Dialog get(Dialog dialog);

    boolean create(Dialog dialog, DialogMessage message);

    boolean delete(Dialog dialog);

    Dialog getByTalkers(long firstAccountId, long secondAccountId);

    boolean isTalker(long accountId, long dialogId);

    Dialog getNullDialog();
}
