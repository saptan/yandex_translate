package ru.saptan.yandextranslator.data.datasource.local.db.schema;


public class NumericField {

    public interface Direction {
        int CODE_LANG_TEXT = 0;
        int CODE_LANG_TRANSLATION = 1;
    }

    public interface Language {
        int CODE_LANGUAGE = 0;
        int CODE_UI = 1;
        int NAME = 2;
    }
}
