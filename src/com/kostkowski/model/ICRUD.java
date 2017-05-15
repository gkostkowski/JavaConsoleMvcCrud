package com.kostkowski.model;

import javax.naming.SizeLimitExceededException;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 * Typ T reprezentuje obiekt identyfikujacy element
 * Typ N reprezentuje element (kolekcji) ktora jest zawarta w modelu
 */
public interface ICRUD<T, N> {
    void create(N newElem) throws SizeLimitExceededException;
    void delete(T id);
    N remove(T id);
    void update(T id, N elem) throws SizeLimitExceededException;
    N get(T id);
}
