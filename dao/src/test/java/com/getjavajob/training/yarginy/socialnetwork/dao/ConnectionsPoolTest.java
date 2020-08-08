package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.ResultPrinter.printPassed;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ConnectionsPoolTest {
    private static final String CLASS = "ConnectionsPoolTest";
    private static final String PROPERTIES_FILE = "connections/MySQLConnection.properties";
    private static final int CONNECTIONS_NEEDED = 5;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private static int counter;

    @Test
    public void testGetConnection() throws InterruptedException {
        int connections_needed = 2;
        DbConnector CONNECTOR = new DbConnectorImpl(PROPERTIES_FILE, connections_needed);
        for (int i = 0; i < CONNECTIONS_NEEDED; i++) {
            Thread thread = new Thread(() -> {
                try (Connection connection = CONNECTOR.getConnection()) {
                    COUNTER.incrementAndGet();
                    sleep(7000);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
        sleep(1000);
        assertSame(connections_needed, COUNTER.get());
        printPassed(CLASS, "testGetConnection");
        sleep(4000);
        assertSame(connections_needed, COUNTER.get());
        printPassed(CLASS, "testGetConnection");
    }

    @Test
    public void testConnectionReuse() throws InterruptedException {
        DbConnector dbConnector = new DbConnectorImpl(PROPERTIES_FILE, 1);
        AtomicReference<Connection> firstConnection = new AtomicReference<>();
        AtomicReference<Connection> lastConnection = new AtomicReference<>();
        for (int i = 0; i < CONNECTIONS_NEEDED; i++) {
            final int counter = i;
            Runnable runnable = () -> {
                try (Connection connection = dbConnector.getConnection()) {
                    sleep(1000);
                    if (counter == 0) {
                        firstConnection.set(connection);
                    } else {
                        lastConnection.set(connection);
                    }
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
        sleep(5000);
        assertEquals(firstConnection.get(), lastConnection.get());
        printPassed(CLASS, "testConnectionReuse");
    }

}
