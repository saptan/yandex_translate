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
import ru.saptan.yandextranslator.data.repository.LanguageRepository;
import ru.saptan.yandextranslator.interactors.mappers.ResponseToDirectionMapper;
import ru.saptan.yandextranslator.interactors.mappers.ResponseToLanguageMapper;
import ru.saptan.yandextranslator.models.Language;
import rx.Observable;

public class LanguageInteractor extends BaseInteractor {

    private SupportLanguagesResponse response;
    private DirectionRepository directionRepository;
    private LanguageRepository languageRepository;

    public LanguageInteractor(DirectionRepository directionRepository, LanguageRepository languageRepository) {
        this.directionRepository = directionRepository;
        this.languageRepository = languageRepository;
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
                // Добавить все направления перевода в БД
                .flatMap(response -> directionRepository.add(ResponseToDirectionMapper.map(response.getDirs())))
                // Получить информацию о всех языках, установив для каждого значение параметра ui
                .flatMap(directions -> Observable.just(ResponseToLanguageMapper.map(response.getLangs(), ui)))
                // Добавить языки в БД
                .flatMap(languages -> languageRepository.add(languages))
                .compose(applySchedulers());
    }

    private void saveResponse(SupportLanguagesResponse response) {
        this.response = response;
    }



}
