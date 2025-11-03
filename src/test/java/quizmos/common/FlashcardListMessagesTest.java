package quizmos.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlashcardListMessagesTest {

    @Test
    void testEmptyListMessageConstant() {
        assertEquals("Your flashcard list is empty!", FlashcardListMessages.EMPTY_LIST_MESSAGE);
    }

    @Test
    void testEmptyStarredListConstant() {
        assertEquals("You have no starred flashcards.", FlashcardListMessages.EMPTY_STARRED_LIST);
    }

    @Test
    void testStarredListMessageConstant() {
        assertEquals("These are your starred flashcards: \n", FlashcardListMessages.STARRED_LIST_MESSAGE);
    }

    @Test
    void testShowStarredFlashcardsList() {
        String input = "1. Flashcard A\n2. Flashcard B";
        String expected = FlashcardListMessages.STARRED_LIST_MESSAGE + input;

        String actual = FlashcardListMessages.showStarredFlashcardsList(input);

        assertEquals(expected, actual);
    }

    @Test
    void testShowStarredFlashcardsListWithEmptyString() {
        String input = "";
        String expected = FlashcardListMessages.STARRED_LIST_MESSAGE;

        String actual = FlashcardListMessages.showStarredFlashcardsList(input);

        assertEquals(expected, actual);
    }
}
