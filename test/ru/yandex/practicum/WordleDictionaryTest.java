package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleDictionaryTest {
    @Test
    void normalize_shouldLowercaseAndReplaceYo() {
        String result = WordleDictionary.normalize("ЁЛКА ");
        assertEquals("елка", result);
    }

    @Test
    void compare_exactMatch() {
        String result = WordleDictionary.compare("гонец", "гонец");
        assertEquals("+++++", result);
    }

    @Test
    void compare_partialMatch() {
        String result = WordleDictionary.compare("гонец", "конец");
        assertEquals("-++++", result);
    }
}
