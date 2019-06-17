package ru.otus.java.hw14.hibernate;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.datasets.AddressDataSet;
import ru.otus.java.hw14.datasets.EmptyDataSet;
import ru.otus.java.hw14.datasets.PhoneDataSet;
import ru.otus.java.hw14.datasets.UserDataSet;
import ru.otus.java.hw14.hibernate.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateDBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public HibernateDBServiceImpl() {
        Configuration configuration = new HibernateConfiguration()
                .configFile("hibernate.cfg.xml")
                .classes(UserDataSet.class, AddressDataSet.class, PhoneDataSet.class, EmptyDataSet.class)
                .build();
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
            try {
                UserDataSet object = dao.read(id);
                Hibernate.initialize(object);
                return object;
            } catch (ObjectNotFoundException e) {
                return null;
            }

        });
    }

    @Override
    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public List<UserDataSet> findByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            try {
                List<UserDataSet> object = dao.findByName(name);
                Hibernate.initialize(object);
                return object;
            } catch (ObjectNotFoundException e) {
                return null;
            }

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

    @Override
    public void flush() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.flush();
            transaction.commit();
        }

    }
}
