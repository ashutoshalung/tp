package quizmos.review;

import quizmos.common.ReviewMessages;
import quizmos.exception.QuizMosLogicException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.ui.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultipleChoiceReview implements IReviewMode {
    private static final Logger logger = Logger.getLogger(MultipleChoiceReview.class.getName());
    private final FlashcardList flashcardList;
    private final List<String> choices = List.of("1", "2", "3", "4", "quit");
    private List<Integer> currentAnswer = new ArrayList<>();

    public MultipleChoiceReview(FlashcardList flashcardList) throws QuizMosLogicException {
        super();
        if (flashcardList.getSize() < 4) {
            throw new QuizMosLogicException(ReviewMessages.MCQ_NOT_ENOUGH_FLASHCARDS);
        }
        this.flashcardList = flashcardList;
    }

    /**
     * @param size size of flashcard list
     * @param index correct index that need to be included
     * @return list of 4 integers ~ 4 indices for 4 answer (including index)
     */
    private static List<Integer> listOfChoices(int size, int index) {
        List<Integer> indices = new ArrayList<>();
        indices.add(index);
        for (int i = 0; i < 3; i++) {
            int random = (int) (Math.random() * size);
            if (!indices.contains(random)) {
                indices.add(random);
            } else {
                int up = random;
                int down = random;
                while (true) {
                    if (indices.contains(up)) {
                        if (up < size - 1) {
                            up++;
                        }
                    } else {
                        indices.add(up);
                        break;
                    }
                    if (indices.contains(down)) {
                        if (down > 0) {
                            down--;
                        }
                    } else {
                        indices.add(down);
                        break;
                    }
                }
            }
        }
        Collections.shuffle(indices, new Random());
        return indices;
    }

    /**
     * Print flashcard's index and question
     * Then print list of choices
     *
     * @param flashcard reviewing
     * @param index of that flashcard
     */
    public void displayQuestion(Flashcard flashcard, int index) {
        assert flashcardList != null: "FlashcardList can not be null";

        Ui.printSeparator();
        Ui.printMessage(ReviewMessages.flashcardQuestionString(flashcard, index));
        Ui.printMessage(ReviewMessages.ANSWER);

        currentAnswer.clear();
        String correctAnswer = flashcard.getAnswer();

        List<Integer> answers = listOfChoices(flashcardList.getSize(), index);
        for (int i = 0; i < 4; i++) {
            String randomAnswer = flashcardList.getFlashcard(answers.get(i)).getAnswer();
            int randomIndex = answers.get(i);
            if (randomIndex == index || correctAnswer.equals(randomAnswer)) {
                currentAnswer.add(i);
                logger.log(Level.WARNING, ReviewMessages.mcqChoice(flashcardList.getFlashcard(randomIndex), i));
            }

            Ui.printMessage(ReviewMessages.mcqChoice(flashcardList.getFlashcard(randomIndex), i));
        }
    }

    /**
     * Check user's answer and return true/false
     * Show correct answer if they're wrong
     *
     * @param input user's input
     * @param flashcard flashcard reviewing
     * @return true if answer is correct, false if not
     */
    public boolean checkAnswer(String input, Flashcard flashcard) {
        assert input.equals("1") || input.equals("2") ||
                input.equals("3") || input.equals("4"): "Input can only be (1/2/3/4)";

        logger.log(Level.WARNING, input);
        int answer = Integer.parseInt(input) - 1;
        if (currentAnswer.contains(answer)) {
            Ui.printMessage(ReviewMessages.CORRECT_ANSWER);
            Ui.printSeparator();
            return true;
        } else {
            Ui.printMessage(ReviewMessages.INCORRECT_ANSWER);
            Ui.printMessage(ReviewMessages.showCorrectAnswerMCQ(currentAnswer.get(0), flashcard));
            Ui.printSeparator();
            return false;
        }
    }

    /**
     * Get user's input (1/2/3/4/quit)
     * @return user's input
     */
    public String getPrompt() {
        while (true) {
            Ui.printPrompt(ReviewMessages.PROMPT_MCQ);
            String input = Ui.readCommand().trim().toLowerCase();

            if (choices.contains(input)) {
                return input;
            }
            Ui.printError(ReviewMessages.INVALID_INPUT_MCQ);
        }
    }

}
