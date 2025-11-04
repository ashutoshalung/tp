package quizmos.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosFileException;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UnstarCommandTest {

    private FlashcardList list;
    private Storage storage;

    @BeforeEach
    void setUp() throws QuizMosFileException {
        list = new FlashcardList();
        storage = new Storage("dummy.txt") {
            boolean written = false;
            @Override
            public void writeToFile(FlashcardList l) { written = true; }
        };
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
    }

    @Test
    void execute_indexOutOfRange_shouldThrowException() {
        list.addFlashcard(new Flashcard("Q1", "A1"));
        UnstarCommand command = new UnstarCommand("5");
        assertThrows(QuizMosInputException.class, () -> command.execute(list, storage));
    }

    @Test
    void execute_alreadyUnstarredFlashcard_shouldThrowException() {
        Flashcard f = new Flashcard("Q1", "A1"); // not starred
        list.addFlashcard(f);

        UnstarCommand command = new UnstarCommand("1");
        assertThrows(QuizMosInputException.class, () -> command.execute(list, storage));
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
