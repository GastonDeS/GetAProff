package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;

import javax.persistence.TypedQuery;

public interface BasePaginationDao<T> {
    Page<T> listBy(TypedQuery<T> query, PageRequest pageRequest);
    int getTotalPageCount(TypedQuery<T> query, Integer pageSize);
}
