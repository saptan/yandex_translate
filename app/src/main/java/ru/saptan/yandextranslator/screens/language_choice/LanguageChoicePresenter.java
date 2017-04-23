package ru.saptan.yandextranslator.screens.language_choice;


import android.util.Log;

import java.util.List;

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
        Log.d(TAG, TAG_CLASS + ": loadSupportLanguage()");

        Subscription subscription = languageInteractor.getSupportLanguages("ru")
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
                        for (Language language : languages) {
                            //Log.d(TAG, TAG_CLASS + ": language -> " + language.getCode() + " : " + language.getName());
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }
}
