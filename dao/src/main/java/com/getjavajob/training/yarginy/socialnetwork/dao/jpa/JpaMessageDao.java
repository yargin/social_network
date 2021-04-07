package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.*;

@Repository
public abstract class JpaMessageDao<E extends Message> extends JpaGenericDao<E> {
    @Override
    protected Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, Message message) {
        return () -> {
            Class<E> messageClass = getMessageClass();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> query = builder.createQuery(messageClass);
            Root<E> root = query.from(messageClass);
            Predicate authorPredicate = builder.equal(root.get("author"), message.getAuthor());
            Predicate receiverId = builder.equal(root.get("receiver_id"), message.getReceiverId());
            Predicate postedDate = builder.equal(root.get("posted"), message.getDate());
            query.select(root).where(authorPredicate).where(receiverId).where(postedDate);
            return entityManager.createQuery(query);
    };}

    @Override
    protected Supplier<E> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(getMessageClass(), id);
    }

    @Override
    protected Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager) {
        return () -> {
            Class<E> messageClass = getMessageClass();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> query = builder.createQuery(messageClass);
            Root<E> root = query.from(messageClass);
            query.select(root);
            return entityManager.createQuery(query);
        };
    }

    protected abstract Class<E> getMessageClass();

    @Override
    protected Supplier<E> getModelReference(EntityManager entityManager, Message message) {
        return () -> entityManager.getReference(getMessageClass(), message.getId());
    }

    @Override
    public E getNullModel() {
        return (E) getNullMessage();
    }
}
