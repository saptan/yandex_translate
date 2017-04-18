package ru.saptan.yandextranslator.data.datasource.remote.api;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import rx.Observable;

public interface TranslateService {

    /**
     * Перевод текста на заданный язык.
     * @param key - API-ключ
     * @param text -    Текст, который необходимо перевести.
     *                  Для POST-запросов максимальный размер передаваемого текста
     *                  составляет 10 000 символов
     * @param lang -    Направление перевода. Может задаваться одним из следующих способов:
                        - В виде пары кодов языков («с какого»-«на какой»), разделенных дефисом.
                          Например, en-ru обозначает перевод с английского на русский.
                        - В виде кода конечного языка (например ru). В этом случае сервис пытается
                          определить исходный язык автоматически.
     * @return - ответ возвращается в формате JSON.
     */
    @FormUrlEncoded
    @POST("translate")
    Observable<TranslationResponse> getTranslation(
            @Field("key") String key,
            @Field("text") String text,
            @Field("lang") String lang);
}
