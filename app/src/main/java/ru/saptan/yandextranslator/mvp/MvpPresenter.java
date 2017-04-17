package ru.saptan.yandextranslator.mvp;


public interface MvpPresenter<View extends MvpView>  {


    /**
     * Привязать к презентеру вьюшку
     * @param view - вью, отвечающее за взаимодействие с пользователем
     */
    void attachView(View view);

    void detachView();

    /**
     * Сообщить презентеру о том, что view было уничтожено
     * @param isChangingConfig - true, если view было уничтожено из-за смены конфигурации
     *                         false, если view было уничтожено из-за закрытия приложения
     */
    void destroy(boolean isChangingConfig);
}
