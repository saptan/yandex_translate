package ru.saptan.yandextranslator.data.datasource.local.db.schema;


public class NumericField {

    public interface Direction {
        int CODE_LANG_TEXT = 0;
        int CODE_LANG_TRANSLATION = 1;
    }

    public interface NumericFieldBase {
        int CODE_LANGUAGE = 0;
        int UI_RU = 1;
        int UI_UK = 2;
        int UI_TR = 3;
        int UI_EN = 4;
    }
}
