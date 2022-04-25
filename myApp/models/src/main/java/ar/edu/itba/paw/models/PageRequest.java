package ar.edu.itba.paw.models;

public class PageRequest {
    private final Integer page;
    private final Integer pageSize;
    private static final int MIN_PAGE_COUNT = 1;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 50;
    public PageRequest(Integer page, Integer pageSize) {
        if(pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE || page < MIN_PAGE_COUNT)
            throw new IllegalArgumentException();
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
