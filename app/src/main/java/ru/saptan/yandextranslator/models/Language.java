package ru.saptan.yandextranslator.models;


public class Language {

    // Код языка - https://tech.yandex.ru/translate/doc/dg/concepts/api-overview-docpage/#languages
    private String code;
    // Полное название
    private String name;
    // Язык интерфейса, т.е. на каком языке будет отображаться название языка.
    private String ui;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }


}
