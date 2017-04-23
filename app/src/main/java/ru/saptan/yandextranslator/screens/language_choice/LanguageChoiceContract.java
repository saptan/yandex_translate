package ru.saptan.yandextranslator.screens.language_choice;


import ru.saptan.yandextranslator.mvp.MvpView;

public interface LanguageChoiceContract {

    interface View extends MvpView {

    }

    interface Presenter {
        /**
         * Загрузить список поддерживаемых языков
         */
        void loadSupportLanguage();

    }
}
