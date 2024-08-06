package org.example.smartinventory.service;

import java.util.Collection;
import java.util.Optional;

public interface ServiceModel<T>
{
    Collection<T> findAll();
    void save(T t);
    void delete(T t);
    Optional<T> findById(Long id);
    Collection<T> findAllById(Iterable<Long> ids);
}
