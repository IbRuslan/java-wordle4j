package exceptions;

public class WordNotFoundInDictionaryException extends GameException {
    public WordNotFoundInDictionaryException(String message) {
        super("Слова \"" + message + "\" нет в словаре");
    }
}
