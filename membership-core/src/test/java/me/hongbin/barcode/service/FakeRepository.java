package me.hongbin.barcode.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

public abstract class FakeRepository<S, ID> implements JpaRepository<S, ID> {

    public final Map<Long, S> map = new HashMap<>();
    private Long id = 0L;

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> S1 saveAndFlush(S1 entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> List<S1> saveAllAndFlush(Iterable<S1> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public S getOne(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public S getById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public S getReferenceById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> List<S1> findAll(Example<S1> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> List<S1> findAll(Example<S1> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> List<S1> saveAll(Iterable<S1> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<S> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public List<S> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> S1 save(S1 entity) {
        id++;
        map.put(id, entity);

        try {
            var declaredField = entity.getClass().getDeclaredField("id");
            declaredField.setAccessible(true);
            declaredField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
        }

        return entity;
    }

    @Override
    public Optional<S> findById(ID id) {
        return Optional.ofNullable(map.get((Long)id));
    }

    @Override
    public boolean existsById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<S> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<S> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> Optional<S1> findOne(Example<S1> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> Page<S1> findAll(Example<S1> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> long count(Example<S1> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S> boolean exists(Example<S1> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1 extends S, R> R findBy(Example<S1> example,
        Function<FluentQuery.FetchableFluentQuery<S1>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
