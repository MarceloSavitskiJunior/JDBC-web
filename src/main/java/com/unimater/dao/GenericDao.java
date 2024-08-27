package com.unimater.dao;

import com.unimater.model.Entity;

import java.util.List;

public interface GenericDao<T extends Entity> {

    List<T> getAll();
    T getById(int id);
    T upsert(T product);
    void deleteById(int id);
}
