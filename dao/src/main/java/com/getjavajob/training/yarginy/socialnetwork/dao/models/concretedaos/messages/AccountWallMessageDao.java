package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.messages.AccountWallMessageDaoTx;
import org.springframework.stereotype.Repository;

@Repository("accountWallMessageDao")
public class AccountWallMessageDao extends AbstractTxDelegateDao<AccountWallMessage> {
    public AccountWallMessageDao(AccountWallMessageDaoTx accountWallMessageDaoTx) {
        super(accountWallMessageDaoTx);
    }
}
