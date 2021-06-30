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

    /** 只想好了下一页怎么做没想好上一页怎么做EMM， 上一页是传小于第一条ID 且  ID倒序排序 获取  获取完再进行List反序
     *  由于ID有自增趋向 ，可以使用ID索引  id>最后一条  size = 条数来做分页， 这样数据都的时候效率会快
     * @param specification 过滤条件
     * @param lastId 上一页的最后一条ID
     * @param pageable 分页参数 （不需要传pageNumber）
     * @return
     */
    Page<T> findNextPage(Specification<T> specification,ID lastId, Pageable pageable);

    Page<T> findPrevPage(Specification<T> specification,ID lastId, Pageable pageable);


    void deleteById(ID id);

    void delete(T t);

    @Transactional
    void deleteAll(Iterable<T> iterable);

    @Transactional
    void deleteAllById(Iterable<ID> ids);

}
