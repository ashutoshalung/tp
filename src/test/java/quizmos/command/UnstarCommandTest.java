package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;




class UnstarCommandTest {

    private FlashcardList list;
    private StorageStub storage;

    // Minimal Storage stub
    static class StorageStub extends Storage {
        boolean Iswritten = false;

        StorageStub() throws QuizMosFileException {
            super("test.txt");
        }

        @Override
        public void writeToFile(FlashcardList l) {
            Iswritten = true;
        }
    }

    @BeforeEach
    void setUp() throws QuizMosFileException {
        list = new FlashcardList();
        storage = new StorageStub();
    }

    @Test
    void execute_validStarredFlashcard_shouldUnstar() throws Exception {
        Flashcard f = new Flashcard("Q1", "A1");
        f.toggleStar(); // initially starred
        list.addFlashcard(f);
        list.addStarredFlashcard(f);

        new UnstarCommand("1").execute(list, storage);

        assertFalse(f.checkIsStarred());
        assertEquals(0, list.getStarredFlashcards().size());
        assertTrue(storage.Iswritten, "Storage should be written");
    }

    @Test
    void execute_indexOutOfRange_shouldThrowException() {
        list.addFlashcard(new Flashcard("Q1", "A1"));
        UnstarCommand command = new UnstarCommand("5");
        assertThrows(QuizMosInputException.class, () -> command.execute(list, storage));
        assertFalse(storage.Iswritten, "Storage should not be written on invalid index");
    }

    @Test
    void execute_alreadyUnstarredFlashcard_shouldThrowException() {
        Flashcard f = new Flashcard("Q1", "A1"); // not starred
        list.addFlashcard(f);

        UnstarCommand command = new UnstarCommand("1");
        assertThrows(QuizMosInputException.class, () -> command.execute(list, storage));
        assertFalse(storage.Iswritten, "Storage should not be written if already unstarred");
    }

    @Test
    void execute_nullFlashcardList_shouldFailAssertion() {
        UnstarCommand command = new UnstarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(null, storage));
    }

    @Test
    void execute_nullStorage_shouldFailAssertion() {
        list.addFlashcard(new Flashcard("Q1", "A1"));
        UnstarCommand command = new UnstarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(list, null));
    }

}
