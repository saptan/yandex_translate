package ru.saptan.yandextranslator.data.datasource.remote.api;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.saptan.yandextranslator.data.datasource.remote.responce.SupportLanguagesResponse;
import ru.saptan.yandextranslator.data.datasource.remote.responce.TranslationResponse;
import rx.Observable;

public interface TranslateService {

    /**
     * Перевод текста на заданный язык.
     *
     * @param key  - API-ключ
     * @param text -    Текст, который необходимо перевести.
     *             Для POST-запросов максимальный размер передаваемого текста
     *             составляет 10 000 символов
     * @param lang -    Направление перевода. Может задаваться одним из следующих способов:
     *             - В виде пары кодов языков («с какого»-«на какой»), разделенных дефисом.
     *             Например, en-ru обозначает перевод с английского на русский.
     *             - В виде кода конечного языка (например ru). В этом случае сервис пытается
     *             определить исходный язык автоматически.
     * @return - ответ от сервиса, содержащий переведенный текст.
     */
    @FormUrlEncoded
    @POST("translate")
    Observable<TranslationResponse> getTranslation(
            @Field("key") String key,
            @Field("text") String text,
            @Field("lang") String lang);


    /**
     * Получить список направлений перевода, поддерживаемых сервисом Яндекс.Переводчик.
     *
     * @param key - API-ключ
     * @param ui  - Обязательный параметр.
     *            В ответе список поддерживаемых языков будет перечислен в поле langs вместе с
     *            расшифровкой кодов языков. Названия языков будут выведены на языке, код
     *            которого соответствует этому параметру.
     *
     *            Экспериментальным путем выяснено, что ui может принимать значения {ru. en, tr, uk}
     *            , т.е. русский, английский, турецкий и украинский. При этом по умолчанию для всех
     *            остальных случаев используется en - английский язык
     * @return - ответ от сервиса, содержащий список поддерживаемых языков.
     */
    @FormUrlEncoded
    @POST("getLangs")
    Observable<SupportLanguagesResponse> getSupportLanguages(
            @Field("key") String key,
            @Field("ui") String ui);
}
