package com.yayla.secondhand.secondhandbackend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends Repository<T, ID> {
    List<T> findAll();
    List<T> findAll(Sort sort);
    List<T> findAll(Pageable pageable);
    Optional<T> findById(ID id);
    long count();
}
