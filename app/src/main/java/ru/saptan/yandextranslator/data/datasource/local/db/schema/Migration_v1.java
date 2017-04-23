package ru.saptan.yandextranslator.data.datasource.local.db.schema;


public class Migration_v1 {

    public static final String CREATE_DIRECTIONS =
            "create table directions (" +
                    "code_lang_text text, " +
                    "code_lang_translation text, " +
                    "unique (code_lang_text, code_lang_translation))";

    public static final String CREATE_LANGUAGES =
            "create table languages (" +
                    "code_language text, " +
                    "code_ui text, " +
                    "name text, " +
                    "unique (code_language, code_ui) )";
}
