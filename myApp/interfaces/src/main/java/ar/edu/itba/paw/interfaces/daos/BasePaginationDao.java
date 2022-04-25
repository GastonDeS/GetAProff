package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;

import javax.persistence.Query;

public interface BasePaginationDao<T> {
    Page<T> listBy(Query query, PageRequest pageRequest);
    int getTotalPageCount(Query query, Integer pageSize);
}
