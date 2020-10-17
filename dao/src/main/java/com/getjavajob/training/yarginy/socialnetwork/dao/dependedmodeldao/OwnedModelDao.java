package com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.OwnedEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

public interface OwnedModelDao<O extends Entity, E extends OwnedEntity<O>> {
    E select(O owner);

    boolean create(E ownedEntity);

    boolean update(E ownedEntity);

    boolean delete(E ownedEntity);

    /**
     * checks that {@link OwnedEntity} is applicable
     *
     * @param ownedEntity to check
     */
    void checkEntity(E ownedEntity);

    /**
     * @return {@link OwnedEntity} that doesn't exist
     */
    E getNullEntity();
}
