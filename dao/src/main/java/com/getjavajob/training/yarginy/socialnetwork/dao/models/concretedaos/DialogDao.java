package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.DialogDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("dialogDao")
public class DialogDao extends DelegateDaoTx<Dialog> {
    public DialogDao(DialogDaoTransactional dialogDaoTransactional) {
        super(dialogDaoTransactional);
    }
}
