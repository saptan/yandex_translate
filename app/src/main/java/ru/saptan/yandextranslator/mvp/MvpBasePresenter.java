package ru.saptan.yandextranslator.mvp;


import android.util.Log;

import java.lang.ref.WeakReference;

import ru.saptan.yandextranslator.App;
import ru.saptan.yandextranslator.screens.translate.TranslateViewModel;
import ru.terrakok.cicerone.Router;
import rx.subscriptions.CompositeSubscription;

public abstract class MvpBasePresenter<View extends MvpView, ViewModel> implements MvpPresenter<View> {


    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    // Класс, который превращает высокоуровневые вызовы навигации презентера в набор Command
    protected Router router;
    // Название текущего экрана
    protected String currentScreen;

    //
    private boolean firstLaunch;
    // Ссылка на вьюшку
    private WeakReference<View> view;
    // Объект для хранения всех подписок
    protected CompositeSubscription compositeSubscription;
    // Модель для сохранения данных, необходимых для отображения View
    protected ViewModel viewModel;

    public MvpBasePresenter() {
        firstLaunch = true;
        compositeSubscription = new CompositeSubscription();
        router = App.getInstance().getRouter();
    }

    /**
     * Привязать к презентеру вьюшку
     *
     * @param view - вью, отвечающее за взаимодействие с пользователем
     */
    @Override
    public void attachView(View view) {

        this.view = new WeakReference<View>(view);
        // Если к Presenter-у первый раз привязывается View
        if (firstLaunch) {
            firstLaunch = false;
            onFirstViewAttach();
        } else {
            // Иначе View было пересоздано
            onRecreated();
        }
    }

    /**
     * Выполнить какие-либо действия после того как к Presenter-у первый раз будет привязана View
     * Например, можно получить данные из БД
     */
    protected void onFirstViewAttach() {
    }

    /**
     * Метод вызывается тогда, когда Presenter пережил уничтожение старого View и привязывется к новому
     */
    protected void onRecreated() {
    }

    /**
     * Открепить View от Presenter-а. Данный метод должен вызываться в onPause() у Activity (Fragment)
     */
    @Override
    public void detachView() {
        view = null;
    }

    /**
     * Сообщить презентеру о том, что view было уничтожено
     *
     * @param isChangingConfig - true, если view было уничтожено из-за смены конфигурации
     *                         false, если view было уничтожено из-за закрытия приложения
     */
    @Override
    public void destroy(boolean isChangingConfig) {

        // В случае, если View было уничтожено не из-за смены конфигурации, то
        if (!isChangingConfig) {
            // Отписаться сразу от всех подписок
            compositeSubscription.clear();
        }
    }

    protected View getView() {
        return view.get();
    }



}
