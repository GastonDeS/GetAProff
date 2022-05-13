package ar.edu.itba.paw.models;

import java.util.Collections;
import java.util.List;

public class Page<T> {
    private final List<T> content;
    private final Integer size, total, page;

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

    public Page(Builder<T> builder) {
        this.content = builder.content;
        this.size = builder.size;
        this.page = builder.page;
        this.total = builder.total;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Page<?>)) return false;
        Page<?> aux = (Page<?>) object;
        return aux.getContent().equals(this.content)
                && aux.getPage().equals(this.page)
                && aux.getTotal().equals(this.total)
                && aux.getSize().equals(this.size);
    }

    public static class Builder<T>
    {
        private List<T> content;
        private Integer size;
        private Integer total;
        private Integer page;

        public Builder() {
            this.content = Collections.emptyList();
            this.size = 0;
            this.total = 1;
            this.page = 1;
        }
        public Builder<T> content(List<T> content) {
            this.content = content;
            return this;
        }
        public Builder<T> size(Integer size) {
            this.size = size;
            return this;
        }
        public Builder<T> total(Integer total) {
            this.total = total;
            return this;
        }
        public Builder<T> page(Integer page) {
            this.page = page;
            return this;
        }
        public Page<T> build() {
            return new Page<>(this);
        }
    }
}
