package com.getjavajob.training.yarginy.socialnetwork.common.models;

public interface OwnedEntity<E extends Entity> {
    E getOwner();

    void setOwner(E owner);
}
