package ru.otus.java.hw14.hibernate;

import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    private String configFile;
    private Class[] classList;

    public HibernateConfiguration configFile(String fileName) {
        this.configFile = fileName;
        return this;
    }

    public HibernateConfiguration classes(Class... classList) {
        this.classList = classList;
        return this;
    }

    public Configuration build() {
        Configuration configuration = new Configuration()
                .configure(this.configFile);
        for (Class clazz : this.classList) {
            configuration.addAnnotatedClass(clazz);
        }
        return configuration;
    }
}
