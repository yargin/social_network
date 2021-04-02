package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.function.Function;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullGroup;

@Repository
public class JpaGroupDao extends GenericDao<Group> {
    @Override
    protected Function<EntityManager, TypedQuery<Group>> getSelectAllFunction() {
        return entityManager -> entityManager.createQuery("select g from Group g", Group.class);
    }

    @Override
    protected Function<EntityManager, Group> getSelectByPkFunction(long id) {
        return entityManager -> entityManager.find(Group.class, id);
    }

    @Override
    protected Function<EntityManager, TypedQuery<Group>> getSelectByAltKeyFunction(Group group) {
        return entityManager -> {
            TypedQuery<Group> query = entityManager.createQuery("select g from Group g where g.name = :name", Group.class);
            query.setParameter("name", group.getName());
            return query;
        };
    }

    @Override
    public Group getNullModel() {
        return getNullGroup();
    }
}
