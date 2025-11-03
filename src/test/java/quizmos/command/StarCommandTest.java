package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;


import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit 5 tests for StarCommand (no Mockito, no Ui modifications)
 */
public class StarCommandTest {

    private FlashcardList flashcardList;
    private StorageStub storageStub;
    private Flashcard flashcard1;
    private Flashcard flashcard2;
    private ByteArrayOutputStream outputStream;

    // === Simple storage stub that tracks if writeToFile() is called ===
    static class StorageStub extends Storage {
        boolean written = false;

        public StorageStub() throws QuizMosFileException {
            super("testFile.txt");
        }

        @Override
        public void writeToFile(FlashcardList flashcards) {
            written = true;
        }
    }

    @BeforeEach
    void setUp() throws QuizMosFileException {
        // Capture System.out output (what Ui.respond prints)
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Sample flashcards
        flashcard1 = new Flashcard("Q1", "A1");
        flashcard2 = new Flashcard("Q2", "A2");
        flashcardList = new FlashcardList();
        flashcardList.addFlashcard(flashcard1);
        flashcardList.addFlashcard(flashcard2);

        storageStub = new StorageStub();
    }

    @AfterEach
    void tearDown() {
        // Reset System.out after test
        System.setOut(System.out);
    }

    @Test
    void execute_validIndex_shouldStarFlashcardAndPersist() throws Exception {
        StarCommand command = new StarCommand("1");
        command.execute(flashcardList, storageStub);

        assertTrue(flashcard1.checkIsStarred(), "Flashcard should be starred");
        assertTrue(flashcardList.getStarredFlashcards().contains(flashcard1),
                "Starred flashcard list should contain flashcard1");
        assertTrue(storageStub.written, "Storage should have been written to");

        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("Starred"), "Ui output should indicate the flashcard was starred");
        assertTrue(consoleOutput.contains("Q1"), "Ui output should show the flashcard content");
    }

    @Test
    void execute_indexOutOfRange_shouldThrowException() {
        StarCommand command = new StarCommand("5"); // invalid index
        QuizMosInputException exception = assertThrows(
                QuizMosInputException.class,
                () -> command.execute(flashcardList, storageStub)
        );
        assertEquals("Index is out of range!", exception.getMessage());
        assertFalse(storageStub.written, "Storage should not be written to");
    }

    @Test
    void execute_alreadyStarredFlashcard_shouldThrowException() throws Exception {
        // Star once manually
        flashcard1.toggleStar();
        flashcardList.addStarredFlashcard(flashcard1);

        StarCommand command = new StarCommand("1");

        QuizMosInputException exception = assertThrows(
                QuizMosInputException.class,
                () -> command.execute(flashcardList, storageStub)
        );

        assertEquals("This flashcard is already starred!", exception.getMessage());
        assertFalse(storageStub.written, "Storage should not be written to if already starred");
    }

    @Test
    void execute_nullFlashcardList_shouldFailAssertion() {
        StarCommand command = new StarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(null, storageStub));
    }

    @Test
    void execute_nullStorage_shouldFailAssertion() {
        StarCommand command = new StarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(flashcardList, null));
    }
}
