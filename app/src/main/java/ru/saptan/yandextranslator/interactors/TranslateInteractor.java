package ru.saptan.yandextranslator.interactors;


import ru.saptan.yandextranslator.data.datasource.remote.api.ApiKeyStore;
import ru.saptan.yandextranslator.data.datasource.remote.api.RetrofitApi;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TranslateInteractor {

    /**
     * Получить перевод текста
     * @param textInput - текст, который необходимо перевести
     * @param lang - Направление перевода
     * @return - переведенный текст
     */
    public Observable<TranslationResponse> getTranslation(String textInput, String lang) {

        return RetrofitApi.getInstance().getTranslateService()
                .getTranslation(ApiKeyStore.KEY_API_TRANSLATE, textInput, lang)
                // Все запросы выполнить в фоновом потоке
                .subscribeOn(Schedulers.io())
                // После выполнения запросов вернуть управление в главный поток
                .observeOn(AndroidSchedulers.mainThread());
    }
}
