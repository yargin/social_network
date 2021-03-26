package com.getjavajob.training.yarginy.socialnetwork.common.models;

import javax.persistence.Id;

/**
 * defines {@link Model} that can be saved in some storage, identified by unique(among it's type {@link Model}) number
 * identifier
 */
public class AbstractModel {
    @Id
    private long id;

    /**
     * gets {@link Model} id
     * <br>1 or higher if {@link Model} was read from storage
     * <br>0 if it wasn't read yet
     * <br>-1 - special value for null-{@link Model}
     *
     * @return id assigned when {@link Model} saved to storage
     */
    public long getIdNumber() {
        return id;
    }

    /**
     * sets {@link Model} id
     * <br>1 or higher if {@link Model} was read from storage
     * <br>0 if it wasn't read yet
     * <br>-1 - special value for null-{@link Model}
     */
    public void setIdNumber(long id) {
        this.id = id;
    }
}
