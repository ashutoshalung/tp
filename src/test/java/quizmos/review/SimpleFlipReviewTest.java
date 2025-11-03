package quizmos.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.flashcard.Flashcard;
import quizmos.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleFlipReviewTest {

    private SimpleFlipReview review;
    private Flashcard flashcard;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        Ui.isTestMode = true;
        review = new SimpleFlipReview();
        flashcard = new Flashcard("What is Java?", "A programming language");

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testDisplayQuestionPrintsCorrectQuestion() {
        review.displayQuestion(flashcard, 2);
        String output = outContent.toString();

        assertTrue(output.contains("What is Java?"), "Output should include the flashcard question");
        assertFalse(output.contains("2"), "Output should include the index number");
    }

    @Test
    void testCheckAnswerWithYesPrintsAnswer() {
        boolean result = review.checkAnswer("y", flashcard);

        assertTrue(result, "checkAnswer should always return true");
        String output = outContent.toString();
        assertTrue(output.contains("A programming language"), "Should print the answer when input is 'y'");
    }

    @Test
    void testCheckAnswerWithNoDoesNotPrintAnswer() {
        boolean result = review.checkAnswer("n", flashcard);

        assertTrue(result, "checkAnswer should always return true");
        String output = outContent.toString();
        assertFalse(output.contains("A programming language"), "Should not print the answer when input is 'n'");
    }

    @Test
    void testCheckAnswerRejectsInvalidInput() {
        assertThrows(AssertionError.class, () -> review.checkAnswer("x", flashcard),
                "Invalid input should trigger assertion");
    }

    @Test
    void testGetPromptReturnsValidInput() throws Exception {
        // Mock Ui.readCommand() to simulate user input sequence: invalid -> valid
        AtomicInteger callCount = new AtomicInteger(0);
        Ui.mockedCommandSupplier = () -> {
            if (callCount.getAndIncrement() == 0) {
                return "wrong";
            }
            return "y";
        };

        // Run getPrompt()
        String result = review.getPrompt();

        assertEquals("y", result, "Should return 'y' after receiving invalid then valid input");
        String output = outContent.toString();
        assertTrue(output.toLowerCase().contains("invalid") || output.contains("Invalid"),
                "Should print an invalid input message for the first input");
    }
}
