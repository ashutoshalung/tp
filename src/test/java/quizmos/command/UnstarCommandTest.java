package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.common.FlashcardMessages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UnstarCommandTest {

    private FlashcardList flashcards;
    private Storage storage;

    private static class TestStorage extends Storage {
        boolean writeCalled = false;

        public TestStorage() throws QuizMosFileException {
            super("test.txt");
        }

        @Override
        public void writeToFile(FlashcardList flashcards) {
            writeCalled = true; // mark that it was invoked
        }
    }

    @BeforeEach
    void setUp() throws QuizMosFileException {
        flashcards = new FlashcardList();
        storage = new TestStorage();
    }

    // ✅ VALID INPUT TEST
    @Test
    void execute_validStarredFlashcard_successfullyUnstars() throws Exception {
        // Arrange
        Flashcard flashcard = new Flashcard("Q1", "A1");
        flashcard.toggleStar(); // make it starred initially
        flashcards.addFlashcard(flashcard);
        flashcards.addStarredFlashcard(flashcard);

        UnstarCommand command = new UnstarCommand("1");

        // Capture console output
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outStream));

        try {
            // Act
            command.execute(flashcards, storage);
        } finally {
            System.setOut(originalOut);
        }

        // Assert
        String expectedMsg = FlashcardMessages.showUnstarredFlashcard(flashcard);
        String actualOut = outStream.toString().trim();
        assertTrue(actualOut.contains(expectedMsg), "Ui should display correct unstar message");
        assertFalse(flashcard.checkIsStarred(), "Flashcard should now be unstarred");
        assertEquals(0, flashcards.getStarredFlashcards().size(), "Starred list should be empty");
        assertTrue(((TestStorage) storage).writeCalled, "Storage.writeToFile() should be called");
    }

    // ❌ INVALID INPUT TEST #1: Null flashcard list
    @Test
    void execute_nullFlashcardList_throwsAssertionError() {
        UnstarCommand command = new UnstarCommand("1");
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(null, storage);
        });
        assertTrue(error.getMessage().contains("FlashcardList must not be null"));
    }

    // ❌ INVALID INPUT TEST #2: Null storage
    @Test
    void execute_nullStorage_throwsAssertionError() {
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));
        UnstarCommand command = new UnstarCommand("1");
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(flashcards, null);
        });
        assertTrue(error.getMessage().contains("Storage must not be null"));
    }

    // ❌ INVALID INPUT TEST #3: Negative index
    @Test
    void execute_negativeIndex_throwsAssertionError() {
        UnstarCommand command = new UnstarCommand("0"); // index = -1 internally
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(flashcards, storage);
        });
        assertTrue(error.getMessage().contains("Index must be non-negative"));
    }

    // ❌ INVALID INPUT TEST #4: Index out of range
    @Test
    void execute_indexOutOfRange_throwsQuizMosInputException() {
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));
        UnstarCommand command = new UnstarCommand("5"); // 1-based → index 4
        QuizMosInputException ex = assertThrows(QuizMosInputException.class, () -> {
            command.execute(flashcards, storage);
        });
        assertEquals("Index is out of range!", ex.getMessage());
    }

    // ❌ INVALID INPUT TEST #5: Already unstarred flashcard
    @Test
    void execute_alreadyUnstarredFlashcard_throwsQuizMosInputException() {
        Flashcard flashcard = new Flashcard("Q1", "A1"); // not starred
        flashcards.addFlashcard(flashcard);

        UnstarCommand command = new UnstarCommand("1");
        QuizMosInputException ex = assertThrows(QuizMosInputException.class, () -> {
            command.execute(flashcards, storage);
        });
        assertEquals("This flashcard is already unstarred!", ex.getMessage());
    }
}
