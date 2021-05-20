package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericBatchDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullPhone;
import static java.util.Objects.isNull;

@Repository
public class PhoneDao extends GenericBatchDao<Phone> {
    @Override
    public Phone getNullModel() {
        return getNullPhone();
    }

    @Override
    protected TypedQuery<Phone> getSelectByAltKey(Phone phone) {
        TypedQuery<Phone> query = entityManager.createQuery("select p from Phone p where p.number = :number",
                Phone.class);
        query.setParameter("number", phone.getNumber());
        return query;
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
    protected Phone selectByPk(long id) {
        return entityManager.find(Phone.class, id);
    }

    @Override
    protected boolean checkEntityFail(Phone phone) {
        return isNull(phone.getOwner());
    }

    @Override
    protected void prepareModelRelations(Phone phone) {
        phone.setOwner(entityManager.getReference(Account.class, phone.getOwner().getId()));
    }

    @Override
    protected TypedQuery<Phone> getSelectAll() {
        return entityManager.createQuery("select p from Phone p join fetch p.owner a", Phone.class);
    }

    @Override
    protected Phone getModelReference(Phone phone) {
        return entityManager.getReference(Phone.class, phone.getId());
    }

    @Override
    protected Query getDeleteByAltKeyQuery(Phone phone) {
        Query query = entityManager.createQuery("delete from Phone p where p.number = :number");
        query.setParameter("number", phone.getNumber());
        return query;
    }
}
