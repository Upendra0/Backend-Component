package com.upendra.bootstrap;

import com.upendra.entity.Author;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class HibernateConfigFileBootstrap implements SessionFactoryBootstrap {

    private final SessionFactory sessionFactory;

    public HibernateConfigFileBootstrap() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // remember in case of hibernate-sample.properties file, hibernate load properties implicitly.
                .addAnnotatedClass(Author.class)
                .buildSessionFactory();
    }


    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void shutDown() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
