package com.getjavajob.training.yarginy.socialnetwork.common.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * represents model as entity
 */
@Component
public interface Entity extends Serializable {
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
