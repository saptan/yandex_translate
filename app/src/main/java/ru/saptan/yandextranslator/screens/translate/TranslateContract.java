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
         * Спрятать карточку с переводом текста
         */
        void hideCardTranslation();
    }

    interface Presenter {
        /**
         * Выполнить перевод текста
         * @param text - текст, который необходмо перевести
         */
        void translateText(String text);
    }


}
