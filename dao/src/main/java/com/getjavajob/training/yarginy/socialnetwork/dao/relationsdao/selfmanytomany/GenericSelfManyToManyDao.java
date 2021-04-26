package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaSelfManyToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import java.io.Serializable;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericSelfManyToManyDao<E extends Model> implements Serializable {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract JpaSelfManyToMany<E> genericGetReference(EntityManager entityManager, long greaterId, long lowerId);

    protected abstract JpaSelfManyToMany<E> genericFind(EntityManager entityManager, long greaterId, long lowerId);

    protected abstract JpaSelfManyToMany<E> genericCreateObject(EntityManager entityManager, long greaterId, long lowerId);

    protected abstract Collection<E> genericSelect(EntityManager entityManager, long id);

    public Collection<E> selectTransactional(long id) {
        checkId(id);
        return genericSelect(entityManager, id);
    }

    @Transactional
    public boolean createTransactional(long greaterId, long lowerId) {
        checkId(greaterId, lowerId);
        JpaSelfManyToMany<E> manyToMany = genericCreateObject(entityManager, greaterId, lowerId);
        try {
            entityManager.persist(manyToMany);
            entityManager.flush();
            return true;
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
    }

    @Transactional
    public boolean relationExistsTransactional(long greaterId, long lowerId) {
        checkId(greaterId, lowerId);
        JpaSelfManyToMany<E> manyToMany = genericFind(entityManager, greaterId, lowerId);
        return !isNull(manyToMany);
    }

    @Transactional
    public boolean deleteTransactional(long greaterId, long lowerId) {
        checkId(greaterId, lowerId);
        try {
            JpaSelfManyToMany<E> friendship = genericGetReference(entityManager, greaterId, lowerId);
            entityManager.remove(friendship);
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
    }

    private void checkId(long... ids) {
        for (long id : ids) {
            if (id < 1) { throw new IllegalArgumentException(); }
        }
    }
}
