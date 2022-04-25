package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.BasePaginationDao;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;

import javax.persistence.TypedQuery;

public class BasePaginationDaoImpl<T> implements BasePaginationDao<T> {

    public Page<T> listBy(TypedQuery<T> query, PageRequest pageRequest) {
        int totalPages = getTotalPageCount(query, pageRequest.getPageSize());
        query.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return new Page<T>(query.getResultList(), pageRequest.getPageSize(), pageRequest.getPage(), totalPages);
    }

    public int getTotalPageCount(TypedQuery<T> query, Integer pageSize) {
        return query.getResultList().size() / pageSize;
    }
}
