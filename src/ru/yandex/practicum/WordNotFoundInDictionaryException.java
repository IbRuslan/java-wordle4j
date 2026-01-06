package ru.yandex.practicum;

public class WordNotFoundInDictionaryException extends GameException {
    public WordNotFoundInDictionaryException(String message) {
        super("Слова \"" + message + "\" нет в словаре");
    }
}
