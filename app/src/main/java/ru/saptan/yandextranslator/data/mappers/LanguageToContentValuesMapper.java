package ru.saptan.yandextranslator.data.mappers;


import android.content.ContentValues;

import ru.saptan.yandextranslator.data.datasource.local.db.schema.Contract;
import ru.saptan.yandextranslator.models.Language;

public class LanguageToContentValuesMapper implements Mapper<Language, ContentValues>  {
    @Override
    public ContentValues map(Language language) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.LanguageField.CODE_LANGUAGE, language.getCode());
        cv.put(Contract.LanguageField.CODE_UI, language.getUi());
        cv.put(Contract.LanguageField.NAME, language.getName());
        return cv;
    }
}
