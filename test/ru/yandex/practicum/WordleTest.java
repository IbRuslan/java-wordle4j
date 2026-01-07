package ru.yandex.practicum;

import exceptions.GameException;
import exceptions.InvalidWordFormatException;
import exceptions.WordNotFoundInDictionaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private WordleGame game;

    @BeforeEach
    void setUp() {
        List<String> words = List.of("гонец", "конец", "венец");
        WordleDictionary dictionary = new WordleDictionary(words);

        GameLog gameLogger = new GameLog(new PrintWriter(System.out, true));
        game = new WordleGame(dictionary, gameLogger);

        game.setAnswerForTest("гонец");
    }
    @Test
    void makeGuess_validWord() throws GameException {
        String result = game.makeGuess("конец");
        assertEquals("-++++", result);
    }

    @Test
    void win_whenGuessEqualsAnswer() throws GameException {
        String result = game.makeGuess("гонец");
        assertTrue(game.isWin("гонец"));
        assertEquals("+++++", result);
    }

    @Test
    void invalidFormat_shouldThrowException() {
        assertThrows(
                InvalidWordFormatException.class,
                () -> game.makeGuess("abc")
        );
    }

    @Test
    void wordNotInDictionary_shouldThrowException() {
        assertThrows(
                WordNotFoundInDictionaryException.class,
                () -> game.makeGuess("домик")
        );
    }

    @Test
    void hint_shouldMatchPreviousGuesses() throws GameException {
        game.makeGuess("конец");

        String hint = game.getHint();

        assertNotNull(hint);
        assertEquals(5, hint.length());
        assertTrue(
                WordleDictionary.matches(
                        hint, "конец", "-++++"
                )
        );
    }
}
