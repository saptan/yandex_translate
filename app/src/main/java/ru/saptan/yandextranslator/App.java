package ru.saptan.yandextranslator;

import android.app.Application;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;


public class App extends Application {

    private static App instance;

    // Класс для управления переходами между экранами
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
}
