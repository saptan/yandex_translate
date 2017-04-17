package ru.saptan.yandextranslator.screens.translate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ru.saptan.yandextranslator.R;

public class TranslateView extends Fragment {

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
        ButterKnife.bind(this, view);

        return view;
    }

}
