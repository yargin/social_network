package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.*;

@Repository
public class JpaPhoneDao extends JpaGenericDao<Phone> {
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
    protected Supplier<Phone> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(Phone.class, id);
    }

    @Override
    protected Supplier<TypedQuery<Phone>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select p from Phone p", Phone.class);
    }

    @Override
    protected Supplier<Phone> getModelReference(EntityManager entityManager, Phone phone) {
        return () -> entityManager.getReference(Phone.class, phone.getId());
    }
}
