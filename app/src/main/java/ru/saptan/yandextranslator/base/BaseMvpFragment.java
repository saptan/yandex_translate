package ru.saptan.yandextranslator.base;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.Unbinder;
import ru.saptan.yandextranslator.data.StateManager;
import ru.saptan.yandextranslator.mvp.MvpAdapter;
import ru.saptan.yandextranslator.mvp.MvpPresenter;
import ru.saptan.yandextranslator.mvp.MvpView;
import ru.saptan.yandextranslator.screens.translate.TranslatePresenter;

public abstract class BaseMvpFragment<Presenter extends MvpPresenter, Adapter extends MvpAdapter>
        extends Fragment implements MvpView {

    protected final String TAG = "debug";
    protected final String TAG_CLASS = getClass().getSimpleName();

    // Объект для сохранения состояния.
    protected StateManager stateManager;
    //
    protected Unbinder unbinder;
    // Ссылка на презентер
    protected Presenter presenter;
    // Адаптер
    protected Adapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        // Настроить Presenter для взаимодействия со View
        setupPresenter();
        // Привязать к презентеру вьюшку
        presenter.attachView(this);
    }

    /**
     * Настроить Presenter для взаимодействия со View
     */
    @Override
    public void setupPresenter() {
        if (stateManager == null)
            stateManager = new StateManager(getActivity().getSupportFragmentManager(), TAG_CLASS + "_retainer");

        // Если фрагмент создается впервые, то...
        if (stateManager.firstTimeIn()) {

            // Создать новый презентер
            Presenter _presenter = createPresenter();
            // Сохранить его в менеджере состояний
            stateManager.put(_presenter);
            presenter = _presenter;
        } else {
            onRecreatedPresenter();
            // Иначе если фрагмент был восстановлен, то получить уже ранее созданный презентер
            presenter = stateManager.get(getPresenterClass().getName());
        }
    }

    /**
     * Создать Presenter для взаимодействия с View
     */
    protected abstract Presenter createPresenter();

    /**
     * Получить класс Presenter
     * @return - class presenter
     */
    protected abstract Class<Presenter> getPresenterClass();

    /**
     * Событие происходит когда Presenter был восстановлен
     */
    protected void onRecreatedPresenter(){
    }

    @Override
    public void onPause() {
        super.onPause();
        // Отвязать View от Presenter
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        adapter.destroy();

        // Если фрагмент уничтожен из-за смены конфигурации (например, поворот экрана), то
        // Presenter продолжает жить, иначе
        presenter.destroy(getActivity().isChangingConfigurations());

        if (getActivity().isChangingConfigurations()) {
            presenter = null;
            stateManager = null;
        }
    }

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
