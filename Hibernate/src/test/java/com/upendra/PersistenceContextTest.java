package com.upendra;

import com.upendra.bootstrap.HibernateConfigFileBootstrap;
import com.upendra.bootstrap.HibernateUtil;
import com.upendra.bootstrap.SessionFactoryBootstrap;
import com.upendra.dto.AuthorDTO;
import com.upendra.entity.Author;
import com.upendra.repository.AuthorRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class PersistenceContextTest {

    private static SessionFactoryBootstrap sessionFactoryBootstrap;

    @BeforeAll
    public static void init() {
        sessionFactoryBootstrap = new HibernateConfigFileBootstrap();
    }

    @AfterAll
    public static void close() {
        sessionFactoryBootstrap.shutDown();
    }

    @Test
    void idForNewEntityISGenerateJustAfterPersistOperation() {
        Author author = new Author("Upendra", 21);

        Session session = sessionFactoryBootstrap.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(author);

        Assertions.assertNotNull(author.getId());

        tx.commit();
        session.close();
    }

    @Test
    void persistingTransientObjectWithIdThrowsException() {
        Author author = new Author(21L, "Upendra", 21);

        Session session = sessionFactoryBootstrap.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Assertions.assertThrows(Exception.class, () -> session.persist(author));

        tx.commit();
        session.close();
    }

    @Test
    void entityCanBeRemovedFromDetachStateWithId() {
        Author author = new Author();
        author.setId(2L);
        Session session = sessionFactoryBootstrap.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Assertions.assertDoesNotThrow(() -> session.remove(author));

        tx.commit();
        session.close();
    }

    @Test
    void mergeofdetachedentityNotInPersistenceContextOrDatabaseThrowsException() {
        Author author = new Author(21L, "Upendra", 21);

        Session session = sessionFactoryBootstrap.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Assertions.assertThrows(Exception.class, () -> session.merge(author));

        tx.commit();
        session.close();
    }

    @Test
    void dirtyChecking() {
        Session session = sessionFactoryBootstrap.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Author author = session.get(Author.class, 1L);
        Author author2 = session.get(Author.class, 1L);

        Assertions.assertSame(author, author2);

        tx.commit();
        session.close();
    }

    @Test
    void readOnlySessionDoesNotPersistEntities() {
        try (Session session = sessionFactoryBootstrap.getSessionFactory().openSession()) {
            session.setDefaultReadOnly(true);
            Transaction tx = session.beginTransaction();

            Author author = session.get(Author.class, 1L);

            Assertions.assertFalse(session.contains(author));

            tx.commit();
        }
    }

    @Test
    void executingHQLAlsoStoreEntityInPersistenceContext() {
        AuthorRepositoryImpl authorRepositoryImpl = new AuthorRepositoryImpl(HibernateUtil.getSessionFactory());
        Author a = new Author("upen", 21);
        Author b = new Author(a.getName(), 23);
        authorRepositoryImpl.save(a);
        authorRepositoryImpl.save(b);

        List<AuthorDTO> authorsWithSameName = authorRepositoryImpl.getByName(a.getName());

        Assertions.assertFalse(authorsWithSameName.isEmpty());

    }
}
