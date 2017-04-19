package ru.saptan.yandextranslator.data.datasource.remote.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ru.saptan.yandextranslator.data.datasource.remote.api.ApiKeyStore.API_TRANSLATE_URL;

public class RetrofitApi {

    private static RetrofitApi instance;
    // Базовый URL, который используется как URL по умолчанию для запросов
    // Сервис, содержащий описание запросов к API для получения перевода
    private TranslateService translateService;

    public static RetrofitApi getInstance() {
        if (instance == null) {
            instance = new RetrofitApi();
        }
        return instance;
    }

    // Построить Retrofit только один раз
    private RetrofitApi() {
        buildRetrofit();
    }

    /**
     * Создать сервисы для обращения к API
     */
    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_TRANSLATE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.translateService = retrofit.create(TranslateService.class);
    }

    /**
     * Получить сервис для перевода текст
     *
     * @return - сервис, который описывает запросы к API для получения перевода
     */
    public TranslateService getTranslateService() {
        return this.translateService;
    }

}
