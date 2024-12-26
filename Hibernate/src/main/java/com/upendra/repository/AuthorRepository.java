package com.upendra.repository;

import com.upendra.dto.AuthorDTO;
import com.upendra.entity.Author;

import java.util.List;

public interface AuthorRepository {

    void save(Author author);

    List<AuthorDTO> getByName(String name);
}
