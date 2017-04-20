package ru.saptan.yandextranslator.screens.translate;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.saptan.yandextranslator.R;
import ru.saptan.yandextranslator.models.TranslateCardItem;
import ru.saptan.yandextranslator.base.BaseAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static ru.saptan.yandextranslator.models.TranslateCardItem.Type.INPUT;
import static ru.saptan.yandextranslator.models.TranslateCardItem.Type.OUTPUT;


public class TranslateAdapter extends BaseAdapter<TranslateContract.View, TranslateCardItem> {

    // Состояние карточки:
    // true - Пусто (текстовое поле незаполнено, отображаются кнопки для вставки текста и голосового ввода)
    // false - Не Пусто (текстовое поле заполнено, отображается кнопка для удаления текста)
    private boolean emptyState;

    TranslateAdapter() {
        super();
        emptyState = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case INPUT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_translate_input, parent, false);
                return new InputVH(v);
            case OUTPUT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_translate_output, parent, false);
                return new OutputVH(v);
            default:
                throw new RuntimeException("Некорректное значение viewType: " + viewType + ". Проверь тип карточки");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TranslateCardItem item = items.get(position);

        // Если элемент является карточкой для ввода текста
        if (holder instanceof InputVH) {

            InputVH inputVH = (InputVH) holder;

            if (inputVH.textChangedSubscription != null) {
                compositeSubscription.remove(inputVH.textChangedSubscription);
            }

            if (item.getText().length() > 0) {
                // Текст, который необходимо перевести
                inputVH.editTextInput.setText(item.getText());
            }

            // Заменить кнопку Enter на Complete (готово), чтобы исключить перенос текста, а при нажатии
            // на нее скрывать клавиатуру
            inputVH.editTextInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
            inputVH.editTextInput.setRawInputType(InputType.TYPE_CLASS_TEXT);

            // Подписать на изменения текста
            inputVH.textChangedSubscription = RxTextView
                    .textChanges(inputVH.editTextInput)
                    // Удалить пробелы в начале и конце строки
                    .map(text -> text.toString().trim())
                    // Установить задержку в 900 мс, чтобы исключить частые запросы
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, TAG_CLASS + ": onError -> " + e);
                        }

                        @Override
                        public void onNext(String s) {
                            // Настроить отображение кнопок для первой карточки
                            hideInputButtons(inputVH, s);
                            if (getView() != null) {
                                // Сообщить View о том, что содержимое editTextInput было изменено
                                getView().textChanged(s);
                            }
                        }
                    });


            compositeSubscription.add((inputVH).textChangedSubscription);
        }
        // Если элемент является карточкой для отображения перевода
        else if (holder instanceof OutputVH) {
            OutputVH outputVH = (OutputVH) holder;

            // Если текстовое поле не пустое, то
            if (item.getText().length() > 0) {
                // Отобразить карточку
                outputVH.cardOutput.setVisibility(View.VISIBLE);
                // Отобразить переведенный текст
                outputVH.tvTranslated.setText(item.getText());
            } else
                // иначе скрыть карточку
                outputVH.cardOutput.setVisibility(View.GONE);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeCard();
    }

    /**
     * Настроить отображение кнопок для первой карточки
     * @param inputVH - ViewHolder карточки для ввода текста
     * @param text - текст, который ввел пользователь
     */
    private void hideInputButtons(InputVH inputVH, String text) {
        // Если длина текста больше 0 и карточка находится в "пустом состоянии" (т.е. текстовое поле
        // было пустое), то изменить ее состояние
        if (text.length() > 0 && emptyState) {
            // Скрыть кнопку "Вставить текст из буфера"
            inputVH.btnPaste.setVisibility(View.GONE);
            // Скрыть кнопку "Голосовой ввод"
            inputVH.btnVoiceInput.setVisibility(View.GONE);
            // Отобразить кнопку "Очистить текстовое поле"
            inputVH.btnClearInputArea.setVisibility(View.VISIBLE);
            // Переключить состояние карточки в "Не пусто"
            emptyState = false;
        }
        // Иначе если длина текста равна 0 и карточка находится в состоянии "Не пусто"
        else if (text.length() == 0 && !emptyState)  {
            // Отобразить кнопку "Вставить текст из буфера"
            inputVH.btnPaste.setVisibility(View.VISIBLE);
            // Отобразить кнопку "Голосовой ввод"
            inputVH.btnVoiceInput.setVisibility(View.VISIBLE);
            // Скрыть кнопку "Очистить текстовое поле"
            inputVH.btnClearInputArea.setVisibility(View.GONE);
            // Переключить состояние карточки в "Пусто"
            emptyState = true;
        }
    }

    static class InputVH extends RecyclerView.ViewHolder {

        // Подписка на изменения текста
        Subscription textChangedSubscription;

        @BindView(R.id.ic_listen_input_text)
        ImageView btnListenInputText;

        @BindView(R.id.ic_voice_input)
        ImageView btnVoiceInput;

        @BindView(R.id.ic_paste)
        ImageView btnPaste;

        @BindView(R.id.ic_clear)
        ImageView btnClearInputArea;

        @BindView(R.id.et_input)
        EditText editTextInput;

        public InputVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class OutputVH extends RecyclerView.ViewHolder {

        @BindView(R.id.card_output)
        CardView cardOutput;

        @BindView(R.id.ic_listen_output_text)
        ImageView btnListenOutputText;

        @BindView(R.id.ic_fullscreen)
        ImageView btnFullscreen;

        @BindView(R.id.ic_favorite)
        ImageView btnFavorite;

        @BindView(R.id.ic_copy)
        ImageView btnCopy;

        @BindView(R.id.ic_share)
        ImageView btnShare;

        @BindView(R.id.tv_translated)
        TextView tvTranslated;

        public OutputVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
