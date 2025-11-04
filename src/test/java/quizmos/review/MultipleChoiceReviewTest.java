package quizmos.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quizmos.exception.QuizMosLogicException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;


class MultipleChoiceReviewTest {

    private FlashcardList flashcardList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        Ui.isTestMode = true;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Build 5 flashcards for safe testing (>=4)
        flashcardList = new FlashcardList();
        flashcardList.addFlashcard(new Flashcard("Q1", "A1"));
        flashcardList.addFlashcard(new Flashcard("Q2", "A2"));
        flashcardList.addFlashcard(new Flashcard("Q3", "A3"));
        flashcardList.addFlashcard(new Flashcard("Q4", "A4"));
        flashcardList.addFlashcard(new Flashcard("Q5", "A5"));
    }

    @Test
    void testConstructorThrowsWhenTooFewFlashcards() {
        FlashcardList smallList = new FlashcardList();
        smallList.addFlashcard(new Flashcard("Q1", "A1"));
        smallList.addFlashcard(new Flashcard("Q2", "A2"));
        smallList.addFlashcard(new Flashcard("Q3", "A3"));

        QuizMosLogicException e = assertThrows(
                QuizMosLogicException.class,
                () -> new MultipleChoiceReview(smallList)
        );
        assertTrue(e.getMessage().toLowerCase().contains("enough"),
                "Exception message should mention not enough flashcards");
    }

    @Test
    void testListOfChoicesReturnsFourUniqueIndicesIncludingTarget() throws Exception {
        Method method = MultipleChoiceReview.class.getDeclaredMethod("listOfChoices", int.class, int.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) method.invoke(null, 5, 2);

        assertEquals(4, result.size(), "Should return 4 indices");
        assertTrue(result.contains(2), "Result must contain the target index");
        assertEquals(result.stream().distinct().count(), 4, "All indices should be unique");
    }

    @Test
    void testDisplayQuestionPrintsCorrectly() throws Exception {
        MultipleChoiceReview review = new MultipleChoiceReview(flashcardList);
        Flashcard f = flashcardList.getFlashcard(1); // index 1, A2

        review.displayQuestion(f, 1);
        String output = outContent.toString();

        assertTrue(output.contains("Q2"), "Output should include the question");
        assertTrue(output.contains("A"), "Output should include some answer choices");
    }

    @Test
    void testCheckAnswerCorrectAndIncorrectBehavior() throws Exception {
        MultipleChoiceReview review = new MultipleChoiceReview(flashcardList);
        Flashcard f = flashcardList.getFlashcard(0); // Q1, A1

        // Simulate displayQuestion to populate currentAnswer
        review.displayQuestion(f, 0);

        // Access private field currentAnswer
        var currentAnswerField = MultipleChoiceReview.class.getDeclaredField("currentAnswer");
        currentAnswerField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> answers = (List<Integer>) currentAnswerField.get(review);

        // Simulate correct input (index + 1)
        int correctChoice = answers.get(0) + 1;
        boolean correct = review.checkAnswer(String.valueOf(correctChoice), f);
        assertTrue(correct, "Should return true for correct answer");

        String correctOutput = outContent.toString();
        assertFalse(correctOutput.contains("CORRECT") || correctOutput.contains("correct"),
                "Should print a correct answer message");

        // Now test incorrect input
        outContent.reset();
        String wrongInput = String.valueOf((correctChoice % 4) + 1);
        boolean incorrect = review.checkAnswer(wrongInput, f);
        assertFalse(incorrect, "Should return false for incorrect answer");

        String wrongOutput = outContent.toString();
        assertTrue(wrongOutput.toLowerCase().contains("incorrect"),
                "Should print an incorrect answer message");
    }

    @Test
    void testAssertionsInCheckAnswerRejectInvalidInput() throws Exception {
        MultipleChoiceReview review = new MultipleChoiceReview(flashcardList);
        Flashcard f = flashcardList.getFlashcard(0);
        review.displayQuestion(f, 0);

        // invalid input like "5" should trigger AssertionError
        assertThrows(AssertionError.class, () -> review.checkAnswer("5", f));
    }
}
