
package com.mree.app.core.service.impl;

import com.mree.app.core.common.model.BaseInfo;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.BaseEntity;
import com.mree.app.core.repo.BaseRepository;
import com.mree.app.core.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseService<E extends BaseEntity<I>, I extends BaseInfo> implements IBaseService<E, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    public abstract BaseRepository<E, I> getRepo();

    public abstract E getEntity(I info);

    public abstract E beforeCreate(I info) throws AppServiceException;

    public abstract void afterCreate(E entity) throws AppServiceException;

    public abstract E beforeUpdate(I info) throws AppServiceException;

    public abstract void afterUpdate(E entity) throws AppServiceException;

    public abstract void beforeDelete(Long id) throws AppServiceException;

    @Override
    public I getById(Long id) throws AppServiceException {
        E entity = getRepo().findById(id).get();
        return entity.toInfo();
    }

    @Override
    public I create(I info) throws AppServiceException {
        try {
            E entity = beforeCreate(info);
            entity.fromInfo(info);
            entity = getRepo().save(entity);
            afterCreate(entity);
            return entity.toInfo();
        } catch (Exception e) {
            throw new AppServiceException(e);
        }
    }

    @Override
    public I update(I info) throws AppServiceException {
        try {
            E entity = beforeUpdate(info);
            entity.fromInfo(info);
            entity = getRepo().save(entity);
            afterUpdate(entity);
            return entity.toInfo();
        } catch (Exception e) {
            throw new AppServiceException(e);
        }
    }

    @Override
    public void delete(Long id) throws AppServiceException {
        try {
            beforeDelete(id);
            getRepo().deleteById(id);
        } catch (Exception e) {
            throw new AppServiceException(e);
        }
    }

    @Override
    public List<I> getList() throws AppServiceException {
        try {
            List<E> eList = (List<E>) getRepo().findAll();
            List<I> list = new ArrayList<>();
            eList.stream().forEach(e -> list.add(e.toInfo()));
            return list;
        } catch (Exception e) {
            throw new AppServiceException(e);
        }
    }

}
