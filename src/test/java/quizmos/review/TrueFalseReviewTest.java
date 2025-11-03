package quizmos.review;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.ui.Ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrueFalseReviewTest {

    private FlashcardList flashcardList;
    private TrueFalseReview review;
    private Flashcard flashcard;

    @BeforeEach
    void setUp() {
        ArrayList<Flashcard> cards = new ArrayList<>();
        cards.add(new Flashcard("Q1", "A1"));
        cards.add(new Flashcard("Q2", "A2"));
        cards.add(new Flashcard("Q3", "A3"));
        flashcardList = new FlashcardList(cards);
        review = new TrueFalseReview(flashcardList);
        flashcard = flashcardList.getFlashcard(0);
    }

    @AfterEach
    void tearDown() throws Exception {
        Field supplierField = Ui.class.getDeclaredField("mockedCommandSupplier");
        supplierField.setAccessible(true);
        supplierField.set(null, null);
    }

    @Test
    void testRandomQuestionWithinRange() {
        for (int i = 0; i < 50; i++) {
            int result = review.randomQuestion(0, 2);
            assertTrue(result >= 0 && result <= 2, "Random index should be in range [0,2]");
        }
    }

    @Test
    void testDisplayQuestionDoesNotThrow() {
        assertDoesNotThrow(() -> review.displayQuestion(flashcard, 0));
    }

    @Test
    void testCheckAnswerTrueWhenCorrect() {
        // Force currentAnswer = true using reflection
        try {
            Field field = TrueFalseReview.class.getDeclaredField("currentAnswer");
            field.setAccessible(true);
            field.set(review, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean result = review.checkAnswer("t", flashcard);
        assertTrue(result, "Should be correct when currentAnswer = true and input = t");
    }

    @Test
    void testCheckAnswerFalseWhenIncorrect() {
        // Force currentAnswer = false using reflection
        try {
            Field field = TrueFalseReview.class.getDeclaredField("currentAnswer");
            field.setAccessible(true);
            field.set(review, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean result = review.checkAnswer("t", flashcard);
        assertFalse(result, "Should be incorrect when currentAnswer = false and input = t");
    }

    @Test
    void testCheckAnswerTrueWhenInputFAndNotCurrentAnswer() {
        try {
            Field field = TrueFalseReview.class.getDeclaredField("currentAnswer");
            field.setAccessible(true);
            field.set(review, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean result = review.checkAnswer("f", flashcard);
        assertTrue(result, "Input f should be correct when currentAnswer = false");
    }

    @Test
    void testCheckAnswerFalseWhenInputFAndCurrentAnswerTrue() {
        try {
            Field field = TrueFalseReview.class.getDeclaredField("currentAnswer");
            field.setAccessible(true);
            field.set(review, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean result = review.checkAnswer("f", flashcard);
        assertFalse(result, "Input f should be incorrect when currentAnswer = true");
    }

    @Test
    void testGetPromptReturnsValidInput() throws Exception {
        // Mock Ui.readCommand() to simulate user entering "t"
        Field supplierField = Ui.class.getDeclaredField("mockedCommandSupplier");
        supplierField.setAccessible(true);
        supplierField.set(null, (Supplier<String>) () -> "t");

        String result = review.getPrompt();
        assertEquals("t", result, "getPrompt should return valid input");
    }

    @Test
    void testGetPromptRetriesUntilValidInput() throws Exception {
        // Simulate invalid input first, then valid input
        Field supplierField = Ui.class.getDeclaredField("mockedCommandSupplier");
        supplierField.setAccessible(true);

        final String[] inputs = {"invalid", "f"};
        supplierField.set(null, new Supplier<String>() {
            private int index = 0;
            @Override
            public String get() {
                return inputs[Math.min(index++, inputs.length - 1)];
            }
        });

        String result = review.getPrompt();
        assertEquals("f", result, "Should retry until valid input is entered");
    }

    @Test
    void testConstructorInitializesFields() {
        assertNotNull(review, "Review object should be created");
    }
}
