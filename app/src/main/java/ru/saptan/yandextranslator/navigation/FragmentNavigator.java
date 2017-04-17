package ru.saptan.yandextranslator.navigation;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.saptan.yandextranslator.screens.translate.TranslateView;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public abstract class FragmentNavigator extends SupportFragmentNavigator {

    /**
     * Создать SupportFragmentNavigator.
     *
     * @param fragmentManager support fragment менеджер
     * @param containerId     id container layout для фрагментов
     */
    public FragmentNavigator(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
    }

    /**
     * Создать фрагмент в соответствии с параметром screenKey (название экрана).
     *
     * @param screenKey screen key
     * @param data      данные, которые необходимо передать для инициализации фрагмента
     * @return фрагмент
     */
    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case NameScreens.TRANSLATE:
                return TranslateView.newInstance();
            default:
                throw new RuntimeException("Невозможно перейти на экран: " + screenKey);

        }
    }
}
