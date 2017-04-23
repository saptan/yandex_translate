package ru.saptan.yandextranslator.data.repository;


import java.util.List;

import ru.saptan.yandextranslator.data.repository.specifications.Specification;
import rx.Observable;

public interface Repository<T> {

    /**
     * Добавить один объект в БД
     * @param item - объект
     */
    Observable<T> add(T item);

    /**
     * Добавить коллекцию объектов в БД
     * @param items - список объектов
     */
    Observable<List<T>> add(List<T> items);

    /**
     * Обновить один объект в БД
     * @param item - объект
     */
    Observable<T> update(T item);

    /**
     * Удалить один объект из БД
     * @param item - объект
     */
    Observable<T> remove(T item);

    /**
     * Удалить один объект из БД, согласно условию
     * @param specification - спецификация, которая содержит в себе правила удаления объекта
     */
    void remove(Specification specification);

    /**
     * Выполнить запрос к БД, обычно это поиск записи по конкретному условию
     * @param specification - спецификация, которая содержит в себе правила выборки объекта
     * @return список объектов
     */
    Observable<List<T>> query(Specification specification);
}