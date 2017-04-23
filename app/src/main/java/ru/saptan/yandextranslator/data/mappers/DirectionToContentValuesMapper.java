package ru.saptan.yandextranslator.data.mappers;


import android.content.ContentValues;

import ru.saptan.yandextranslator.data.datasource.local.db.schema.Contract;
import ru.saptan.yandextranslator.data.entity.Direction;

public class DirectionToContentValuesMapper  implements Mapper<Direction, ContentValues> {

    @Override
    public ContentValues map(Direction direction) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.DirectionField.CODE_LANG_TEXT, direction.getCodeInputLanguage());
        cv.put(Contract.DirectionField.CODE_LANG_TRANSLATION, direction.getCodeOutputLanguage());
        return cv;
    }
}
