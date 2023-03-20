package ru.schedule.DAO;

import ru.schedule.models.CommonEntity;

public interface CommonDAO<T extends CommonEntity<ID>, ID>{
    void add(T entity);
}
