package com.jd.jr.pmock.server.query;


public class Query<T> {
    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
