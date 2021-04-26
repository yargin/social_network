package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany.concretes;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany.FriendshipDaoTransactional;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany.SelfManyToManyDelegateDaoTx;
import org.springframework.stereotype.Repository;

@Repository("friendshipDao")
public class FriendshipDao extends SelfManyToManyDelegateDaoTx<Account> {
    public FriendshipDao(FriendshipDaoTransactional friendshipDaoTransactional) {
        super(friendshipDaoTransactional);
    }
}
