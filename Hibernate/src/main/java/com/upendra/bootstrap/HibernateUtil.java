package com.upendra.bootstrap;

import com.upendra.entity.Author;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Author.class)
                .buildSessionFactory();
    }

    private HibernateUtil() {
        throw new IllegalArgumentException("Can not create instance of Util class.");
    }


    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
