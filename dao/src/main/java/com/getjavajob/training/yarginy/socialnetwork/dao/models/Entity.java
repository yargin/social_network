package com.getjavajob.training.yarginy.socialnetwork.dao.models;

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
     * retrieve entity's number unique identifier, usually auto-generated
     *
     * @return unique number identifier
     */
    int getId();
}
