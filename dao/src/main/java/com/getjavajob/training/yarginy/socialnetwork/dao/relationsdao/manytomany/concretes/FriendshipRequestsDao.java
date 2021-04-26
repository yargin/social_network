package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.concretes;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.FriendshipRequestsDaoTransactional;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDelegateDaoTx;
import org.springframework.stereotype.Repository;

@Repository("friendshipRequestsDao")
public class FriendshipRequestsDao extends ManyToManyDelegateDaoTx<Account, Account> {
    public FriendshipRequestsDao(FriendshipRequestsDaoTransactional friendshipRequestsDaoTransactional) {
        super(friendshipRequestsDaoTransactional);
    }
}
