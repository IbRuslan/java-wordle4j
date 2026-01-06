package ru.yandex.practicum;

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

    public static void main(String[] args) throws IOException {

        try(GameLog logger = new GameLog("wordle.log") ) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            logger.log("Начинаем загрузку словаря");

            WordleDictionary dictionary = loader.load(Path.of("words_ru.txt"));
            if (dictionary.getWords().isEmpty()) {
                throw new GameStateException("Словарь пуст");
            }

            logger.log("Словарь загружен. Количество слов: "
                    + dictionary.getWords().size());

            WordleGame game = new WordleGame(dictionary, logger);
            Scanner scanner = new Scanner(System.in);

            logger.log("Игра началась");

            while (!game.isGameOver()) {
                System.out.println("Введите слово: ");
                String input = scanner.nextLine();

                try {
                    if (input.isEmpty()) {
                        String hint = game.getHint();
                        System.out.println("Подсказка: " + hint);
                        logger.log("Запрошена подсказка: " + hint);
                        continue;
                    }

                    if (input.length() != 5) {
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

    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
