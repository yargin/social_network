package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class DbConnectorImpl implements DbConnector {
    private static ConnectionsPool singleConnectionsPool;
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionProxy> connectionsToReuse;
    private final Semaphore waitersSemaphore;
    private final int capacity;
    private final ConnectionsPool connectionsPool;

    public DbConnectorImpl(BlockingQueue<ConnectionProxy> connectionsToReuse, Semaphore waitersSemaphore, int capacity, ConnectionsPool connectionsPool) {
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

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public Connection bindConnection() throws SQLException {
        return null;
    }

    @Override
    public void unbindConnection() throws SQLException {

    }
}
