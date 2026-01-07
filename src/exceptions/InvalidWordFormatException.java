package exceptions;

public class InvalidWordFormatException extends GameException {

    public InvalidWordFormatException() {
        super("Введите слово из 5 русских букв");
    }
}
