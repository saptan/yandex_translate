package ru.saptan.yandextranslator.screens.root;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.saptan.yandextranslator.App;
import ru.saptan.yandextranslator.R;
import ru.saptan.yandextranslator.mvp.StateManager;
import ru.saptan.yandextranslator.navigation.FragmentNavigator;
import ru.terrakok.cicerone.Navigator;

public class RootView extends AppCompatActivity implements RootContract.View {

    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    @BindView(R.id.container)
    FrameLayout container;

    // Презентер
    private RootPresenter rootPresenter;

    // Объект для сохранения состояния.
    public final StateManager stateManager = new StateManager(getSupportFragmentManager(), TAG_CLASS);

    // Непосредственная реализация «переключения экранов» внутри контейнера
    private Navigator navigator = new FragmentNavigator(getSupportFragmentManager(), R.id.container) {
        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(RootView.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
    }

    /**
     * Настроить Presenter для взаимодействия со View
     */
    @Override
    public void setupPresenter() {
        // Если Activity создается впервые, то...
        if (stateManager.firstTimeIn()) {

            // Создать новый презентер
            RootPresenter _presenter = new RootPresenter();
            // Сохранить его в менеджере состояний
            stateManager.put(_presenter);
            rootPresenter = _presenter;
        } else {
            // Иначе если Activity была восстановлена, то получить уже ранее созданный презентер
            rootPresenter = stateManager.get(RootPresenter.class.getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupPresenter();
        rootPresenter.attachView(this);
        App.getInstance().getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    public void onPause() {
        super.onPause();
        rootPresenter.detachView();
        App.getInstance().getNavigatorHolder().removeNavigator();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rootPresenter.destroy(isChangingConfigurations());
    }


}
