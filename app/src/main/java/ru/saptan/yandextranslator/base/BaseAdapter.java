package ru.saptan.yandextranslator.base;


import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseAdapter<View, Item> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG_CLASS = getClass().getSimpleName();
    protected final String TAG = "debug";

    // Объект для хранения всех подписок
    protected CompositeSubscription compositeSubscription;
    // Список объектов
    protected List<Item> items;
    // Интерфейс для взаимодействия с View
    protected WeakReference<View> view;

    public BaseAdapter() {
        items = new ArrayList<>();
        compositeSubscription = new CompositeSubscription();
    }

    public void bindView(View view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void insertItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void updateItem(Item item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void unsubscribe() {
        view.clear();
        compositeSubscription.clear();
    }

    protected View getView() {
        return view.get();
    }


}
