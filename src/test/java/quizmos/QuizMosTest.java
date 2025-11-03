package quizmos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * Tests for the QuizMos main application class.
 * Focuses on initialization, logging setup, and field integrity.
 * Does NOT test the interactive run() loop to avoid blocking input.
 */
class QuizMosTest {

    @BeforeEach
    void setUp() {
        // Enable test mode to prevent ANSI color output
        Ui.isTestMode = true;

        // Ensure data directory exists for Storage initialization
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            assertTrue(created || dataDir.exists(), "Failed to create data directory");
        }
    }

    @Test
    void testQuizMosConstructorInitializesFields() {
        QuizMos quizMos = new QuizMos();
        assertNotNull(quizMos, "QuizMos instance should not be null");

        try {
            // Access private flashcards field
            Field flashcardsField = QuizMos.class.getDeclaredField("flashcards");
            flashcardsField.setAccessible(true);
            Object flashcards = flashcardsField.get(quizMos);
            assertNotNull(flashcards, "Flashcards list should not be null");
            assertTrue(flashcards instanceof FlashcardList, "Flashcards should be instance of FlashcardList");

            // Access private storage field
            Field storageField = QuizMos.class.getDeclaredField("storage");
            storageField.setAccessible(true);
            Object storage = storageField.get(quizMos);
            assertNotNull(storage, "Storage should not be null");
            assertTrue(storage instanceof Storage, "Storage should be instance of Storage");

        } catch (Exception e) {
            fail("Reflection access failed: " + e.getMessage());
        }
    }

    @Test
    void testSetupLoggingCreatesLogFileAndDoesNotThrow() {
        QuizMos quizMos = new QuizMos();
        try {
            Method setupLoggingMethod = QuizMos.class.getDeclaredMethod("setupLogging");
            setupLoggingMethod.setAccessible(true);
            assertDoesNotThrow(() -> setupLoggingMethod.invoke(quizMos), "setupLogging should not throw an exception");

            // Verify the log file exists or can be created
            File logFile = new File("quizmos.log");
            assertTrue(logFile.exists() || logFile.canWrite(), "Log file should exist or be writable");
        } catch (Exception e) {
            fail("setupLogging invocation failed: " + e.getMessage());
        }
    }

    @Test
    void testStorageFileCreatedOnInitialization() {
        QuizMos quizMos = new QuizMos();
        File dataFile = new File("data/QuizMos.txt");
        assertTrue(dataFile.exists(), "Storage data file should be created during initialization");
        assertTrue(dataFile.canWrite(), "Storage data file should be writable");
    }
}
