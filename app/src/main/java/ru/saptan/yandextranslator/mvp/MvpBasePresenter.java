package ru.saptan.yandextranslator.mvp;


import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

public abstract class MvpBasePresenter<View extends MvpView>  implements MvpPresenter<View> {


    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    //
    private boolean firstLaunch ;
    // Ссылка на вьюшку
    private WeakReference<View> view;
    // Объект для хранения всех подписок
    protected CompositeSubscription compositeSubscription;

    public MvpBasePresenter() {
        firstLaunch = true;
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * Привязать к презентеру вьюшку
     * @param view - вью, отвечающее за взаимодействие с пользователем
     */
    @Override
    public void attachView(View view) {

        this.view = new WeakReference<View>(view);

        if (firstLaunch) {
            firstLaunch = false;
            onFirstViewAttach();
        }
    }

    /**
     * Выполнить какие-либо действия после того как к Presenter-у первый раз будет привязана View
     * Например, можно получить данные из БД
     */
    protected void onFirstViewAttach() {
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
     * @param isChangingConfig - true, если view было уничтожено из-за смены конфигурации
     *                         false, если view было уничтожено из-за закрытия приложения
     */
    @Override
    public void destroy(boolean isChangingConfig) {

        // В случае, если View было уничтожено не из-за смены конфигурации, то
        if (!isChangingConfig) {
            // Отписаться сразу от всех подписок
            compositeSubscription.unsubscribe();
        }
    }



}
