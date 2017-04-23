package ru.saptan.yandextranslator.data.repository;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.saptan.yandextranslator.App;
import ru.saptan.yandextranslator.data.datasource.local.db.DatabaseHelper;
import ru.saptan.yandextranslator.data.datasource.local.db.schema.Contract;
import ru.saptan.yandextranslator.data.entity.Direction;
import ru.saptan.yandextranslator.data.mappers.CursorToDirectionMapper;
import ru.saptan.yandextranslator.data.mappers.DirectionToContentValuesMapper;
import ru.saptan.yandextranslator.data.mappers.Mapper;
import ru.saptan.yandextranslator.data.repository.specifications.Specification;
import ru.saptan.yandextranslator.data.repository.specifications.SqlSpecification;
import rx.Observable;

public class DirectionRepository implements Repository<Direction> {


    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    private final DatabaseHelper databaseHelper;
    private final Mapper<Cursor, Direction> toDirectionMapper;
    private final Mapper<Direction, ContentValues> toContentValuesMapper;

    public DirectionRepository() {
        // создаем объект для создания и управления версиями БД
        this.databaseHelper = App.getInstance().getDatabaseHelper();
        this.toContentValuesMapper = new DirectionToContentValuesMapper();
        this.toDirectionMapper = new CursorToDirectionMapper();
    }

    /**
     * Добавить один объект в БД
     *
     * @param item - объект
     */
    @Override
    public Observable<Direction> add(Direction item) {
        return add(Collections.singletonList(item))
                .filter(directions -> !directions.isEmpty())        // Проверить на наличие элементов
                .map(directions -> directions.get(0));              // Получить первый элемент
    }

    /**
     * Добавить коллекцию объектов в БД
     *
     * @param items - список объектов
     */
    @Override
    public Observable<List<Direction>> add(List<Direction> items) {
        // Открыть БД для записи
        final SQLiteDatabase database = databaseHelper.getWritableDatabase();
        // Открыть транзакцию
        database.beginTransaction();
        try {
            for (Direction direction : items) {
                // Заполнить объект contentValues данными
                final ContentValues contentValues = toContentValuesMapper.map(direction);
                // Вставить эту информацию в БД и получить id добавленной записи
                long id = database.insert(Contract.Table.DIRECTIONS, null, contentValues);
                // Обновить локальный id
                direction.setId(id);


                Log.d(TAG, TAG_CLASS + ": add direction -> "
                        + direction.getCodeInputLanguage()
                        + " : "
                        + direction.getCodeOutputLanguage());
            }

            // Указать что транзакция была успешно завершена
            database.setTransactionSuccessful();

            return Observable.just(items);
        } finally {
            // Зафиксировать транзакцию
            database.endTransaction();
            // Закрыть соединение с БД
            database.close();
        }
    }

    /**
     * Обновить один объект в БД
     *
     * @param item - объект
     */
    @Override
    public Observable<Direction> update(Direction item) {
        return null;
    }

    /**
     * Удалить один объект из БД
     *
     * @param item - объект
     */
    @Override
    public Observable<Direction> remove(Direction item) {
        return null;
    }

    /**
     * Удалить один объект из БД, согласно условию
     *
     * @param specification - спецификация, которая содержит в себе правила удаления объекта
     */
    @Override
    public void remove(Specification specification) {

    }

    /**
     * Выполнить запрос к БД, обычно это поиск записи по конкретному условию
     *
     * @param specification - спецификация, которая содержит в себе правила выборки объекта
     * @return список объектов
     */
    @Override
    public Observable<List<Direction>> query(Specification specification) {
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;

        // подключаемся к БД
        final SQLiteDatabase database = databaseHelper.getReadableDatabase();
        final List<Direction> directions = new ArrayList<>();

        try {
            // Выполнить запрос к БД
            final Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), new String[]{});
            // Перебрать все результаты выборки
            if (cursor.moveToFirst()) {
                do {
                    // Наполнить объект данными из курсора
                    Direction direction = toDirectionMapper.map(cursor);
                    // добавить его в коллекцию результата
                    directions.add(direction);
                } while (cursor.moveToNext());
            }

            cursor.close();

            return Observable.just(directions);
        } finally {
            database.close();
        }
    }
}
