package com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl;

public interface ScriptExecutor {
    /**
     * executes specified script
     *
     * @param scriptFile path to script to execute
     * @return true if successfuly executed, otherwise false
     * @throws IllegalStateException    if script can't be read
     * @throws IllegalArgumentException if script has mistake
     */
    boolean executeScript(String scriptFile);
}
