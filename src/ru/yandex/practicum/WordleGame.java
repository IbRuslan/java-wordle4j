package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    private final WordleDictionary dictionary;
    private final GameLog logger;

    private final List<String> guesses = new ArrayList<>();
    private final List<String> results = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, GameLog logger) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.logger = logger;
        this.steps = 6;

        logger.log("Загадано слово (скрыто)");
    }

    public String makeGuess(String guess) throws GameException {
        if (steps <= 0) {
            throw new GameAlreadyFinishedException();
        }

        validateWord(guess);

        String result = WordleDictionary.compare(answer, guess);

        guesses.add(guess);
        results.add(result);
        steps--;

        logger.log("Проверка слова: " + guess + " -> " + result);

        if (steps < 0) {
            throw new GameStateException("stepsLeft < 0");
        }

        return result;
    }

    private void validateWord(String word)
            throws GameException {

        if (word.length() != 5) {
            throw new InvalidWordFormatException();
        }

        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                throw new InvalidWordFormatException();
            }
        }

        if (!dictionary.getWords().contains(word)) {
            throw new WordNotFoundInDictionaryException(word);
        }
    }

    public String getHint() throws GameException {
        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.getWords()) {
            boolean found = true;

            for (int i = 0; i < guesses.size(); i++) {
                if (!WordleDictionary.matches(
                        word,
                        guesses.get(i),
                        results.get(i))) {
                    found = false;
                    break;
                }
            }

            if (found) {
                candidates.add(word);
            }
        }

        if (candidates.isEmpty()) {
            return "Подходящих слов нет";
        }

        Random random = new Random();
        return candidates.get(random.nextInt(candidates.size()));
    }

    public boolean isWin(String guess) {
        return answer.equals(guess);
    }

    public boolean isGameOver() {
        return steps <= 0;
    }

    public String getAnswer() {
        return answer;
    }

    void setAnswerForTest(String answer) {
        this.answer = answer;
    }

}
