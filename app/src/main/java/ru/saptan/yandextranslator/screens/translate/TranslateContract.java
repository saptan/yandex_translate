package ru.saptan.yandextranslator.screens.translate;


import java.util.List;

import ru.saptan.yandextranslator.models.Language;
import ru.saptan.yandextranslator.mvp.MvpView;

public interface TranslateContract {

    interface View {
        /**
         * Сообщить View о том, что содержимое EditText было изменено
         * @param text - текст, который ввел пользователь, чтобы получить его перевод
         */
        void textChanged(String text);

        /**
         * Отобразить перевод
         * @param text - переведенный текст
         */
        void showTranslatedText(String text);

        /**
         * Отобразить текст, который ввел пользователь
         * Данный метод вызывается после изменения конфигурации, например, поворота экрана
         * @param text - введеный ранее текст
         */
        void showInputtedText(String text);

        /**
         * Спрятать карточку с переводом текста
         */
        void hideCardTranslation();

        /**
         * Отобразить в Toolbar название языка для исходного текста
         * @param language - информация о языке
         */
        void showInputLanguage(Language language);

        /**
         * Отобразить в Toolbar название языка для переведенного текста
         * @param language - информация о языке
         */
        void showTranslateLanguage(Language language);


    }

    interface Presenter extends SettingDirectionTranslation {

        /**
         * Сохранить текст, который ввел пользователь
         * @param inputtedText - введеный текст
         */
        void setInputtedText(String inputtedText);

        /**
         * Выполнить перевод текста
         */
        void translateText();

        /**
         * Выбрать другой язык текста
         */
        void chooseInputLanguage();

        /**
         * Выбрать другой язык перевода
         */
        void chooseOutputLanguage();

    }

    interface ViewModel extends SettingDirectionTranslation {
        /**
         * Получить текст, который ввел пользователь
         * @return - введеный текст
         */
        String getInputtedText();

        /**
         * Сохранить текст, который ввел пользователь
         * @param inputtedText - введеный текст
         */
        void setInputtedText(String inputtedText);

        /**
         * Получить текст, который был переведен
         * @return - переведенный текст
         */
        String getTranslatedText();

        /**
         * Сохранить текст, который был переведен
         * @param translatedText - переведенный текст
         */
        void setTranslatedText(String translatedText);

        /**
         * Получить используемые языки направления перевода
         * @return - информация о языках (код, название)
         */
        List<Language> getLanguages();

        /**
         * Получить направление перевода
         * @return - строка в виде пары кодов языков "ru-en", либо в виде кода конечного языка "ru"
         */
        String getDirectionTranslation();

        /**
         * Проверить, установлен ли флаг "Автоматическое определение языка исходного текста"
         * @return true - определяется автоматически, false - определяется пользователем
         */
        boolean isAutoDetermineLanguage();



    }

    /**
     *  Контракт для настройки направления перевода
     */
    interface SettingDirectionTranslation {

        /**
         * Установить язык для исходного текста
         * @param language - информация о языке (код, название)
         */
        void setInputLanguage(Language language);

        /**
         * Установить язык, на который будет переведен текст
         * @param language - информация о языке (код, название)
         */
        void setTranslatedLanguage(Language language);

        /**
         * Переключить язык перевода. Например, если до вызова этого метода languages = {ru, en},
         * то после будет languages = {en, ru}
         */
        void swapLanguage();

        /**
         * Установить флаг "Автоматическое определение языка исходного текста"
         * @param autoDetermineLanguage - true - язык определяется автоматически,
         *                              false - язык определяется пользователем
         */
        void setAutoDetermineLanguage(boolean autoDetermineLanguage);
    }






}
