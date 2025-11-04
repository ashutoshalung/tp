package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.common.FlashcardListMessages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetStarCommandTest {

    private GetStarCommand command;

    private static class TestFlashcardList extends FlashcardList {
        private final String starredList;
        TestFlashcardList(String starredList) {
            this.starredList = starredList;
        }
        @Override
        public String getStarredFlashcardsString() {
            return starredList;
        }
    }

    private static class TestStorage extends Storage {
        public TestStorage() throws QuizMosFileException {
            super("test.txt");
        }
    }

    @BeforeEach
    void setUp() {
        command = new GetStarCommand();
    }

    // --- VALID INPUT TEST ---
    @Test
    void execute_validInput_displaysStarredFlashcards() throws QuizMosInputException, QuizMosFileException {
        // Arrange
        String starredList = "1. Question: Q1 | Answer: A1 (Starred)";
        FlashcardList flashcardList = new TestFlashcardList(starredList);
        Storage storage = new TestStorage();

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Act
            command.execute(flashcardList, storage);
        } finally {
            System.setOut(originalOut);
        }

        // Assert
        String expectedMessage = FlashcardListMessages.showStarredFlashcardsList(starredList);
        String actualOutput = outContent.toString().trim();

        assertTrue(actualOutput.contains(expectedMessage),
                "Expected Ui to print the starred flashcards list");
    }

    // --- INVALID INPUT TEST #1 ---
    @Test
    void execute_nullFlashcardList_throwsAssertionError() throws QuizMosFileException {
        Storage storage = new TestStorage();
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(null, storage);
        });
        assertTrue(error.getMessage().contains("FlashcardList must not be null"));
    }

    // --- INVALID INPUT TEST #2 ---
    @Test
    void execute_nullStorage_throwsAssertionError() {
        FlashcardList flashcardList = new TestFlashcardList("Starred flashcards");
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(flashcardList, null);
        });
        assertTrue(error.getMessage().contains("Storage must not be null"));
    }

    // --- INVALID INPUT TEST #3 ---
    @Test
    void execute_getStarredFlashcardsStringReturnsNull_throwsAssertionError() throws QuizMosFileException {
        FlashcardList flashcardList = new TestFlashcardList(null);
        Storage storage = new TestStorage();
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.execute(flashcardList, storage);
        });
        assertTrue(error.getMessage().contains("getStarredFlashcardsString should not return null"));
    }
}
