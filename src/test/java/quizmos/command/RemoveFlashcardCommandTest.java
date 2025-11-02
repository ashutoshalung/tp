package quizmos.command;

import org.junit.jupiter.api.Test;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveFlashcardCommandTest {

    @Test
    void execute_validIndex_removesFlashcard() throws Exception {
        FlashcardList flashcards = new FlashcardList();
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));

        Storage storage = new Storage("data/QuizMos.txt") {
            @Override
            public void writeToFile(FlashcardList list) { /* no-op */ }
        };

        RemoveFlashcardCommand command = new RemoveFlashcardCommand("1");
        command.execute(flashcards, storage);

        assertEquals(0, flashcards.getSize());
    }

    @Test
    void constructor_nonIntegerIndex_doesNotRemoveFlashcard() throws Exception {
        FlashcardList flashcards = new FlashcardList();
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));

        Storage storage = new Storage("data/QuizMos.txt") {
            @Override
            public void writeToFile(FlashcardList list) { }
        };

        RemoveFlashcardCommand command = new RemoveFlashcardCommand("abc");
        command.execute(flashcards, storage);

        assertEquals(1, flashcards.getSize());
    }

    @Test
    void execute_negativeIndex_doesNotRemoveFlashcard() throws Exception {
        FlashcardList flashcards = new FlashcardList();
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));

        Storage storage = new Storage("data/QuizMos.txt") {
            @Override
            public void writeToFile(FlashcardList list) { }
        };

        RemoveFlashcardCommand command = new RemoveFlashcardCommand("-1");
        command.execute(flashcards, storage);

        assertEquals(1, flashcards.getSize());
    }

    @Test
    void execute_indexOutOfRange_doesNotRemoveFlashcard() throws Exception {
        FlashcardList flashcards = new FlashcardList();
        flashcards.addFlashcard(new Flashcard("Q1", "A1"));

        Storage storage = new Storage("data/QuizMos.txt") {
            @Override
            public void writeToFile(FlashcardList list) { }
        };

        RemoveFlashcardCommand command = new RemoveFlashcardCommand("5");
        command.execute(flashcards, storage);

        assertEquals(1, flashcards.getSize());
    }
}
