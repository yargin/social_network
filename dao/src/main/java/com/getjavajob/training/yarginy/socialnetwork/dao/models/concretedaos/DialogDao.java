package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.DialogDaoTx;
import org.springframework.stereotype.Repository;

@Repository("dialogDao")
public class DialogDao extends AbstractTxDelegateDao<Dialog> {
    public DialogDao(DialogDaoTx dialogDaoTx) {
        super(dialogDaoTx);
    }
}
