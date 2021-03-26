package com.getjavajob.training.yarginy.socialnetwork.common.models;

public interface OwnedModel<E extends Model> {
    E getOwner();

    void setOwner(E owner);
}
