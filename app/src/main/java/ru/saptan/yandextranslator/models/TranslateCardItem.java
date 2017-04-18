package ru.saptan.yandextranslator.models;


public class TranslateCardItem {
    // Текст, отображаемый на карточке
    private String text;
    // Тип карточки (для ввода текста, или для отображения перевода)
    private int typeCard;


    public int getTypeCard() {
        return typeCard;
    }

    public TranslateCardItem setTypeCard(int typeCard) {
        this.typeCard = typeCard;
        return this;
    }

    public String getText() {
        return text;
    }

    public TranslateCardItem setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Тип карточек
     */
    public static class Type {
        // Карточка, на которой располагается текстовое поле ввода
        public static final int INPUT = 0;
        // Карточка, на которой отображается перевод
        public static final int OUTPUT = 1;
    }

}
