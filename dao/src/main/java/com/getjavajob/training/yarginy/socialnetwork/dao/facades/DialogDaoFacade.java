package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;

import java.io.Serializable;
import java.util.Collection;

public interface DialogDaoFacade extends Serializable {
    Dialog select(long id);

    Dialog select(Dialog dialog);

    Dialog getNullEntity();

    boolean create(Dialog dialog);

    boolean update(Dialog dialog, Dialog storedDialog);

    boolean delete(Dialog dialog);

    Collection<Dialog> selectAll();

    Collection<Dialog> selectDialogsByAccount(long accountId);

    boolean isTalker(long accountId, long dialogId);
}
