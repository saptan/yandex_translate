package ru.saptan.yandextranslator.screens.translate;


public class TranslateViewModel implements TranslateContract.ViewModel{

    // Введенный пользователем текст
    private String inputtedText = "";
    // Переведенный текст
    private String translatedText = "";

    /**
     * Получить текст, который ввел пользователь
     *
     * @return - введеный текст
     */
    @Override
    public String getInputtedText() {
        return inputtedText;
    }

    /**
     * Сохранить текст, который ввел пользователь
     *
     * @param inputtedText - введеный текст
     */
    @Override
    public void setInputtedText(String inputtedText) {
        this.inputtedText = inputtedText;
    }

    /**
     * Получить текст, который был переведен
     *
     * @return - переведенный текст
     */
    @Override
    public String getTranslatedText() {
        return translatedText;
    }

    /**
     * Сохранить текст, который был переведен
     *
     * @param translatedText - переведенный текст
     */
    @Override
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
