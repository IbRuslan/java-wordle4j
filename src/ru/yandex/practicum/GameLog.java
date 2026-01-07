package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class GameLog implements AutoCloseable {

    private final PrintWriter writer;

    public GameLog(String filename) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filename, true));
        log("=== Лог запущен ===");
    }

    public GameLog(PrintWriter writer) {
        this.writer = writer;
    }

    public void log(String message) {
        writer.println(LocalDateTime.now() + " | " + message);
        writer.flush();
    }

    public void logError(String message, Exception e) {
        log(message);
        log("ERROR: " + e.getClass().getSimpleName()
                + " - " + e.getMessage());
    }

    @Override
    public void close() {
        log("=== Лог закрыт ===");
        writer.close();
    }
}
