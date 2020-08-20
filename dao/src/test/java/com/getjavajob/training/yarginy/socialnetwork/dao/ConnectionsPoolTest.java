package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ConnectionsPoolTest {
    private static final String CLASS = "ConnectionsPoolTest";
    private static final String PROPERTIES_FILE = "connections/MySQLConnection.properties";
    private static final int CONNECTIONS_NEEDED = 10;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private static final DbConnector CONNECTOR = DbConnectorImpl.getDbConnector(PROPERTIES_FILE, 1);

    @Test
    public void testGetConnection() throws InterruptedException {
        sleep(15000);
        for (int i = 0; i < CONNECTIONS_NEEDED; i++) {
            Thread thread = new Thread(() -> {
                try (Connection connection = CONNECTOR.getConnection()) {
                    COUNTER.incrementAndGet();
                    sleep(3000);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
        sleep(1000);
        int connections = CONNECTOR.getCapacity();
        assertSame(connections, COUNTER.get());
        printPassed(CLASS, "testGetConnection");
        sleep(4500);
        assertSame(connections + CONNECTOR.getCapacity(), COUNTER.get());
        printPassed(CLASS, "testGetConnection");
    }

    /**
     * assert that required connections will actually be specified reused connections. Needed connections are stored in
     * the {@link Set}. In the end we compare set's size(number of unique connections was used) with specified capacity
     */
    @Test
    public void testConnectionReuse() throws InterruptedException {
        Set<Connection> connections = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < CONNECTIONS_NEEDED; i++) {
            Runnable runnable = () -> {
                try (Connection connection = CONNECTOR.getConnection()) {
                    sleep(1000);
                    connections.add(connection);
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();
        }
        sleep(7000);
        assertEquals(CONNECTOR.getCapacity(), connections.size());
        printPassed(CLASS, "testConnectionReuse");
    }
}
