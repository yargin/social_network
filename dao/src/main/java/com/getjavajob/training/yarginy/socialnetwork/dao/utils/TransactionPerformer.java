package com.getjavajob.training.yarginy.socialnetwork.dao.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class TransactionPerformer implements Serializable {
    //for model crud operations
    public <E extends Model> boolean transactionPerformed(Consumer<E> consumer, E model) {
//        return perform(() -> consumer.accept(model));
        //todo
        try {
            consumer.accept(model);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    //for models batch updates
    public <E extends Model> boolean transactionPerformed(BiConsumer<Collection<E>, Collection<E>> consumer,
                                                          Collection<E> storedModels, Collection<E> newModels) {
        return perform(() -> consumer.accept(storedModels, newModels));
    }

    //for models batch create delete
    public <E extends Model> boolean transactionPerformed(Consumer<Collection<E>> consumer, Collection<E> models) {
        return perform(() -> consumer.accept(models));
    }

    //for many to many relations
    public boolean transactionPerformed(LongBiConsumer consumer, long firstId, long secondId) {
        return perform(() -> consumer.accept(firstId, secondId));
    }

    //for one field updates
    public <E extends Model, P> boolean transactionPerformed(BiConsumer<E, P> biConsumer, E model, P object) {
        return perform(() -> biConsumer.accept(model, object));
    }

    //for one field updates
    public <E extends Model, P extends Enum<P>> boolean transactionPerformed(BiConsumer<E, P> biConsumer, E model,
                                                                             P object) {
        return perform(() -> biConsumer.accept(model, object));
    }

    private boolean perform(VoidOperationPerformer performer) {
        try {
            performer.performOperation();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
