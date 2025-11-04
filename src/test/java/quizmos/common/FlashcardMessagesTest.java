package quizmos.common;

import org.junit.jupiter.api.Test;
import quizmos.flashcard.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlashcardMessagesTest {

    @Test
    void testInvalidAddCommandConstant() {
        assertEquals("Invalid command! Try add q/<QUESTION> a/<ANSWER>", FlashcardMessages.invalidAddCommand);
    }

    @Test
    void testAddedFlashcardMessageConstant() {
        assertEquals("Added this flashcard\n", FlashcardMessages.addedFlashcardMessage);
    }

    @Test
    void testNoMatchesMessageConstant() {
        assertEquals("No matches found", FlashcardMessages.noMatchesMessage);
    }

    @Test
    void testInvalidIndexMessageConstant() {
        assertEquals(
                "Invalid index! Use a correct integer flashcard index!\nTry `list` to see a list of valid flashcards",
                FlashcardMessages.invalidIndexMessage
        );
    }

    @Test
    void testRemovedFlashcardMessageConstant() {
        assertEquals("Removed this flashcard\n", FlashcardMessages.removedFlashcardMessage);
    }

    @Test
    void testStarredFlashcardMessageConstant() {
        assertEquals("Starred this flashcard:\n", FlashcardMessages.starredFlashcardMessage);
    }

    @Test
    void testUnstarredFlashcardMessageConstant() {
        assertEquals("Unstarred this flashcard:\n", FlashcardMessages.unstarredFlashcardMessage);
    }

    @Test
    void testShowFlashcardAdded() {
        Flashcard flashcard = new Flashcard("Capital of France?", "Paris");
        String expected = FlashcardMessages.addedFlashcardMessage + flashcard;
        assertEquals(expected, FlashcardMessages.showFlashcardAdded(flashcard));
    }

    @Test
    void testShowStarredFlashcard() {
        Flashcard flashcard = new Flashcard("Largest planet?", "Jupiter");
        flashcard.toggleStar(); // now starred
        String expected = FlashcardMessages.starredFlashcardMessage + flashcard;
        assertEquals(expected, FlashcardMessages.showStarredFlashcard(flashcard));
    }

    @Test
    void testShowFlashcardRemoved() {
        Flashcard flashcard = new Flashcard("2 + 2", "4");
        String expected = FlashcardMessages.removedFlashcardMessage + flashcard;
        assertEquals(expected, FlashcardMessages.showFlashcardRemoved(flashcard));
    }

    @Test
    void testShowUnstarredFlashcard() {
        Flashcard flashcard = new Flashcard("Water formula", "H2O");
        flashcard.toggleStar(); // make it starred
        flashcard.toggleStar(); // unstar again
        String expected = FlashcardMessages.unstarredFlashcardMessage + flashcard;
        assertEquals(expected, FlashcardMessages.showUnstarredFlashcard(flashcard));
    }
}
