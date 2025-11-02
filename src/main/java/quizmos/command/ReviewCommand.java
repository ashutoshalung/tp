package quizmos.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quizmos.common.ReviewMessages;
import quizmos.exception.QuizMosException;
import quizmos.exception.QuizMosInputException;
import quizmos.exception.QuizMosLogicException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.review.IReviewMode;
import quizmos.review.MultipleChoiceReview;
import quizmos.review.SimpleFlipReview;
import quizmos.review.TrueFalseReview;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

/**
 * Represents a command that initiates a flashcard review session.
 * In review mode, each flashcard is shown one at a time, allowing the user
 * to view the question, reveal the answer, and proceed to the next card.
 * The session can be exited early with the "exit" command.
 */
public class ReviewCommand extends Command {
    private IReviewMode reviewMode;

    private int count = 0;
    private int correct = 0;
    private final String mode;

    /**
     * @param command raw user's command
     * @throws QuizMosInputException if missing review mode
     */
    public ReviewCommand(String command) throws QuizMosInputException {
        String reviewRegex = "(review)\\sm/(.+)";

        Pattern pattern = Pattern.compile(reviewRegex);
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            this.mode =  matcher.group(2).trim().toLowerCase();
        } else {
            throw new QuizMosInputException(ReviewMessages.REVIEW_MISSING_MODE);
        }
    }

    /**
     * set review mode as flip/mcq/tf with flashcard list
     *
     * @param flashcards list of flashcard
     * @throws QuizMosException for input error
     */
    private void setReviewMode(FlashcardList flashcards) throws QuizMosException {
        switch (mode) {
        case "mcq":
            this.reviewMode = new MultipleChoiceReview(flashcards);
            break;
        case "flip":
            this.reviewMode = new SimpleFlipReview();
            break;
        case "tf":
            this.reviewMode = new TrueFalseReview(flashcards);
            break;
        default:
            throw new QuizMosInputException(ReviewMessages.REVIEW_INVALID_MODE);
        }
    }

    /**
     * quit review session
     * print goodbye message, total cards reviewed and result (if mcq or tf)
     */
    public void quitReview() {
        Ui.printSeparator();
        Ui.printMessage(ReviewMessages.endReview(count));
        if (!(reviewMode instanceof SimpleFlipReview)) {
            Ui.printMessage(ReviewMessages.reviewResult(count, correct));
        }
        Ui.printSeparator();
    }

    /**
     * doing 3 steps for each flashcard in the list
     * 1. display the question
     * 2. get user's input
     * 3. resolve the input (for t/f and mcq mode)
     *
     * @param flashcards list of flashcards
     */
    public void reviewLoop(FlashcardList flashcards) {
        int listSize = flashcards.getSize();
        while (count < listSize) {
            Flashcard currentFlashcard = flashcards.getFlashcard(count);
            // display question
            reviewMode.displayQuestion(currentFlashcard, count);
            // get user's input
            String input = reviewMode.getPrompt();
            if (input.equals("quit")) {
                break;
            }
            // resolve the input
            if (reviewMode.checkAnswer(input, currentFlashcard)) {
                correct++;
            }
            count++;
        }
    }

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        assert flashcards != null : "FlashcardList can't be null";

        if (flashcards.getSize() == 0) {
            throw new QuizMosLogicException(ReviewMessages.REVIEW_EMPTY_LIST);
        }

        // assign reviewMode
        setReviewMode(flashcards);

        // review loop
        reviewLoop(flashcards);

        // quit review
        quitReview();
    }

}
