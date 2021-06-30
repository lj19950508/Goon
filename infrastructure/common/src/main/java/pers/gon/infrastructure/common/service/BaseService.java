package pers.gon.infrastructure.common.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.repository.BaseRepository;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public abstract class BaseService<R extends BaseRepository<T,ID>,T extends BaseEntity,ID> implements  IBaseService <T,ID> {
	@Autowired
	EntityManager entityManager;
	@Override
	public EntityManager entityManager(){
		return this.entityManager;
	}
	@Autowired
	protected R repository;
	@Override
	public T save(T t){
		return repository.save(t);
	}
	@Override
	public List<T> saveAll(Iterable<T> iterable){
		return repository.saveAll(iterable);
	}
	@Override
	public Iterable<T> insertInBatch(Iterable<T> iterable,int batchSize){
		Iterator<T> iterator = iterable.iterator();
		int index = 0;
		while (iterator.hasNext()){
			entityManager.persist(iterator.next());
			index++;
			if (index % batchSize == 0){
				entityManager.flush();
				entityManager.clear();
			}
		}
		if (index % batchSize != 0){
			entityManager.flush();
			entityManager.clear();
		}
		return iterable;
	}
	@Override
	public Iterable<T> updateInBatch(Iterable<T> iterable,int batchSize){
		Iterator<T> iterator = iterable.iterator();
		int index = 0;
		while (iterator.hasNext()){
			entityManager.merge(iterator.next());
			index++;
			if (index % batchSize == 0){
				entityManager.flush();
				entityManager.clear();
			}
		}
		if (index % batchSize != 0){
			entityManager.flush();
			entityManager.clear();
		}
		return iterable;
	}
	@Override
	public List<T> findAllById(Iterable<ID> id){
		return repository.findAllById(id);
	}

	@Override
	public T findById(ID id){
		return repository.findById(id).get();
	}
	@Override
	public List<T> findAll(){
		return repository.findAll();
	}
	@Override
	public List<T> findList(T entity){
		return repository.findAll(Example.of(entity));
	}
	@Override
	public List<T> findList(Example<T> example){
		return repository.findAll(example);
	}
	@Override
	public List<T> findList(Specification<T> specification){
		return repository.findAll(specification);
	}
	@Override
	public Page<T> findPage(T entity, Pageable pageable){
		return repository.findAll(Example.of(entity),pageable);
	}
	@Override
	public Page<T> findPage(Example<T> example, Pageable pageable){
		return repository.findAll(example,pageable);
	}
	@Override
	public Page<T> findPage(Specification<T> specification, Pageable pageable){
		return repository.findAll(specification,pageable);
	}
	@Override
	public void deleteById(ID id){
		repository.deleteById(id);
	}
	@Override
	public void delete(T t){
		repository.delete(t);
	}
	@Override
	public void deleteAllById(Iterable<ID> ids){
		repository.deleteAll(repository.findAllById(ids));
	}
	@Override
	public void deleteAll(Iterable<T> entitys){
		repository.deleteAll(entitys);
	}

//	DATARULE: 1.本人数据 2.所在部门数据    3.所在部门以及以下数据 ， 4部门以下数据 5.是否排除本人 (在 2，3有效) 部门级过滤

}
