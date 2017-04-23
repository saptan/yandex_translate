package ru.saptan.yandextranslator.data.entity;


public class Direction {
    // id записи
    private long id;
    // Код языка исходного текста
    private String codeInputLanguage;
    // Код языка перевода
    private String codeOutputLanguage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodeInputLanguage() {
        return codeInputLanguage;
    }

    public void setCodeInputLanguage(String codeInputLanguage) {
        this.codeInputLanguage = codeInputLanguage;
    }

    public String getCodeOutputLanguage() {
        return codeOutputLanguage;
    }

    public void setCodeOutputLanguage(String codeOutputLanguage) {
        this.codeOutputLanguage = codeOutputLanguage;
    }


}
