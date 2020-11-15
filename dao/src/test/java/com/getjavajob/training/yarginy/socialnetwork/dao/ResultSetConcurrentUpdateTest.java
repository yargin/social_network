package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class ResultSetConcurrentUpdateTest {
    private static final int ITERATIONS = 1500;
    private final AccountDao accountDao = new AccountDaoImpl();
    private final DbFactory dbFactory = getDbFactory();
    private volatile boolean inFirstThread;
//
//    @Test
//    public void testConcurrentUpdate() throws InterruptedException {
//        Account account = new AccountImpl("test", "test", "test@test.test");
//        accountDao.create(account);
//        String query = "SELECT * FROM Accounts WHERE email = 'test@test.test'";
//        ConnectionPool connectionPool = dbFactory.getConnectionPool();
//        Thread firstThread = new Thread(() -> {
//            try (Connection connection = connectionPool.getConnection();
//                 PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
//                         ResultSet.CONCUR_UPDATABLE);
//                 ResultSet resultSet = statement.executeQuery()) {
//                resultSet.next();
//                inFirstThread = true;
//                System.out.println("first thread started");
//                for (int i = 0; i <= ITERATIONS; i++) {
//                    resultSet.updateString("name", account.getName() + i);
//                }
//                resultSet.updateRow();
//                System.out.println("first thread finished");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        firstThread.start();
//        Thread secondThread = new Thread(() -> {
//            try (Connection connection = connectionPool.getConnection();
//                 PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
//                         ResultSet.CONCUR_UPDATABLE);
//                 ResultSet resultSet = statement.executeQuery()) {
//                while (!inFirstThread) {
//                    currentThread().wait();
//                }
//                if (inFirstThread) {
//                    System.out.println("second thread started");
//                    resultSet.next();
//                    resultSet.updateString("name", "name from second thread");
//                    System.out.println("second thread finished");
//                    resultSet.updateRow();
//                }
//            } catch (Exception ignore) {
//            }
//        });
//        secondThread.start();
//        firstThread.join();
//        secondThread.join();
//        Account checkedAccount = accountDao.select(account);
//        assertEquals("test" + ITERATIONS, checkedAccount.getName());
//        assert accountDao.delete(account);
//    }
}
