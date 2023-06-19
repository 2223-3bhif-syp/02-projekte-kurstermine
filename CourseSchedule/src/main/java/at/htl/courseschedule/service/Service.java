package at.htl.courseschedule.service;

public interface Service<T> {
    void add(T entity);
    void remove(T entity);
    void update(T entity);
}
