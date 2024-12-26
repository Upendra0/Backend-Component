package com.upendra.repository;

import com.upendra.dto.AuthorDTO;
import com.upendra.entity.Author;
import org.hibernate.SessionFactory;

import java.util.List;

public class AuthorRepositoryImpl extends HibernateGenericRepository<Author> implements AuthorRepository {

    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<AuthorDTO> getByName(String name) {
        String selectSQL = "SELECT a.name, a.age From author a where a.name=?1";
        return executeSelectionNativeQuery(selectSQL, AuthorDTO.class, name);
    }
}
