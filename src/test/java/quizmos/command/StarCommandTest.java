package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;




class StarCommandTest {

    private FlashcardList list;
    private StorageStub storage;

    // By using a stub, logging calls inside Storage do nothing, so no new unnecessary log files are created.
    static class StorageStub extends Storage {
        boolean Iswritten = false;

        StorageStub() throws QuizMosFileException {
            super("dummy.txt");
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
    void execute_validIndex_shouldStarFlashcard() throws Exception {
        Flashcard f = new Flashcard("Q", "A");
        list.addFlashcard(f);

        new StarCommand("1").execute(list, storage);

        assertTrue(f.checkIsStarred());
        assertTrue(list.getStarredFlashcards().contains(f));
        assertTrue(storage.Iswritten, "Storage should be marked as written");
    }

    @Test
    void execute_indexOutOfRange_shouldThrowException() {
        list.addFlashcard(new Flashcard("Q", "A"));

        StarCommand command = new StarCommand("5"); // invalid index
        QuizMosInputException e = assertThrows(QuizMosInputException.class,
                () -> command.execute(list, storage));
        assertEquals("Index is out of range!", e.getMessage());
        assertFalse(storage.Iswritten, "Storage should not be written for invalid index");
    }

    @Test
    void execute_alreadyStarredFlashcard_shouldThrowException() throws Exception {
        Flashcard f = new Flashcard("Q", "A");
        f.toggleStar();
        list.addFlashcard(f);
        list.addStarredFlashcard(f);

        StarCommand command = new StarCommand("1");
        QuizMosInputException e = assertThrows(QuizMosInputException.class,
                () -> command.execute(list, storage));
        assertEquals("This flashcard is already starred!", e.getMessage());
        assertFalse(storage.Iswritten, "Storage should not be written if already starred");
    }

    @Test
    void execute_nullFlashcardList_shouldFailAssertion() {
        StarCommand command = new StarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(null, storage));
    }

    @Test
    void execute_nullStorage_shouldFailAssertion() {
        list.addFlashcard(new Flashcard("Q", "A"));
        StarCommand command = new StarCommand("1");
        assertThrows(AssertionError.class, () -> command.execute(list, null));
    }

}
