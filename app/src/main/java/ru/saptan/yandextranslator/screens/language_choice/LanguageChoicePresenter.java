package ru.saptan.yandextranslator.screens.language_choice;


import android.util.Log;

import java.util.List;
import java.util.Locale;

import ru.saptan.yandextranslator.interactors.LanguageInteractor;
import ru.saptan.yandextranslator.models.Language;
import ru.saptan.yandextranslator.mvp.MvpBasePresenter;
import rx.Subscriber;
import rx.Subscription;

public class LanguageChoicePresenter extends MvpBasePresenter<LanguageChoiceView, LanguageChoiseViewModel>
        implements LanguageChoiceContract.Presenter {


    // Интерактор для получения информации о поддерживаемых языках и направлениях переводов
    private LanguageInteractor languageInteractor;


    public LanguageChoicePresenter(LanguageInteractor languageInteractor) {
        super();
        this.languageInteractor = languageInteractor;
        // Создать объект для хранения временных данных, которые необходимо
        // передать из презентера во вью
        viewModel = new LanguageChoiseViewModel();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadSupportLanguage();
    }


    /**
     * Загрузить список поддерживаемых языков
     */
    @Override
    public void loadSupportLanguage() {
        // В ответе список поддерживаемых языков будет перечислен в поле langs
        // вместе с расшифровкой кодов языков. Названия языков будут выведены на языке,
        // код которого соответствует этому параметру.
        String ui = Locale.getDefault().getLanguage();

        Subscription subscription = languageInteractor.getSupportLanguages(ui)
                .subscribe(new Subscriber<List<Language>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, TAG_CLASS + ": onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, TAG_CLASS + ": onError()");
                        Log.d(TAG, TAG_CLASS + e);
                    }

                    @Override
                    public void onNext(List<Language> languages) {
                        Log.d(TAG, TAG_CLASS + ": onNext()");
                    }
                });

        compositeSubscription.add(subscription);
    }
}
