package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.currentThread;
import static java.util.Objects.isNull;

public final class ConnectionsPoolImpl implements ConnectionsPool {
    private static ConnectionsPool singleConnectionsPool;
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionProxy> connectionsToReuse;
    private final Semaphore waitersSemaphore;
    private final ThreadLocal<ConnectionProxy> threadConnection;
    private final int capacity;

    private ConnectionsPoolImpl(String propertiesFile, int capacity) {
        try (InputStream inputStream = ConnectionsPoolImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.capacity = capacity;
        connectionsToReuse = new ArrayBlockingQueue<>(capacity);
        waitersSemaphore = new Semaphore(capacity);
        threadConnection = new ThreadLocal<>();
    }

    public static ConnectionsPool getDbConnector(String propertiesFile, int capacity) {
        if (isNull(singleConnectionsPool)) {
            singleConnectionsPool = new ConnectionsPoolImpl(propertiesFile, capacity);
        }
        return singleConnectionsPool;
    }

    public void unbindConnection() throws SQLException {
        ConnectionProxy connectionProxy = threadConnection.get();
        threadConnection.remove();
        //if no connections consumer is waiting for connection
        if (waitersSemaphore.getQueueLength() == 0) {
            connectionProxy.closeActually();
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
        return threadConnection.get();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public Connection bindConnection() throws SQLException {
        ConnectionProxy connection;
        try {
            waitersSemaphore.acquire();
            if (connectionsToReuse.isEmpty()) {
                connection = new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"), properties));
            } else {
                //blocks until at least one connection becomes free
                connection = connectionsToReuse.take();
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        threadConnection.set(connection);
        return connection;
    }
}
