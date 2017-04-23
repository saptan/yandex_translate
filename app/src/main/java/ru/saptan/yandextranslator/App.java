package ru.saptan.yandextranslator;

import android.app.Application;

import ru.saptan.yandextranslator.data.datasource.local.db.DatabaseHelper;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;


public class App extends Application {

    private static App instance;
    // Класс для работы с БД
    private DatabaseHelper databaseHelper;
    // Класс для управления переходами между экранами
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databaseHelper = new DatabaseHelper(this);
        cicerone = Cicerone.create();
    }

    public static App getInstance() {
        return instance;
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
