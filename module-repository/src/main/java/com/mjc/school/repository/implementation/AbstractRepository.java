package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {
    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;
    private final Class<K> idClass;

    protected AbstractRepository() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        idClass = (Class<K>) type.getActualTypeArguments()[1];
    }

    @Override
    public List<T> readAll(int page, int size, String sortBy) {
        String[] sort = sortBy.split("::");
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " +
                entityClass.getSimpleName() + " e ORDER BY e." + sort[0] + " " + sort[1], entityClass);

        if(page > 0 && size > 0) {
            query.setFirstResult((page - 1) * size).setMaxResults(size);
        }
        return query.getResultList();
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T newEntity) {
        T updated = null;
        if(readById(newEntity.getId()).isPresent()) {
            T entity = readById(newEntity.getId()).get();
            update(entity, newEntity);
            updated = entityManager.merge(entity);
        }
        entityManager.flush();
        return updated;
    }

    abstract void update(T entity, T newEntity);

    @Override
    public boolean deleteById(K id) {
        T entity = entityManager.getReference(this.entityClass, id);
        entityManager.remove(entity);
        return true;
    }

    @Override
    public boolean existById(K id) {
        EntityType<T> entityType = entityManager.getMetamodel().entity(entityClass);
        String idFieldName = entityType.getId(idClass).getName();

        Query query = entityManager.createQuery("SELECT COUNT(*) FROM " +
                entityClass.getSimpleName() + " WHERE " + idFieldName + " = ?1")
                .setParameter(1, id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public T getReference(K id) {
        return entityManager.getReference(this.entityClass, id);
    }
}
