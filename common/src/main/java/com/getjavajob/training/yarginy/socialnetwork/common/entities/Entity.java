package com.getjavajob.training.yarginy.socialnetwork.common.entities;

/**
 * represents model as entity
 */
public interface Entity {
    /**
     * retrieve entity's {@link String} unique identifier
     *
     * @return unique {@link String} identifier
     */
    String getIdentifier();

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
