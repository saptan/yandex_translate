package ru.saptan.yandextranslator.data;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class StateManager {

    private final String mStateMaintenerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;
    private boolean mIsRecreating;

    public StateManager(FragmentManager fragmentManager, String stateMaintainerTAG) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintenerTag = stateMaintainerTAG;
    }


    /**
     * Создать фрагмент, который будет ответственный за сохранение объектов
     * @return true - если создан впервые
     */
    public boolean firstTimeIn() {
        try {
            // Восстановление ссылки
            mStateMaintainerFrag = (StateMngFragment)
                    mFragmentManager.get().findFragmentByTag(mStateMaintenerTag);

            // Создать новый RetainedFragment
            if (mStateMaintainerFrag == null) {
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction().add(mStateMaintainerFrag, mStateMaintenerTag).commit();
                mIsRecreating = false;
                return true;
            } else {
                mIsRecreating = true;
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Метод для определения, была ли Activity пересоздана, или это ее первый запуск.
     * @return  true: если Activity создавалась хотя бы раз
     */
    public boolean wasRecreated() { return mIsRecreating; }


    /**
     * Вставить объект, чтобы его потом можно было сохранить
     * @param key   TAG
     * @param obj   объект, который нужно сохранить
     */
    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }


    /**
     * Вставить объект, чтобы его потом можно было сохранить
     * @param obj   объект, который нужно сохранить
     */
    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }


    /**
     * Восстановить сохраненный объект
     * @param key    TAG
     * @param <T>   тип объекта (класс)
     * @return      сохраненный объект
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    /**
     * Проверить, существует ли объект среди сохраненных
     * @param key   TAG
     * @return      true: если объект существует
     */
    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }


    /**
     * Объект, отвественный за сохранение
     * Инициализируется только один раз. Импользует hashmap для сохранения объектов
     */
    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        /**
         * Вставмть данные в hashmap
         * @param key   ключ ссылка (TAG - имя класса)
         * @param obj   объект, который будет сохранен
         */
        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        /**
         * Вставмть данные в hashmap
         * @param object    объект, который будет сохранен
         */
        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        /**
         * Восстановить сохраненные объекты
         * @param key   ключ ссылка (TAG - имя класса)
         * @param <T>   тип объекта (класс)
         * @return      сохраненный объект
         */
        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}