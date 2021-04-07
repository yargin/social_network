package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaMessageDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAccountWallMessageDao extends JpaMessageDao<AccountWallMessage> {
    @Override
    protected Class<AccountWallMessage> getMessageClass() {
        return AccountWallMessage.class;
    }
}
