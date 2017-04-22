package ru.saptan.yandextranslator.navigation;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import ru.saptan.yandextranslator.screens.language_choice.LanguageChoiceView;
import ru.saptan.yandextranslator.screens.translate.TranslateView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

public abstract class FragmentNavigator implements Navigator {

    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    private FragmentManager fragmentManager;
    private int containerId;

    /**
     * Создать SupportFragmentNavigator.
     *
     * @param fragmentManager support fragment менеджер
     * @param containerId     id container layout для фрагментов
     */
    public FragmentNavigator(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void applyCommand(Command command) {

        // Переход на новый экран
        if (command instanceof Forward) {
            Forward forward = (Forward) command;
            fragmentManager
                    .beginTransaction()
                    .add(containerId, createFragment(forward.getScreenKey(), forward.getTransitionData()))
                    .addToBackStack(forward.getScreenKey())
                    .commit();
        }
        // Возврат на предыдущий экран
        else if (command instanceof Back) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            } else { // если BackStack пуст, то значит выйти из приложения
                exit();
            }
        }
        // Замена текущего экрана на новый
        else if (command instanceof Replace) {
            Replace replace = (Replace) command;
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(replace.getScreenKey(), replace.getTransitionData()))
                        .addToBackStack(replace.getScreenKey())
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(replace.getScreenKey(), replace.getTransitionData()))
                        .commit();
            }
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    /**
     * Создать фрагмент в соответствии с параметром screenKey (название экрана).
     *
     * @param screenKey screen key
     * @param data      данные, которые необходимо передать для инициализации фрагмента
     * @return фрагмент
     */
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case NameScreens.TRANSLATE:
                return TranslateView.newInstance();
            case NameScreens.CHOICE_LANGUAGE:
                return LanguageChoiceView.newInstance((Boolean) data);
            default:
                throw new RuntimeException("Невозможно перейти на экран: " + screenKey);

        }
    }

    /**
     * Вызывается когда выполняется выход с root экрана
     */
    protected abstract void exit();

    /**
     * Отобразить сообщение
     * @param message - текст сообщения
     */
    protected abstract void showSystemMessage(String message);

}
