package pers.gon.infrastructure.common.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


public interface IBaseService<T, ID> {

    EntityManager entityManager();

    T save(T t);

    List<T> saveAll(Iterable<T> iterable);
    @Transactional
    Iterable<T> insertInBatch(Iterable<T> iterable, int batchSize);
    @Transactional
    Iterable<T> updateInBatch(Iterable<T> iterable, int batchSize);

    List<T> findList(T entity);

    List<T> findList(Example<T> example);

    List<T> findList(Specification<T> specification);

    List<T> findAll();

    Page<T> findPage(T entity, Pageable pageable);

    List<T> findAllById(Iterable<ID> id);

    T findById(ID id);

    Page<T> findPage(Example<T> example, Pageable pageable);

    Page<T> findPage(Specification<T> specification, Pageable pageable);


    void deleteById(ID id);

    void delete(T t);

    @Transactional
    void deleteAll(Iterable<T> iterable);

    @Transactional
    void deleteAllById(Iterable<ID> ids);

}
