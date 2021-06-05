package com.getjavajob.training.yarginy.socialnetwork.dao.repositories.specificdaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

public interface GroupSpecificDao {
    Group getFullInfo(long id);
}
