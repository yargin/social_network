package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullPassword;
import static java.util.Objects.isNull;


@Repository("jpaPasswordDao")
public class PasswordDao extends GenericDao<Password> {
    private AccountDao accountDao;

    public PasswordDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

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
    protected boolean checkEntity(Password password) {
        return !isNull(password.getAccount());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, Password password) {
//        TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.email = :email",
//                Account.class);
//        query.setParameter("email", password.getAccount().getEmail());
//        try {
//            Account account = query.getSingleResult();
//            password.setAccount(entityManager.getReference(Account.class, account.getId()));
//        } catch (NoResultException | NonUniqueResultException e) {
//            throw new IllegalArgumentException(e);
//        }

//        password.setAccount(entityManager.merge(password.getAccount()));

        Account account;
        try {
            password.setAccount(entityManager.getReference(Account.class, password.getAccount().getId()));
        } catch (EntityNotFoundException e) {
            password.setAccount(entityManager.merge(password.getAccount()));
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
