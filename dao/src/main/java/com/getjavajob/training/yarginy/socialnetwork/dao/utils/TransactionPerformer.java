package com.getjavajob.training.yarginy.socialnetwork.dao.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Component
public class TransactionPerformer implements Serializable {
    //for jpa
    public boolean perform(VoidOperationPerformer performer) {
        try {
            performer.performOperation();
            return true;
        } catch (DataAccessException | IllegalArgumentException e) {
            return false;
        }
    }

    //for repo creation
    public <E extends Model> boolean repoPerformCreate(E model, JpaRepository<E, Long> repository) {
        if (isNull(model) || model.getId() != 0) {
            return false;
        }
        try {
            repository.saveAndFlush(model);
            return true;
        } catch (DataAccessException | IllegalArgumentException e) {
            return false;
        }
    }

    //for repo update & delete
    public <E extends Model> boolean repoPerformUpdateOrDelete(E model, JpaRepository<E, Long> repository, Consumer<E> consumer) {
        if (isNull(model) || !repository.existsById(model.getId())) {
            return false;
        }
        try {
            consumer.accept(model);
            return true;
        } catch (DataAccessException | IllegalArgumentException e) {
            return false;
        }
    }
}
