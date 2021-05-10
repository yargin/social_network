package com.getjavajob.training.yarginy.socialnetwork.dao.repositories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

public interface GroupGraphRepository {
    Group getFullInfo(long id);
}
