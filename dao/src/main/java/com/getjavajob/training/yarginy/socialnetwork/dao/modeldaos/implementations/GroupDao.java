package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.GenericBatchDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullGroup;
import static java.util.Objects.isNull;

@Repository
public class GroupDao extends GenericBatchDao<Group> {
    @Override
    protected Supplier<TypedQuery<Group>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select g from Group g", Group.class);
    }

    @Override
    @Transactional
    public Group selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.Group.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(Group.class, id, hints);
    }

    @Override
    protected Supplier<Group> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Group.class, id);
    }

    @Override
    protected Supplier<TypedQuery<Group>> getSelectByAltKey(EntityManager entityManager, Group group) {
        return () -> {
            TypedQuery<Group> query = entityManager.createQuery("select g from Group g join fetch g.owner o " +
                    "where g.name = :name", Group.class);
            query.setParameter("name", group.getName());
            return query;
        };
    }

    @Override
    protected boolean checkEntityFail(Group group) {
        return isNull(group.getOwner());
    }

    @Override
    public Group getNullModel() {
        return getNullGroup();
    }

    @Override
    protected Supplier<Group> getModelReference(EntityManager entityManager, Group group) {
        return () -> entityManager.getReference(Group.class, group.getId());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Group group) {
        group.setOwner(entityManager.getReference(Account.class, group.getOwner().getId()));
    }
}
