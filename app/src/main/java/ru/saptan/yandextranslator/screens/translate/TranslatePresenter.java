package ru.saptan.yandextranslator.screens.translate;


import android.util.Log;

import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import ru.saptan.yandextranslator.interactors.TranslateInteractor;
import ru.saptan.yandextranslator.mvp.MvpBasePresenter;
import rx.Subscriber;
import rx.Subscription;

public class TranslatePresenter extends MvpBasePresenter<TranslateView>
        implements TranslateContract.Presenter {

    // Интерактор для получения перевода
    private TranslateInteractor translateInteractor;
    // Переведенный текст
    private String translatedText;

    public TranslatePresenter(TranslateInteractor translateInteractor) {
        this.translateInteractor = translateInteractor;
    }

    /**
     * Выполнить перевод текста
     *
     * @param text - текст, который необходмо перевести
     */
    @Override
    public void translateText(String text) {
        Subscription subscription = translateInteractor.getTranslation(text, "ru-en")
                .subscribe(new Subscriber<TranslationResponse>() {
                    @Override
                    public void onCompleted() {
                        getView().showTranslatedText(translatedText);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TranslationResponse translationResponse) {
                        translatedText =  translationResponse.getTranslatedText().get(0);
                    }
                });

        compositeSubscription.add(subscription);

    }
}
