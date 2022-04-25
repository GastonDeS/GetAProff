package ar.edu.itba.paw.models;

import java.util.Collections;
import java.util.List;

public class Page<T> {
    List<T> content;
    Integer size;
    Integer total;
    Integer page;

    public Page() {
        this.content = Collections.emptyList();
        this.size = 0;
        this.total = 1;
        this.page = 1;
    }

    public Page(List<T> content, Integer size, Integer page, Integer total) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.total = total;
    }

    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }
}
