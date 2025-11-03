package quizmos.flashcardlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.common.FlashcardListMessages;
import quizmos.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class FlashcardListTest {

    private FlashcardList list;

    @BeforeEach
    void setUp() {
        list = new FlashcardList();
    }

    @Test
    void testConstructorInitializesEmptyList() {
        assertNotNull(list.getFlashcards());
        assertEquals(0, list.getSize());
        assertEquals(0, list.getStarredFlashcards().size());
    }

    @Test
    void testConstructorWithExistingFlashcards() {
        ArrayList<Flashcard> arr = new ArrayList<>();
        arr.add(new Flashcard("Q1", "A1"));
        arr.add(new Flashcard("Q2", "A2"));
        FlashcardList customList = new FlashcardList(arr);
        assertEquals(2, customList.getSize());
        assertEquals(0, customList.getStarredFlashcards().size());
    }

    @Test
    void testAddAndRemoveFlashcard() {
        Flashcard f = new Flashcard("Question?", "Answer");
        list.addFlashcard(f);
        assertEquals(1, list.getSize());
        assertEquals(f, list.getFlashcard(0));

        list.removeFlashcard(0);
        assertEquals(0, list.getSize());
    }

    @Test
    void testGetFlashcard() {
        Flashcard f = new Flashcard("Q", "A");
        list.addFlashcard(f);
        assertEquals(f, list.getFlashcard(0));
    }

    @Test
    void testInitialiseStarredFlashcards() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        Flashcard f2 = new Flashcard("Q2", "A2");
        f2.toggleStar(); // starred
        ArrayList<Flashcard> arr = new ArrayList<>();
        arr.add(f1);
        arr.add(f2);

        FlashcardList list2 = new FlashcardList(arr);

        assertEquals(1, list2.getStarredFlashcards().size());
        assertTrue(list2.getStarredFlashcards().contains(f2));
    }


    @Test
    void testGetStarredFlashcardsStringWhenEmpty() {
        assertEquals(FlashcardListMessages.EMPTY_STARRED_LIST, list.getStarredFlashcardsString());
    }

    @Test
    void testGetStarredFlashcardsStringWhenNotEmpty() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        Flashcard f2 = new Flashcard("Q2", "A2");
        f2.toggleStar();
        list.addFlashcard(f1);
        list.addFlashcard(f2);
        list.initialiseStarredFlashcards();

        String result = list.getStarredFlashcardsString();
        assertTrue(result.contains("Q2"));
        assertFalse(result.contains("Q1"));
    }

    @Test
    void testToStringWhenEmpty() {
        assertEquals(FlashcardListMessages.EMPTY_LIST_MESSAGE, list.toString());
    }

    @Test
    void testToStringWhenNotEmpty() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        Flashcard f2 = new Flashcard("Q2", "A2");
        list.addFlashcard(f1);
        list.addFlashcard(f2);
        String output = list.toString();

        assertTrue(output.contains("Q1"));
        assertTrue(output.contains("Q2"));
        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
    }

    @Test
    void testIterator() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        Flashcard f2 = new Flashcard("Q2", "A2");
        list.addFlashcard(f1);
        list.addFlashcard(f2);

        Iterator<Flashcard> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(f1, it.next());
    }

    @Test
    void testAddAndRemoveStarredFlashcard() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        f1.toggleStar();
        list.addStarredFlashcard(f1);
        assertEquals(1, list.getStarredFlashcards().size());

        // Should remove since it's still starred
        list.removeStarredFlashcard(f1);
        assertEquals(0, list.getStarredFlashcards().size());
    }

    @Test
    void testRemoveStarredFlashcardWhenUnstarredDoesNothing() {
        Flashcard f1 = new Flashcard("Q1", "A1");
        list.addStarredFlashcard(f1);
        assertEquals(1, list.getStarredFlashcards().size());

        list.removeStarredFlashcard(f1); // unstarred, should not remove
        assertEquals(1, list.getStarredFlashcards().size());
    }
}
