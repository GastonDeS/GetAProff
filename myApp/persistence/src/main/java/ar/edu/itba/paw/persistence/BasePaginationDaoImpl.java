package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.BasePaginationDao;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;

import javax.persistence.Query;
import java.util.List;

public class BasePaginationDaoImpl<T> implements BasePaginationDao<T> {

    public Page<T> listBy(Query query, PageRequest pageRequest) {
        int totalPages = getTotalPageCount(query, pageRequest.getPageSize());
        query.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return new Page<>((List<T>) query.getResultList(), pageRequest.getPageSize(), pageRequest.getPage(), totalPages);
    }

    public int getTotalPageCount(Query query, Integer pageSize) {
        int size = query.getResultList().size();
        return size / pageSize + (size%pageSize > 0 ? 1: 0);
    }
}
