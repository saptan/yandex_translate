package ru.saptan.yandextranslator.screens.translate;


import android.util.Log;

import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import ru.saptan.yandextranslator.interactors.TranslateInteractor;
import ru.saptan.yandextranslator.mvp.MvpBasePresenter;
import rx.Subscriber;
import rx.Subscription;

public class TranslatePresenter extends MvpBasePresenter<TranslateView, TranslateViewModel>
        implements TranslateContract.Presenter {

    // Интерактор для получения перевода
    private TranslateInteractor translateInteractor;

    public TranslatePresenter(TranslateInteractor translateInteractor) {
        super();
        this.translateInteractor = translateInteractor;
        // Создать объект для хранения временных данных, которые необходимо
        // передать из презентера во вью
        viewModel = new TranslateViewModel();
    }


    /**
     * Метод вызывается тогда, когда Presenter пережил уничтожение старого View и привязывется к новому
     */
    @Override
    protected void onRecreated() {
        // Если дляна ранее введеного текста больше 0
        if (viewModel != null && viewModel.getInputtedText().length() > 0) {
            // Отобразить этот текст
            getView().showInputtedText(viewModel.getInputtedText());
            // Отобразить перевод
            getView().showTranslatedText(viewModel.getTranslatedText());
        }
    }

    /**
     * Сохранить текст, который ввел пользователь
     *
     * @param inputtedText - введеный текст
     */
    @Override
    public void setInputtedText(String inputtedText) {
        viewModel.setInputtedText(inputtedText);
    }

    /**
     * Выполнить перевод текста
     *
     */
    @Override
    public void translateText() {

        Subscription subscription = translateInteractor.getTranslation(viewModel.getInputtedText(), "ru-en")
                .subscribe(new Subscriber<TranslationResponse>() {
                    @Override
                    public void onCompleted() {
                        getView().showTranslatedText(viewModel.getTranslatedText());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TranslationResponse translationResponse) {
                        viewModel.setTranslatedText(translationResponse.getTranslatedText().get(0));
                    }
                });

        compositeSubscription.add(subscription);

    }
}
