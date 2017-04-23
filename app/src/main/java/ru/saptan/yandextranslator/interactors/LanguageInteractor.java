package ru.saptan.yandextranslator.interactors;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.saptan.yandextranslator.base.BaseInteractor;
import ru.saptan.yandextranslator.data.datasource.remote.api.ApiKeyStore;
import ru.saptan.yandextranslator.data.datasource.remote.api.RetrofitApi;
import ru.saptan.yandextranslator.data.datasource.remote.responce.SupportLanguagesResponse;
import ru.saptan.yandextranslator.data.entity.Direction;
import ru.saptan.yandextranslator.data.repository.DirectionRepository;
import ru.saptan.yandextranslator.interactors.mappers.ResponseToDirectionMapper;
import ru.saptan.yandextranslator.models.Language;
import rx.Observable;

public class LanguageInteractor extends BaseInteractor {

    private SupportLanguagesResponse response;
    private DirectionRepository directionRepository;

    public LanguageInteractor(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    /**
     * Получить список направлений перевода, поддерживаемых сервисом Яндекс.Переводчик.
     *
     * @param ui - код языка, на котором необходимо предоставить полное название всех поддерживаемых языков
     * @return
     */
    public Observable<List<Language>> getSupportLanguages(String ui) {
        return RetrofitApi.getInstance().getTranslateService()
                .getSupportLanguages(ApiKeyStore.KEY_API_TRANSLATE, ui)
                // Сохранить ответ, полученный от сервера
                .doOnNext(this::saveResponse)
                .flatMap(response -> directionRepository.add(ResponseToDirectionMapper.map(response.getDirs())))
                .flatMap(directions -> addLanguage(mapperToLanguage(response.getLangs())))
                //.map(response -> mapperToLanguage(response.getLangs()))
                .compose(applySchedulers());
    }

    private void saveResponse(SupportLanguagesResponse response) {
        Log.d(TAG, TAG_CLASS + ": doOnNext -> saveResponse");
        this.response = response;
    }

    private Observable<List<Language>> addLanguage(List<Language> items) {

        for (Language language : items) {
            //Log.d(TAG, TAG_CLASS + ": add language -> " + language.getCode() + " : " + language.getName());
        }
        return Observable.just(items);

    }
    private List<Language> mapperToLanguage(HashMap<String, String> supportLanguages) {
        Log.d(TAG, TAG_CLASS + ": mapperToLanguage -> ");

        List<Language> languages = new ArrayList<>();

        for (Map.Entry<String, String> entry : supportLanguages.entrySet()) {

            Language language = new Language();
            language.setCode(entry.getKey());
            language.setName(entry.getValue());

            languages.add(language);
        }

        return languages;
    }
}
