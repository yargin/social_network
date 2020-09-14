package com.getjavajob.training.yarginy.socialnetwork.common.models;

/**
 * represents model as entity
 */
public interface Entity {
    /**
     * get entity's number unique identifier, usually auto-generated
     *
     * @return unique number identifier
     */
    long getId();

    /**
     * set entity's number unique identifier
     *
     * @param id unique number identifier
     */
    void setId(long id);
}
