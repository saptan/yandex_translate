package ru.saptan.yandextranslator.data.datasource.local.db.schema;


public class Contract {

    // Названия таблиц
    public interface Table {

        String DIRECTIONS = "directions";
        String LANGUAGES = "languages";

    }

    // Доступные направления перевода
    public interface DirectionField {
        String CODE_LANG_TEXT = "code_lang_text";
        String CODE_LANG_TRANSLATION = "code_lang_translation";

    }

    // Информация о доступных языках
    public interface LanguageField {
        // Код языка
        String CODE_LANGUAGE = "code_language";

        // Язык интерфейса, т.е. на каком языке будет отображаться название языка.
        // При обращении к API Яндекс.Переводчика используется в качестве параметра "ui"
        // Экспериментальным путем было выяснено что параметр ui может принимать 4 значения:
        // русский, украинский, турецкей и английский (для всех остальных используется по умолчанию)
        String CODE_UI = "code_ui";
        // Название
        String NAME = "name";




    }
}
