package ru.saptan.yandextranslator.screens.translate;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.saptan.yandextranslator.R;
import ru.saptan.yandextranslator.models.TranslateCardItem;

import static ru.saptan.yandextranslator.models.TranslateCardItem.Type.INPUT;
import static ru.saptan.yandextranslator.models.TranslateCardItem.Type.OUTPUT;


public class TranslateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // Список объектов
    private List<TranslateCardItem> items;

    TranslateAdapter() {
        items = new ArrayList<>();
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
            // Текст, который необходимо перевести
            ((InputVH) holder).editTextInput.setText(item.getText());
        }
        // Если элемент является карточкой для отображения перевода
        else if (holder instanceof OutputVH) {
            ((OutputVH) holder).tvTranslated.setText(item.getText());
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeCard();
    }

    public void insertItem(TranslateCardItem item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    class InputVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ic_listen_input_text)
        ImageView btnListenInputText;

        @BindView(R.id.ic_voice_input)
        ImageView btnVoiceInput;

        @BindView(R.id.ic_paste)
        ImageView btnPaste;

        @BindView(R.id.et_input)
        EditText editTextInput;

        public InputVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class OutputVH extends RecyclerView.ViewHolder {

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
