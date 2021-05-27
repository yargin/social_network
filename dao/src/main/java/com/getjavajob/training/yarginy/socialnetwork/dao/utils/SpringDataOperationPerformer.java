package com.getjavajob.training.yarginy.socialnetwork.dao.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.Consumer;

@Component
public class SpringDataOperationPerformer implements Serializable {
    public <E extends Model> boolean performOperation(Consumer<E> function, E model) {
        try {
            function.accept(model);
            return true;
        } catch (DataAccessException | IllegalArgumentException e) {
            return false;
        }
    }
}
