package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.common.FlashcardListMessages;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetStarCommandTest {

    private FlashcardList list;
    private StorageStub storage;
    private GetStarCommand command;

    // Minimal Storage stub to avoid log/file writes
    static class StorageStub extends Storage {
        StorageStub() throws QuizMosFileException {
            super("dummy.txt");
        }

        @Override
        public void writeToFile(FlashcardList l) {
            // no-op
        }
    }

    @BeforeEach
    void setUp() throws QuizMosFileException {
        command = new GetStarCommand();

        list = new FlashcardList() {
            @Override
            public String getStarredFlashcardsString() {
                return "1. Question: Q1 | Answer: A1 (Starred)";
            }
        };

        storage = new StorageStub();
    }

    @Test
    void execute_validInput_shouldDisplayStarredFlashcards() throws QuizMosInputException {
        command.execute(list, storage);
        String output = FlashcardListMessages.showStarredFlashcardsList(list.getStarredFlashcardsString());
        assertTrue(output.contains("Q1"), "Output should include the starred flashcard");
    }

    @Test
    void execute_nullFlashcardList_shouldFailAssertion() {
        AssertionError e = assertThrows(AssertionError.class, () -> command.execute(null, storage));
        assertTrue(e.getMessage().contains("FlashcardList must not be null"));
    }

    @Test
    void execute_nullStorage_shouldFailAssertion() {
        AssertionError e = assertThrows(AssertionError.class, () -> command.execute(list, null));
        assertTrue(e.getMessage().contains("Storage must not be null"));
    }

    @Test
    void execute_getStarredFlashcardsStringReturnsNull_shouldFailAssertion() {
        list = new FlashcardList() {
            @Override
            public String getStarredFlashcardsString() {
                return null;
            }
        };
        AssertionError e = assertThrows(AssertionError.class, () -> command.execute(list, storage));
        assertTrue(e.getMessage().contains("getStarredFlashcardsString should not return null"));
    }

}
