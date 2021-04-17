package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.messages.DialogMessageDaoTx;
import org.springframework.stereotype.Repository;

@Repository("dialogMessageDao")
public class DialogMessageDao extends AbstractTxDelegateDao<DialogMessage> {
    public DialogMessageDao(DialogMessageDaoTx dialogMessageDaoTx) {
        super(dialogMessageDaoTx);
    }
}
