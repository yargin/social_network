package com.getjavajob.training.yarginy.socialnetwork.common.entities;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.checkId;

/**
 * defines {@link Entity} that can be saved in some storage, identified by unique(among it's type {@link Entity}) number
 * identifier
 */
public class AbstractEntity {
    private long id;

    /**
     * gets {@link Entity} id
     * <br>1 or higher if {@link Entity} was read from storage
     * <br>0 if it wasn't read yet
     * <br>-1 - special value for null-{@link Entity}
     *
     * @return id assigned when {@link Entity} saved to storage
     */
    public long getIdNumber() {
        return checkId(id);
    }

    /**
     * sets {@link Entity} id
     * <br>1 or higher if {@link Entity} was read from storage
     * <br>0 if it wasn't read yet
     * <br>-1 - special value for null-{@link Entity}
     */
    public void setIdNumber(long id) {
        this.id = checkId(id);
    }
}
