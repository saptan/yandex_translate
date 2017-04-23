package ru.saptan.yandextranslator.screens.language_choice;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.saptan.yandextranslator.App;
import ru.saptan.yandextranslator.R;
import ru.saptan.yandextranslator.base.BaseMvpFragment;
import ru.saptan.yandextranslator.data.repository.DirectionRepository;
import ru.saptan.yandextranslator.interactors.LanguageInteractor;

public class LanguageChoiceView extends BaseMvpFragment<LanguageChoicePresenter, LanguageChoiceAdapter>
        implements LanguageChoiceContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    public LanguageChoiceView() {
        // Required empty public constructor
    }

    public static LanguageChoiceView newInstance(boolean choiceInputLanguage) {
        Bundle args = new Bundle();
        args.putBoolean("choiceInputLanguage", choiceInputLanguage);

        LanguageChoiceView f = new LanguageChoiceView();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language_choice, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupToolbar();

        initializeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    /**
     * Создать Presenter для взаимодействия с View
     */
    @Override
    protected LanguageChoicePresenter createPresenter() {
        DirectionRepository directionRepository = new DirectionRepository();
        // Создать интерактор, который будет взаимодействовать с RetrofitApi для получения информации
        // о поддерживаемых языках
        LanguageInteractor translateInteractor = new LanguageInteractor(directionRepository);
        // Создать новый презентер
        return new LanguageChoicePresenter(translateInteractor);
    }

    /**
     * Получить класс Presenter
     *
     * @return - class presenter
     */
    @Override
    protected Class<LanguageChoicePresenter> getPresenterClass() {
        return LanguageChoicePresenter.class;
    }

    /**
     * Настроить Toolbar
     */
    private void setupToolbar() {
        // Заголовок Toolbar
        String title;
        // Если экран открыт для выбора языка текста
        if (getArguments().getBoolean("choiceInputLanguage"))
            title = getString(R.string.toolbar_title_screen_choiceLanguage_input);
        else
            // Иначе он открыт для выбора языка перевода
            title = getString(R.string.toolbar_title_screen_choiceLanguage_output);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        // Установить заголовок
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        // Установить иконку
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setHomeAsUpIndicator(ContextCompat.getDrawable(App.getInstance(),
                        R.drawable.ic_action_arrow_back));
    }

    /**
     * Инициализировать адаптер
     */
    private void initializeAdapter() {
        adapter = new LanguageChoiceAdapter();
        adapter.bindView(this);

    }

}
