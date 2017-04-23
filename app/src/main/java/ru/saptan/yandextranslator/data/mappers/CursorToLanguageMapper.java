package ru.saptan.yandextranslator.data.mappers;


import android.database.Cursor;

import ru.saptan.yandextranslator.data.datasource.local.db.schema.NumericField;
import ru.saptan.yandextranslator.models.Language;

public class CursorToLanguageMapper implements Mapper<Cursor, Language> {
    @Override
    public Language map(Cursor cursor) {
        Language language = new Language();
        language.setCode(cursor.getString(NumericField.Language.CODE_LANGUAGE));
        language.setUi(cursor.getString(NumericField.Language.CODE_UI));
        language.setName(cursor.getString(NumericField.Language.NAME));
        return language;
    }
}
