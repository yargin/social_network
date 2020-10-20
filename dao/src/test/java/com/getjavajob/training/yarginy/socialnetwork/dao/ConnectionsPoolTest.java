package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.TransactionManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertSame;

public class ConnectionsPoolTest {
    private static final String CLASS = "ConnectionsPoolTest";
    private static final DbFactory DB_FACTORY = getDbFactory();
    private static final ConnectionPool CONNECTION_POOL = DB_FACTORY.getConnectionPool();
    private static final int threadsNumber = 30;

    /**
     * create some threads. In each a connection required
     * test asserts that number of connections used in multiple threads is equal to pool size
     * that means connections are transferred between threads when one doesn't need it anymore - without transactions
     */
    @Test
    public void testGetConnection() {
        Set<Connection> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Connection connection = CONNECTION_POOL.getConnection();
                    connections.add(connection);
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalStateException(e);
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertSame(connections.size(), CONNECTION_POOL.getCapacity());
        printPassed(CLASS, "testGetConnection");
    }

    /**
     * create some threads in which transaction started and some connections reused
     * test asserts that number of connections used in multiple threads and multiple transactions is equal to pool size
     * that means that one connection is used due one transaction and
     * connections are transferred between transactions when any doesn't need it anymore
     */
    @Test
    public void testTransaction() {
        Set<Connection> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());
        Collection<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(() -> {
                try {
                    try (Transaction transaction = TransactionManager.getTransaction()) {
                        for (int j = 0; j < 15; j++) {
                            Connection connection = CONNECTION_POOL.getConnection();
                            connections.add(connection);
                            connection.close();
                        }
                        transaction.commit();
                    }
                } catch (SQLException e) {
                    throw new IllegalStateException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertSame(CONNECTION_POOL.getCapacity(), connections.size());
        printPassed(CLASS, "testTransaction");
    }
}
