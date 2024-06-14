package com.cristian.carrito.controllers;
import java.util.List;

public interface DaoInterface<T> {

    T get(T t);
    List<T> getAll();
    void save(T t);
    void update(T t, String[] params);
    void delete(T t);

}
