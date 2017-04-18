package ru.saptan.yandextranslator.data.datasource.remote.responce;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslationResponse {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> translatedText = new ArrayList<String>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(List<String> translatedText) {
        this.translatedText = translatedText;
    }

}
