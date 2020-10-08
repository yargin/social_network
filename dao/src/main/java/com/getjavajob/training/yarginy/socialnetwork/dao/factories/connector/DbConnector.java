package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.currentThread;
import static java.util.Objects.isNull;

public class DbConnector implements ConnectionPool, Transaction, TransactionManager {
    private static DbConnector singleDbConnector;
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionProxy> connectionsToReuse;
    private final Semaphore waitersSemaphore;
    private final ThreadLocal<ConnectionProxy> threadConnection;
    private final int capacity;

    private DbConnector(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnector.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.capacity = capacity;
        connectionsToReuse = new ArrayBlockingQueue<>(capacity);
        waitersSemaphore = new Semaphore(capacity);
        threadConnection = new ThreadLocal<>();
    }

    public static DbConnector getDbConnector(String propertiesFile, int capacity) {
        if (isNull(singleDbConnector)) {
            singleDbConnector = new DbConnector(propertiesFile, capacity);
        }
        return singleDbConnector;
    }

    private ConnectionProxy initConnection() {
        try {
            //if current thread already got connection
            if (!isNull(threadConnection.get()) && !threadConnection.get().isClosed()) {
                return threadConnection.get();
            }

            //if another thread need connection
            waitersSemaphore.acquire();
            ConnectionProxy connection;
            if (connectionsToReuse.isEmpty()) {
                connection = new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"), properties),
                        this);
            } else {
                //blocks until at least one connection becomes free
                connection = connectionsToReuse.take();
            }
            threadConnection.set(connection);
            return connection;
        } catch (InterruptedException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        ConnectionProxy connectionProxy = threadConnection.get();
        threadConnection.remove();
        //if no connections consumer is waiting for connection
        if (waitersSemaphore.getQueueLength() == 0) {
            try {
                connectionProxy.closeActually();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
            //if some consumer waiting for connection
        } else {
            try {
                connectionsToReuse.put(connectionProxy);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
        waitersSemaphore.release();
    }

    @Override
    public Connection getConnection() {
        return initConnection();
    }

    public void closeConnection() {
        close();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public Transaction getTransaction() {
        ConnectionProxy connection = initConnection();
        connection.setTransactional(true);
        return this;
    }

    @Override
    public void commit() {
        try {
            threadConnection.get().commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Savepoint setSavepoint() {
        try {
            return threadConnection.get().setSavepoint();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            threadConnection.get().rollback();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void rollback(Savepoint savepoint) {
        try {
            threadConnection.get().rollback(savepoint);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
