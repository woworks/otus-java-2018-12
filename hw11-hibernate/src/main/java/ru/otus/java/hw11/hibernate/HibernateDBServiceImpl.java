package ru.otus.java.hw11.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.java.hw11.base.DBService;
import ru.otus.java.hw11.datasets.AddressDataSet;
import ru.otus.java.hw11.datasets.EmptyDataSet;
import ru.otus.java.hw11.datasets.PhoneDataSet;
import ru.otus.java.hw11.datasets.UserDataSet;
import ru.otus.java.hw11.hibernate.dao.UserDataSetDAO;


import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author v.chibrikov
 */
public class HibernateDBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public HibernateDBServiceImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(EmptyDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hw11-hibernate");
        configuration.setProperty("hibernate.connection.username", "hw11hibernate");
        configuration.setProperty("hibernate.connection.password", "hw11hibernate");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");

        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public HibernateDBServiceImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(UserDataSet dataSet) {
        runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
        });
    }

    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet object = dao.read(id);
            Hibernate.initialize(object);
            return object;
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    void runInSession(Consumer<Session> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        }
    }
}
