package com.unimater.service;

import com.unimater.model.Entity;

public interface GenericService<T extends Entity> {

    void inserirDados(T entity);

}
