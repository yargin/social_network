package com.getjavajob.training.yarginy.socialnetwork.common.entities;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.checkId;

/**
 * defines {@link Entity} that can be saved in some storage, identified by unique number identifier
 */
public class AbstractEntity {
    private long id;

    public long getIdNumber() {
        return checkId(id);
    }

    public void setIdNumber(long id) {
        this.id = checkId(id);
    }
}
