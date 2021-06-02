package com.getjavajob.training.yarginy.socialnetwork.dao.utils;

@FunctionalInterface
public interface VoidOperationPerformer {
    void performOperation() throws IllegalArgumentException;
}
