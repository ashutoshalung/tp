package quizmos.command;

import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddFlashcardCommandTest {

    @Test
    void execute_validInput_addsFlashcard() throws Exception {
        FlashcardList flashcards = new FlashcardList();
        Storage storage = new Storage("data/QuizMos.txt") {
            @Override
            public void writeToFile(FlashcardList list) { /* skip actual writing */ }
        };

        AddFlashcardCommand command = new AddFlashcardCommand(new String[] {"add", "q/Q1 a/A1"});
        command.execute(flashcards, storage);

        assertEquals(1, flashcards.getSize());
        assertEquals("Q1", flashcards.getFlashcard(0).getQuestion());
        assertEquals("A1", flashcards.getFlashcard(0).getAnswer());
    }

    @Test
    void constructor_missingAnswer_doesNotAddFlashcard() throws Exception {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "q/Q1"}));
    }

    @Test
    void constructor_missingQuestion_throwQuizMosInputException() throws Exception {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "a/A1"}));
    }

    @Test
    void constructor_reversedOrder_doesNotAddFlashcard() {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "a/A1 q/Q1"}));
    }

    @Test
    void constructor_emptyQuestion_rejected() {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "q/ a/A1"}));
    }

    @Test
    void constructor_emptyAnswer_rejected() {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "q/Q1 a/"}));
    }

    @Test
    void constructor_whitespaceOnlyQuestion_rejected() {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "q/   a/A1"}));
    }

    @Test
    void constructor_whitespaceOnlyAnswer_rejected() {
        assertThrows(QuizMosInputException.class, () -> new AddFlashcardCommand(new String[] {"add", "q/Q1 a/    "}));
    }
}
