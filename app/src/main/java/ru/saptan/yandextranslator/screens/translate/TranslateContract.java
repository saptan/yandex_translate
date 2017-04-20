package ru.saptan.yandextranslator.screens.translate;


import ru.saptan.yandextranslator.mvp.MvpView;

public interface TranslateContract {

    interface View extends MvpView {
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
    }

    interface Presenter {

        /**
         * Сохранить текст, который ввел пользователь
         * @param inputtedText - введеный текст
         */
        void setInputtedText(String inputtedText);

        /**
         * Выполнить перевод текста
         */
        void translateText();
    }

    interface ViewModel {
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

    }



}
