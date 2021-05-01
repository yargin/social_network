package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.Supplier;

@Component
public class TransactionPerformer implements Serializable {
    public <E extends Model> boolean transactionPerformed(Consumer<E> consumer, E model) {
        try {
            consumer.accept(model);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public <E extends Model> boolean transactionPerformed(BiConsumer<Collection<E>, Collection<E>> consumer,
                                                          Collection<E> storedModels, Collection<E> newModels) {
        try {
            consumer.accept(storedModels, newModels);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public <E extends Model> boolean transactionPerformed(Consumer<Collection<E>> consumer, Collection<E> models) {
        try {
            consumer.accept(models);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean transactionPerformed(LongBiConsumer consumer, long firstId, long secondId) {
        try {
            consumer.accept(firstId, secondId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public <E extends Model> E transactionPerformed(Supplier<E> supplier, LongFunction<E> function, long id) {
        try {
            return function.apply(id);
        } catch (IllegalArgumentException e) {
            return supplier.get();
        }
    }
}
