package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullGroup;

@Repository
public class JpaGroupDao extends JpaGenericDao<Group> {
    @Override
    protected Supplier<TypedQuery<Group>> getSelectAll(EntityManager entityManager) {
//        return () -> entityManager.createQuery("select g from Group g join fetch g.owner", Group.class);
        return () -> entityManager.createQuery("select g from Group g", Group.class);
    }

    @Override
    protected Supplier<Group> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Group.class, id);
    }

    @Override
    protected Supplier<TypedQuery<Group>> getSelectByAltKey(EntityManager entityManager, Group group) {
        return () -> {
//            TypedQuery<Group> query = entityManager.createQuery("select g from Group g join fetch g.owner where g.name = :name", Group.class);
            TypedQuery<Group> query = entityManager.createQuery("select g from Group g where g.name = :name", Group.class);
            query.setParameter("name", group.getName());
            return query;
        };
    }

    @Override
    public Group getNullModel() {
        return getNullGroup();
    }

    @Override
    protected Supplier<Group> getModelReference(EntityManager entityManager, Group group) {
        return () -> entityManager.getReference(Group.class, group.getId());
    }
}
