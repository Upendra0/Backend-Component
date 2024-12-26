package com.upendra.bootstrap;

import org.hibernate.SessionFactory;

public interface SessionFactoryBootstrap {

    SessionFactory getSessionFactory();

    void shutDown();
}
