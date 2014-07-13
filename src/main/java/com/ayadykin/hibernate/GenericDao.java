package com.ayadykin.hibernate;

import java.io.Serializable;
import java.util.List;


public interface GenericDao <T, PK extends Serializable> {

    List<T> getAll();
    T get(PK id);
    boolean exists(PK id);
    T save(T object);
    void remove(PK id);
}
