package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.exception.QuizMosInputException;
import quizmos.storage.Storage;
import quizmos.ui.Ui;
import quizmos.flashcardlist.FlashcardList;
import quizmos.flashcard.Flashcard;

public class AddFlashcardCommand extends Command {
    private static final String FLASHCARD_QUESTION_KEY = "q/";
    private static final String FLASHCARD_ANSWER_KEY = "a/";
    String question;
    String answer;
    private boolean isValid = true;

    public AddFlashcardCommand(String[] parts) throws QuizMosInputException {
        // return an invalid command if the command is not formatted correctly
        if (parts.length == 1
                || !parts[1].contains(FLASHCARD_QUESTION_KEY)
                || !parts[1].contains(FLASHCARD_ANSWER_KEY)) {
            throw new QuizMosInputException(FlashcardMessages.invalidAddCommand);
        }
        String args = parts[1];
        int qIndex = args.indexOf(FLASHCARD_QUESTION_KEY);
        int aIndex = args.indexOf(FLASHCARD_ANSWER_KEY);

        // handle reversed order or incorrect substring indices
        if (qIndex > aIndex) {
            throw new QuizMosInputException(FlashcardMessages.invalidAddCommand);
        }

        this.question = args.substring(qIndex + 2, aIndex).trim();
        this.answer = args.substring(aIndex + 2).trim();

        // Validate non-empty fields
        if (this.question.isEmpty() || this.answer.isEmpty()) {
            throw new QuizMosInputException(FlashcardMessages.invalidAddCommand);
        }
    }

    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws Exception {
        if (!isValid) {
            return;
        }

        assert flashcards != null : "FlashcardList should not be null";
        assert storage != null : "Storage should not be null";

        Flashcard flashcard = new Flashcard(question, answer);
        flashcards.addFlashcard(flashcard);
        storage.writeToFile(flashcards);

        Ui.respond(FlashcardMessages.showFlashcardAdded(flashcard));
    }
}
