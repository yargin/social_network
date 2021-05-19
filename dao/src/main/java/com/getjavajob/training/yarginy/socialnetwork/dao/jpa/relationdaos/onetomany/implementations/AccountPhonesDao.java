package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class AccountPhonesDao implements OneToManyDao<Phone> {
    private final PhoneDao phoneDao;
    private final ModelsFactory factory;
    @PersistenceContext
    private transient EntityManager entityManager;

    public AccountPhonesDao(PhoneDao phoneDao, ModelsFactory modelsFactory) {
        this.phoneDao = phoneDao;
        this.factory = modelsFactory;
    }

    @Override
    @Transactional
    public Collection<Phone> selectMany(long accountId) {
        Account account = factory.getAccount(accountId);
        TypedQuery<Phone> selectMany = entityManager.createQuery("select p from Phone p join fetch p.owner " +
                "where p.owner = :owner", Phone.class);
        selectMany.setParameter("owner", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long phoneId) {
        Phone phone = phoneDao.select(phoneId);
        Account owner = phone.getOwner();
        return !isNull(owner) && owner.getId() == accountId;
    }
}
