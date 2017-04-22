package ru.saptan.yandextranslator.data.datasource.remote.responce;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupportLanguagesResponse {

    // Направления в формате "код с какого языка" - " на какой" (ru-en)
    @SerializedName("dirs")
    @Expose
    private List<String> dirs = new ArrayList<>();

    // Информация о поддерживаемых языках в формате "код" : "Название" (ru : Русский)
    @SerializedName("langs")
    @Expose
    private HashMap<String, String> langs;


    public HashMap<String, String> getLangs() {
        return langs;
    }

    public void setLangs(HashMap<String, String> langs) {
        this.langs = langs;
    }



    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }



}
