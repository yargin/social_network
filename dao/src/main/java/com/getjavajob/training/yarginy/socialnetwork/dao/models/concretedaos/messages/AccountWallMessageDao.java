package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.messages.AccountWallMessageDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("accountWallMessageDao")
public class AccountWallMessageDao extends DelegateDaoTx<AccountWallMessage> {
    public AccountWallMessageDao(AccountWallMessageDaoTransactional accountWallMessageDaoTransactional) {
        super(accountWallMessageDaoTransactional);
    }
}
