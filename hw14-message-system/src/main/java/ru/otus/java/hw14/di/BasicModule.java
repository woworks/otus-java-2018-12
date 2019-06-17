package ru.otus.java.hw14.di;

import com.google.inject.AbstractModule;
import ru.otus.java.hw14.base.DBService;
import ru.otus.java.hw14.hibernate.HibernateDBServiceImpl;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DBService.class).to(HibernateDBServiceImpl.class);
    }
}
