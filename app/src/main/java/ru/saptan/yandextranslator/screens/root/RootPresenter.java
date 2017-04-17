package ru.saptan.yandextranslator.screens.root;

import ru.saptan.yandextranslator.mvp.MvpBasePresenter;
import ru.saptan.yandextranslator.navigation.NameScreens;

public class RootPresenter extends MvpBasePresenter<RootView> implements RootContract.Presenter {


    public RootPresenter() {
        super();
    }

    /**
     * Привязать к презентеру вьюшку
     *
     * @param view - вью, отвечающее за взаимодействие с пользователем
     */
    @Override
    public void attachView(RootView view) {
        super.attachView(view);
    }

    /**
     * Выполнить какие-либо действия после того как к Presenter-у первый раз будет привязана View
     */
    @Override
    protected void onFirstViewAttach() {
        // При открытии приложения должен открывать экран "Перевод"
        currentScreen = NameScreens.TRANSLATE;
        router.newRootScreen(currentScreen);
    }


}
