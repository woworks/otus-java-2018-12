package ru.otus.java.hw11.hibernate;

import org.hibernate.cfg.Configuration;

import java.io.File;

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
                .configure(new File(this.configFile));
        for (Class clazz : this.classList) {
            configuration.addAnnotatedClass(clazz);
        }
        return configuration;
    }
}
