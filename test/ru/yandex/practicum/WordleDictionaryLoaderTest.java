package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleDictionaryLoaderTest {
    @Test
    void loader_shouldLoadOnlyFiveLetterWords() throws Exception {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dict =
                loader.load(Path.of("test/resources/test_dict.txt"));

        for (String word : dict.getWords()) {
            assertEquals(5, word.length());
        }

    }
}
