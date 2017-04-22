package ru.saptan.yandextranslator.interactors;


import android.util.Log;

import ru.saptan.yandextranslator.base.BaseInteractor;
import ru.saptan.yandextranslator.data.datasource.remote.api.ApiKeyStore;
import ru.saptan.yandextranslator.data.datasource.remote.api.RetrofitApi;
import ru.saptan.yandextranslator.data.datasource.remote.responce.SupportLanguagesResponse;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TranslateInteractor extends BaseInteractor {

    /**
     * Получить перевод текста
     * @param textInput - текст, который необходимо перевести
     * @param lang - Направление перевода
     * @return - переведенный текст
     */
    public Observable<TranslationResponse> getTranslation(String textInput, String lang) {
        return RetrofitApi.getInstance().getTranslateService()
                .getTranslation(ApiKeyStore.KEY_API_TRANSLATE, textInput, lang)
               .compose(applySchedulers());
    }


    /**
     * Получить список направлений перевода, поддерживаемых сервисом Яндекс.Переводчик.
     * @param ui - код языка, на котором необходимо предоставить полное название всех поддерживаемых языков
     * @return
     */
    public Observable<SupportLanguagesResponse> getSupportLanguages(String ui) {
        return RetrofitApi.getInstance().getTranslateService()
                .getSupportLanguages(ApiKeyStore.KEY_API_TRANSLATE, ui)
                .compose(applySchedulers());
    }

}
