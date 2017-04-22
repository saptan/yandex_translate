package ru.saptan.yandextranslator.base;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaseInteractor {


    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    @SuppressWarnings("unchecked")
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
