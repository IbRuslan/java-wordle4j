package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    public static final int WORD_LENGTH = 5;

    private List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public String getRandomWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    public static String normalize(String word) {
        return word.trim().toLowerCase().replace("ё", "е");
    }

    public static boolean isValidGameWord(String word) {
        if (word.length() != WORD_LENGTH) return false;

        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String compare(String answer, String guess) {
        char[] result = new char[WORD_LENGTH];
        boolean[] used = new boolean[WORD_LENGTH];


        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }


        for (int i = 0; i < WORD_LENGTH; i++) {
            if (result[i] == '+') continue;

            char c = guess.charAt(i);
            boolean found = false;

            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!used[j] && answer.charAt(j) == c) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            result[i] = found ? '^' : '-';
        }

        return new String(result);
    }

    public static boolean matches(String candidate, String guess, String result) {

        for (int i = 0; i < WORD_LENGTH; i++) {
            char g = guess.charAt(i);
            char r = result.charAt(i);

            if (r == '+') {
                if (candidate.charAt(i) != g) {
                    return false;
                }
            }

            if (r == '^') {
                if (candidate.charAt(i) == g || candidate.indexOf(g) == -1) {
                    return false;
                }
            }

            if (r == '-') {
                if (candidate.indexOf(g) != -1) {
                    return false;
                }
            }
        }
        return true;
    }

}
