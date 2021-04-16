package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_ID;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.*;
import static java.util.Objects.isNull;

@Repository("phoneDao")
public class PhoneDao extends BatchGenericTransactionalDao<Phone> {
    @Override
    public Phone getNullModel() {
        return getNullPhone();
    }

    @Override
    protected Supplier<TypedQuery<Phone>> getSelectByAltKey(EntityManager entityManager, Phone phone) {
        return () -> {
            TypedQuery<Phone> query = entityManager.createQuery("select p from Phone p join fetch p.owner a " +
                            "where p.number = :number", Phone.class);
            query.setParameter("number", phone.getNumber());
            return query;
        };
    }

    @Override
    protected Supplier<Phone> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Phone.class, id);
    }

    @Override
    protected boolean checkEntity(Phone phone) {
        return !isNull(phone.getOwner());
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
