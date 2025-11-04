package quizmos.common;

import org.junit.jupiter.api.Test;
import quizmos.flashcard.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewMessagesTest {
    Flashcard card1 = new Flashcard("1", "1");
    Flashcard card2 = new Flashcard("2", "2");

    @Test
    void endReview_zeroQuestion_printOutZero() {
        assertEquals("Review session ended!\nTotal reviewed: " + 0, ReviewMessages.endReview(0));
    }

    @Test
    void showStatementTF_normalString_normalOutput() {
        assertEquals("Statement: normal", ReviewMessages.showStatementTF("normal"));
    }

    @Test
    void showStatementTF_space_normalOutput() {
        assertEquals("Statement:  ", ReviewMessages.showStatementTF(" "));
    }

    @Test
    void endReview_nQuestion_printOutN() {
        int n = 200;
        assertEquals("Review session ended!\nTotal reviewed: " + n, ReviewMessages.endReview(n));
    }

    @Test
    void reviewResult_zeroQuestion_printOutZero() {
        assertEquals("Correct questions: 0 (0.0%)", ReviewMessages.reviewResult(0, 0));
    }

    @Test
    void reviewResult_haveQuestion_printOutPercent() {
        assertEquals("Correct questions: 50 (50.0%)", ReviewMessages.reviewResult(100, 50));
        assertEquals("Correct questions: 69 (69.0%)", ReviewMessages.reviewResult(100, 69));
    }

    @Test
    void showCorrectAnswerTF_normalFlashcard_showItsAnswer() {
        assertEquals("True answer: 1",  ReviewMessages.showCorrectAnswerTF(card1));
        assertEquals("True answer: 2",  ReviewMessages.showCorrectAnswerTF(card2));
    }

    @Test
    void showCorrectAnswerMCQ_normalIndexAndFlashCard_showItsAnswer() {
        assertEquals("Correct answer: 2. 1", ReviewMessages.showCorrectAnswerMCQ(1, card1));
        assertEquals("Correct answer: 3. 2", ReviewMessages.showCorrectAnswerMCQ(2, card2));
    }

    @Test
    void showAnswer_normalFlashcard_showItsAnswer() {
        assertEquals("Answer: 1",  ReviewMessages.showAnswer(card1));
        assertEquals("Answer: 2",  ReviewMessages.showAnswer(card2));
    }

    @Test
    void flashcardQuestionString_normalFlashcardAndIndex_showItsIndexAndQuestion() {
        assertEquals("Flashcard no.2\nQuestion: 1", ReviewMessages.flashcardQuestionString(card1, 1));
        assertEquals("Flashcard no.3\nQuestion: 2", ReviewMessages.flashcardQuestionString(card2, 2));
    }

    @Test
    void mcqChoice_normalFlashcardAndChoiceIndex_showChoiceIndexThenAnswer() {
        assertEquals("2. 1",  ReviewMessages.mcqChoice(card1, 1));
        assertEquals("3. 2",  ReviewMessages.mcqChoice(card2, 2));
    }
}
