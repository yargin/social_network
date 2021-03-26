package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;


import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

public interface OneToManyDao<M extends Model> extends Serializable {
    /**
     * selects all participants related to specified {@link Model}
     *
     * @param oneId one-side member of relationship
     * @return {@link Collection} of first participants
     */
    Collection<M> selectMany(long oneId);

    /**
     * examines that relation between two {@link Model}
     *
     * @param oneId  one-side member of relationship
     * @param manyId many-side member of relationship
     * @return true if exists otherwise false
     */
    boolean relationExists(long oneId, long manyId);
}
