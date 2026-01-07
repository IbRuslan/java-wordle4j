package ru.yandex.practicum;

import exceptions.GameException;
import exceptions.GameStateException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {

        try (GameLog logger = new GameLog("wordle.log")) {
            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader();
                logger.log("Начинаем загрузку словаря");

                WordleDictionary dictionary = loader.load(Path.of("words_ru.txt"));

                logger.log("Словарь загружен. Количество слов: "
                        + dictionary.getWords().size());

                WordleGame game = new WordleGame(dictionary, logger);
                Scanner scanner = new Scanner(System.in);

                logger.log("Игра началась");

                System.out.println("Правила игры:");
                System.out.println("— Загадано слово из " + WordleDictionary.WORD_LENGTH + " букв");
                System.out.println("— У вас " + WordleGame.MAX_STEPS + " попыток");
                System.out.println("— После ввода слова вы увидите подсказку:");
                System.out.println("  + — буква на своём месте");
                System.out.println("  ^ — буква есть в слове, но в другом месте");
                System.out.println("  - — буквы нет в слове");
                System.out.println("— Нажмите Enter без ввода слова, чтобы получить подсказку");
                System.out.println();

                while (!game.isGameOver()) {

                    System.out.println("Осталось попыток: " + game.getSteps() + ". " + "Введите слово: ");
                    String input = scanner.nextLine();

                    try {
                        if (input.isEmpty()) {
                            if (game.getSteps() > 1) {
                                String hint = game.getHint();
                                System.out.println("Подсказка: " + hint);
                                logger.log("Запрошена подсказка: " + hint);
                            } else {
                                System.out.println("Подсказки закончились, осталась последняя попытка!");
                            }
                            continue;
                        }

                        if (input.length() != WordleDictionary.WORD_LENGTH) {
                            System.out.println("Введите слово из 5 букв");
                            logger.log("Неверный ввод: " + input);
                            continue;
                        }

                        String guess = WordleDictionary.normalize(input);
                        String resultHint = game.makeGuess(input);

                        System.out.println(resultHint);

                        if (game.isWin(guess)) {
                            System.out.println("Угадали!");
                            logger.log("Игрок победил");
                            return;
                        }
                    } catch (GameException e) {
                        System.out.println(e.getMessage());
                        logger.log("Игровая ошибка: " + e.getMessage());
                    }
                }

                System.out.println("Вы проиграли. Загаданное слово: " + game.getAnswer());
                logger.log("Игрок проиграл. Ответ: " + game.getAnswer());

            } catch (IOException e) {
                System.out.println("Не удалось загрузить словарь.");
                logger.log("Ошибка загрузки словаря: " + e.getMessage());
            } catch (GameStateException e) {
                System.out.println(e.getMessage());
                logger.log("Ошибка состояния игры: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
