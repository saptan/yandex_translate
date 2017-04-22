package ru.saptan.yandextranslator.screens.translate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.saptan.yandextranslator.models.Language;

public class TranslateViewModel implements TranslateContract.ViewModel {

    // Введенный пользователем текст
    private String inputtedText = "";
    // Переведенный текст
    private String translatedText = "";

    /*
    Направление перевода.
    Может задаваться одним из следующих способов:
        * В виде пары кодов языков («с какого»-«на какой»), разделенных дефисом. Например, en-ru
        обозначает перевод с английского на русский.
        * В виде кода конечного языка (например ru). В этом случае сервис пытается определить
        исходный язык автоматически.
     */
    private String directionTranslation;
    // Информация о языках, которые используются для перевода
    private List<Language> languages;
    // Определять язык исходного текста автоматически
    private boolean autoDetermineLanguage;

    public TranslateViewModel() {
        languages = new ArrayList<>();

        // Настроить направление перевода по умолчанию
        // Язык исходного языка
        Language inputLanguage = new Language();
        inputLanguage.setCode("ru");
        inputLanguage.setName("Русский");
        languages.add(inputLanguage);

        // Конечный язык
        Language outputLanguage = new Language();
        outputLanguage.setCode("en");
        outputLanguage.setName("Английский");
        languages.add(outputLanguage);

        // Отключить автоматическое определение исходного текста
        setAutoDetermineLanguage(false);
        // Сформировать направление перевода
        setDirectionTranslation();

    }

    /**
     * Получить текст, который ввел пользователь
     *
     * @return - введеный текст
     */
    @Override
    public String getInputtedText() {
        return inputtedText;
    }

    /**
     * Сохранить текст, который ввел пользователь
     *
     * @param inputtedText - введеный текст
     */
    @Override
    public void setInputtedText(String inputtedText) {
        this.inputtedText = inputtedText;
    }

    /**
     * Получить текст, который был переведен
     *
     * @return - переведенный текст
     */
    @Override
    public String getTranslatedText() {
        return translatedText;
    }

    /**
     * Сохранить текст, который был переведен
     *
     * @param translatedText - переведенный текст
     */
    @Override
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    /**
     * Установить язык для исходного текста
     *
     * @param language - информация о языке(код, название)
     */
    @Override
    public void setInputLanguage(Language language) {
        languages.set(0, language);
        setDirectionTranslation();
    }

    /**
     * Установить язык, на который будет переведен текст
     *
     * @param language - информация о языке(код, название)
     */
    @Override
    public void setTranslatedLanguage(Language language) {
        languages.set(1, language);
        setDirectionTranslation();
    }

    /**
     * Сформировать направление перевода
     */
    private void setDirectionTranslation() {
        // Если язык исхоного текста установлен пользователем (не определяется автоматически)
        if (!isAutoDetermineLanguage())
            // То направление состоит из пары кодов языков, разделенных дефисом
            directionTranslation = languages.get(0).getCode() + "-" + languages.get(1).getCode();
        else
            // Иначе направление состоит из кода конечного языка
            directionTranslation = languages.get(1).getCode();
    }


    /**
     * Получить используемые языки направления перевода
     *
     * @return - информация о языках (код, название)
     */
    @Override
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * Получить направление перевода
     *
     * @return - строка в виде пары кодов языков "ru-en", либо в виде кода конечного языка "ru"
     */
    @Override
    public String getDirectionTranslation() {
        return directionTranslation;
    }

    /**
     * Переключить язык перевода. Например, если до вызова этого метода languages = {ru, en},
     * то после будет languages = {en, ru}
     */
    @Override
    public void swapLanguage() {
        // Поменять местами языки
        Collections.swap(languages, 0, 1);
        // Заново сформировать направление
        setDirectionTranslation();

        // Поменять местами переведенный текст с исходным
        String temp = inputtedText;
        inputtedText = translatedText;
        translatedText = temp;
    }

    /**
     * Проверить, установлен ли флаг "Автоматическое определение языка исходного текста"
     *
     * @return true - определяется автоматически, false - определяется пользователем
     */
    public boolean isAutoDetermineLanguage() {
        return autoDetermineLanguage;
    }

    /**
     * Установить флаг "Автоматическое определение языка исходного текста"
     *
     * @param autoDetermineLanguage - true - язык определяется автоматически,
     *                              false - язык определяется пользователем
     */
    public void setAutoDetermineLanguage(boolean autoDetermineLanguage) {
        this.autoDetermineLanguage = autoDetermineLanguage;
    }
}
