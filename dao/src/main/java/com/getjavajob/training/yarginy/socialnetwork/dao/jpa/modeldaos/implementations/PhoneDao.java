package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericBatchDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPhone;
import static java.util.Objects.isNull;

@Repository
public class PhoneDao extends GenericBatchDao<Phone> {
    @Override
    public Phone getNullModel() {
        return getNullPhone();
    }

    @Override
    protected Supplier<TypedQuery<Phone>> getSelectByAltKey(EntityManager entityManager, Phone phone) {
        return () -> {
            TypedQuery<Phone> query = entityManager.createQuery("select p from Phone p where p.number = :number",
                    Phone.class);
            query.setParameter("number", phone.getNumber());
            return query;
        };
    }

    @Override
    @Transactional
    public Phone selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.Phone.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(Phone.class, id, hints);
    }

    @Override
    protected Supplier<Phone> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Phone.class, id);
    }

    @Override
    protected boolean checkEntityFail(Phone phone) {
        return isNull(phone.getOwner());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Phone phone) {
        phone.setOwner(entityManager.getReference(Account.class, phone.getOwner().getId()));
    }

    @Override
    protected Supplier<TypedQuery<Phone>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select p from Phone p join fetch p.owner a", Phone.class);
    }

    @Override
    protected Supplier<Phone> getModelReference(EntityManager entityManager, Phone phone) {
        return () -> entityManager.getReference(Phone.class, phone.getId());
    }
}
