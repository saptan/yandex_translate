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

    /**
     * Добавить элемент в список
     * @param item - объект
     */
    public void insertItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Обновить элемент списка
     * @param position - позиция элемента в списке
     */
    public void updateItem(int position) {
        notifyItemChanged(position);
    }

    /**
     * Получить элемент списка
     * @param position - позиция элемента в списке
     * @return - объект
     */
    public Item getItem(int position) {
        return items.get(position);
    }

    /**
     * Вызывается конда уничтожается View
     */
    public void destroy() {
        // Очистить ссылку на View
        view.clear();
        // Удалить все подписки
        compositeSubscription.clear();
    }

    protected View getView() {
        return view.get();
    }


}
