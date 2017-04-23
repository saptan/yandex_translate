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
import ru.saptan.yandextranslator.data.mappers.CursorToLanguageMapper;
import ru.saptan.yandextranslator.data.mappers.LanguageToContentValuesMapper;
import ru.saptan.yandextranslator.data.mappers.Mapper;
import ru.saptan.yandextranslator.data.repository.specifications.Specification;
import ru.saptan.yandextranslator.data.repository.specifications.SqlSpecification;
import ru.saptan.yandextranslator.models.Language;
import rx.Observable;

public class LanguageRepository implements Repository<Language>  {

    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    private final DatabaseHelper databaseHelper;
    private final Mapper<Cursor, Language> toLanguageMapper;
    private final Mapper<Language, ContentValues> toContentValuesMapper;

    public LanguageRepository() {
        // создаем объект для создания и управления версиями БД
        this.databaseHelper = App.getInstance().getDatabaseHelper();
        this.toContentValuesMapper = new LanguageToContentValuesMapper();
        this.toLanguageMapper = new CursorToLanguageMapper();
    }
    /**
     * Добавить один объект в БД
     *
     * @param item - объект
     */
    @Override
    public Observable<Language> add(Language item) {
        return add(Collections.singletonList(item))
                .filter(languages -> !languages.isEmpty())        // Проверить на наличие элементов
                .map(languages -> languages.get(0));              // Получить первый элемент
    }

    /**
     * Добавить коллекцию объектов в БД
     *
     * @param items - список объектов
     */
    @Override
    public Observable<List<Language>> add(List<Language> items) {
        // Открыть БД для записи
        final SQLiteDatabase database = databaseHelper.getWritableDatabase();
        // Открыть транзакцию
        database.beginTransaction();
        try {
            for (Language language : items) {
                // Заполнить объект contentValues данными
                final ContentValues contentValues = toContentValuesMapper.map(language);
                // Вставить эту информацию в БД и получить id добавленной записи
                long id = database.insert(Contract.Table.LANGUAGES, null, contentValues);
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
    public Observable<Language> update(Language item) {
        return null;
    }

    /**
     * Удалить один объект из БД
     *
     * @param item - объект
     */
    @Override
    public Observable<Language> remove(Language item) {
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
    public Observable<List<Language>> query(Specification specification) {
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;

        // подключаемся к БД
        final SQLiteDatabase database = databaseHelper.getReadableDatabase();
        final List<Language> languages = new ArrayList<>();

        try {
            // Выполнить запрос к БД
            final Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), new String[]{});
            // Перебрать все результаты выборки
            if (cursor.moveToFirst()) {
                do {
                    // Наполнить объект данными из курсора
                    Language language = toLanguageMapper.map(cursor);
                    // добавить его в коллекцию результата
                    languages.add(language);
                } while (cursor.moveToNext());
            }

            cursor.close();

            return Observable.just(languages);
        } finally {
            database.close();
        }
    }
}
