package ru.saptan.yandextranslator.screens.translate;


import android.util.Log;

import java.util.Map;

import ru.saptan.yandextranslator.data.datasource.remote.responce.SupportLanguagesResponse;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import ru.saptan.yandextranslator.interactors.TranslateInteractor;
import ru.saptan.yandextranslator.models.Language;
import ru.saptan.yandextranslator.mvp.MvpBasePresenter;
import ru.saptan.yandextranslator.navigation.NameScreens;
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

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadSupportLanguage();
    }

    /**
     * Метод вызывается тогда, когда Presenter пережил уничтожение старого View и привязывется к новому
     */
    @Override
    protected void onRecreated() {
        Log.d(TAG, TAG_CLASS + ": onRecreated()");

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
        Log.d(TAG, TAG_CLASS + ": setInputtedText() -> " + inputtedText);
        viewModel.setInputtedText(inputtedText);
    }

    /**
     * Выполнить перевод текста
     */
    @Override
    public void translateText() {


        Log.d(TAG, TAG_CLASS + ": translateText()");

        Subscription subscription = translateInteractor.getTranslation(viewModel.getInputtedText(), viewModel.getDirectionTranslation())
                .subscribe(new Subscriber<TranslationResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, TAG_CLASS + ": translateText() -> onCompleted()");
                        getView().showTranslatedText(viewModel.getTranslatedText());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, TAG_CLASS + ": onError()");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TranslationResponse translationResponse) {
                        Log.d(TAG, TAG_CLASS + ": translateText() -> onNext() -> " + translationResponse.getTranslatedText().get(0));
                        viewModel.setTranslatedText(translationResponse.getTranslatedText().get(0));
                    }
                });

        compositeSubscription.add(subscription);

    }

    /**
     * Выбрать другой язык текста
     */
    @Override
    public void chooseInputLanguage() {
        Log.d(TAG, TAG_CLASS + ": chooseInputLanguage()");
        router.navigateTo(NameScreens.CHOICE_LANGUAGE, true);
    }

    /**
     * Выбрать другой язык перевода
     */
    @Override
    public void chooseOutputLanguage() {
        Log.d(TAG, TAG_CLASS + ": chooseInputLanguage()");
        router.navigateTo(NameScreens.CHOICE_LANGUAGE, false);
    }

    /**
     * Загрузить список поддерживаемых языков
     */
    @Override
    public void loadSupportLanguage() {
        Log.d(TAG, TAG_CLASS + ": loadSupportLanguage()");

        Subscription subscription = translateInteractor.getSupportLanguages("ru")
                .subscribe(new Subscriber<SupportLanguagesResponse>() {
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
                    public void onNext(SupportLanguagesResponse supportLanguagesResponse) {

                        for (Map.Entry<String, String> entry : supportLanguagesResponse.getLangs().entrySet()) {
                            Log.d(TAG, TAG_CLASS + ": code = " + entry.getKey() + ". name = " + entry.getValue());
                        }

                    }
                });

        compositeSubscription.add(subscription);
    }

    /**
     * Установить язык для исходного текста
     *
     * @param language - информация о языке (код, название)
     */
    @Override
    public void setInputLanguage(Language language) {
        viewModel.setInputLanguage(language);
    }

    /**
     * Установить язык, на который будет переведен текст
     *
     * @param language - информация о языке (код, название)
     */
    @Override
    public void setTranslatedLanguage(Language language) {
        viewModel.setTranslatedLanguage(language);
    }

    /**
     * Переключить язык перевода. Например, если до вызова этого метода languages = {ru, en},
     * то после будет languages = {en, ru}
     */
    @Override
    public void swapLanguage() {
        Log.d(TAG, TAG_CLASS + ": swapLanguage()");

        viewModel.swapLanguage();
        getView().showInputLanguage(viewModel.getLanguages().get(0));
        getView().showTranslateLanguage(viewModel.getLanguages().get(1));
        // Заменить исходный текст на переведенный
        getView().showInputtedText(viewModel.getInputtedText());
    }

    /**
     * Установить флаг "Автоматическое определение языка исходного текста"
     *
     * @param autoDetermineLanguage - true - язык определяется автоматически,
     *                              false - язык определяется пользователем
     */
    @Override
    public void setAutoDetermineLanguage(boolean autoDetermineLanguage) {
        viewModel.setAutoDetermineLanguage(autoDetermineLanguage);
    }
}
