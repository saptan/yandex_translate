package ru.saptan.yandextranslator.data.mappers;


import android.database.Cursor;

import ru.saptan.yandextranslator.data.datasource.local.db.schema.NumericField;
import ru.saptan.yandextranslator.data.entity.Direction;

public class CursorToDirectionMapper implements Mapper<Cursor, Direction> {

    @Override
    public Direction map(Cursor cursor) {
        Direction direction = new Direction();
        direction.setCodeInputLanguage(cursor.getString(NumericField.Direction.CODE_LANG_TEXT));
        direction.setCodeOutputLanguage(cursor.getString(NumericField.Direction.CODE_LANG_TRANSLATION));
        return direction;
    }
}
