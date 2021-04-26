package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.messages.DialogMessageDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("dialogMessageDao")
public class DialogMessageDao extends DelegateDaoTx<DialogMessage> {
    public DialogMessageDao(DialogMessageDaoTransactional dialogMessageDaoTx) {
        super(dialogMessageDaoTx);
    }
}
