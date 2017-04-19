package ru.saptan.yandextranslator.screens.translate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.saptan.yandextranslator.R;
import ru.saptan.yandextranslator.data.StateManager;
import ru.saptan.yandextranslator.interactors.TranslateInteractor;
import ru.saptan.yandextranslator.models.TranslateCardItem;

public class TranslateView extends Fragment implements TranslateContract.View {

    protected final String TAG = "debug";
    protected final String TAG_CLASS = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    // Объект для сохранения состояния.
    private StateManager stateManager;
    //
    private Unbinder unbinder;

    // Ссылка на презентер
    private TranslatePresenter presenter;
    //
    private TranslateAdapter adapter;

    public TranslateView() {
        // Required empty public constructor
    }

    public static TranslateView newInstance() {
        TranslateView f = new TranslateView();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        unbinder = ButterKnife.bind(this, view);

        initializeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    /**
     * Настроить Presenter для взаимодействия со View
     */
    @Override
    public void setupPresenter() {
        if (stateManager == null)
            stateManager = new StateManager(getActivity().getSupportFragmentManager(), TAG + "_retainer");

        // Если фрагмент создается впервые, то...
        if (stateManager.firstTimeIn()) {
            // Создать интерактор, который будет взаимодействовать с RetrofitApi для получения перевода
            TranslateInteractor translateInteractor = new TranslateInteractor();
            // Создать новый презентер
            TranslatePresenter _presenter = new TranslatePresenter(translateInteractor);
            // Сохранить его в менеджере состояний
            stateManager.put(_presenter);
            presenter = _presenter;
        } else {
            // Иначе если фрагмент был восстановлен, то получить уже ранее созданный презентер
            presenter = stateManager.get(TranslatePresenter.class.getName());
        }
    }

    /**
     * Инициализировать адаптер
     */
    private void initializeAdapter() {
        adapter = new TranslateAdapter();
        adapter.bindView(this);
        // Карточка для ввода текста
        TranslateCardItem inputCard = new TranslateCardItem()
                .setTypeCard(TranslateCardItem.Type.INPUT);

        // Карточка для отображения переведенного текста
        TranslateCardItem outputCard = new TranslateCardItem()
                .setTypeCard(TranslateCardItem.Type.OUTPUT);

        adapter.insertItem(inputCard);
        adapter.insertItem(outputCard);
    }

    /**
     * Сообщить View о том, что содержимое EditText было изменено
     *
     * @param text - текст, который ввел пользователь, чтобы получить его перевод
     */
    @Override
    public void textChanged(String text) {
        presenter.translateText(text);
    }

    /**
     * Отобразить перевод
     *
     * @param text - переведенный текст
     */
    @Override
    public void showTranslatedText(String text) {
        TranslateCardItem outputCard = adapter.getItem(TranslateCardItem.Type.OUTPUT);
        outputCard.setText(text);
        adapter.updateItem(outputCard, TranslateCardItem.Type.OUTPUT);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Настроить Presenter для взаимодействия со View
        setupPresenter();
        // Привязать к презентеру вьюшку
        presenter.attachView(this);
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
        adapter.unsubscribe();

        // Если фрагмент уничтожен из-за смены конфигурации (например, поворот экрана), то
        // Presenter продолжает жить, иначе
        presenter.destroy(getActivity().isChangingConfigurations());

        if (getActivity().isChangingConfigurations()) {
            presenter = null;
            stateManager = null;
        }
    }


}
