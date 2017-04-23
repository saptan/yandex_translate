package ru.saptan.yandextranslator.interactors.mappers;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.saptan.yandextranslator.models.Language;

public abstract class ResponseToLanguageMapper {

    public static List<Language> map(HashMap<String, String> supportLanguages, String ui) {

        List<Language> languages = new ArrayList<>();

        for (Map.Entry<String, String> entry : supportLanguages.entrySet()) {

            Language language = new Language();
            language.setCode(entry.getKey());
            language.setName(entry.getValue());
            language.setUi(ui);

            languages.add(language);
        }

        return languages;
    }
}
