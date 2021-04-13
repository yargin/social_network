package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Repository;

import javax.persistence.Access;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullPassword;


@Repository("jpaPasswordDao")
public class JpaPasswordDao extends JpaGenericDao<Password> {
    @Override
    public Password getNullModel() {
        return getNullPassword();
    }

    @Override
    protected Supplier<TypedQuery<Password>> getSelectByAltKey(EntityManager entityManager, Password password) {
        return () -> {
            TypedQuery<Password> query = entityManager.createQuery("select new Password(a, p.stringPassword) " +
                            "from Password p join Account a on p.email = a.email where p.email =:email",
                    Password.class);
            query.setParameter("email", password.getAccount().getEmail());
            return query;
        };
    }

    @Override
    protected Supplier<Password> getSelectByPk(EntityManager entityManager, long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Supplier<TypedQuery<Password>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select p from Password p join fetch p.account a", Password.class);
    }

    @Override
    protected Supplier<Password> getModelReference(EntityManager entityManager, Password password) {
        return () -> entityManager.getReference(Password.class, password.getAccount());
    }

    @Override
    public boolean create(Password password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            password.setAccount(entityManager.merge(password.getAccount()));
            entityManager.persist(password);
            transaction.commit();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            if (e.getCause().getClass() == PropertyValueException.class) {
                throw new IllegalArgumentException(e);
            }
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Password password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Query query = entityManager.createQuery("update Password set stringPassword =: password where email =: email");
            query.setParameter("password", password.getStringPassword());
            query.setParameter("email", password.getAccount().getEmail());
            return query.executeUpdate() > 0;
        } catch (OptimisticLockException e) {
            throw new IllegalStateException(e);
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            return false;
        }
    }
}
