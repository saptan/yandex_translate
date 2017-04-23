package ru.saptan.yandextranslator.interactors;

import ru.saptan.yandextranslator.base.BaseInteractor;
import ru.saptan.yandextranslator.data.datasource.remote.api.ApiKeyStore;
import ru.saptan.yandextranslator.data.datasource.remote.api.RetrofitApi;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import rx.Observable;

public class TranslateInteractor extends BaseInteractor {

    /**
     * Получить перевод текста
     *
     * @param textInput - текст, который необходимо перевести
     * @param lang      - Направление перевода
     * @return - переведенный текст
     */
    public Observable<TranslationResponse> getTranslation(String textInput, String lang) {
        return RetrofitApi.getInstance().getTranslateService()
                .getTranslation(ApiKeyStore.KEY_API_TRANSLATE, textInput, lang)
                .compose(applySchedulers());
    }
}
