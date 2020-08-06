package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.ResultPrinter.printPassed;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertSame;

public class ConnectionsPoolTest {
    private static final String CLASS = "ConnectionsPoolTest";
    private static final int NEEDED_CONNECTIONS = 5;
    private static final int POOL_SIZE = 2;
    private static final DbConnector CONNECTOR = new DbConnectorImpl("connections/MySQLConnection.properties", POOL_SIZE);
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Test
    public void testGetConnection() throws InterruptedException {
        for (int i = 0; i < NEEDED_CONNECTIONS; i++) {
            Thread thread = new Thread(() -> {
                try (Connection connection = CONNECTOR.getConnection()) {
                    COUNTER.incrementAndGet();
                    sleep(4000);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
        sleep(1000);
        assertSame(POOL_SIZE, COUNTER.get());
        printPassed(CLASS, "testGetConnection");
        sleep(7000);
        assertSame(POOL_SIZE, COUNTER.get());
        printPassed(CLASS, "testGetConnection");
    }
}
