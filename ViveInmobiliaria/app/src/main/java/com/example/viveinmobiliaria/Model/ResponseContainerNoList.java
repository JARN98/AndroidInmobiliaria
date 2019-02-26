package com.example.viveinmobiliaria.Model;

public class ResponseContainerNoList<T> {
    private T rows;
    private long count;

    public ResponseContainerNoList(T rows, long count) {
        this.rows = rows;
        this.count = count;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseContainerNoList<?> that = (ResponseContainerNoList<?>) o;

        if (count != that.count) return false;
        return rows != null ? rows.equals(that.rows) : that.rows == null;
    }

    @Override
    public int hashCode() {
        int result = rows != null ? rows.hashCode() : 0;
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
