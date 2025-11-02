package quizmos.command;

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

    public AddFlashcardCommand(String[] parts) {
        // return an invalid command if the command is not formatted correctly
        if (parts.length == 1
                || !parts[1].contains(FLASHCARD_QUESTION_KEY)
                || !parts[1].contains(FLASHCARD_ANSWER_KEY)) {
            Ui.invalidCommandRespond();
            isValid = false;
            return;
        }
        String args = parts[1];
        int qIndex = args.indexOf(FLASHCARD_QUESTION_KEY);
        int aIndex = args.indexOf(FLASHCARD_ANSWER_KEY);

        // handle reversed order or incorrect substring indices
        if (qIndex > aIndex) {
            Ui.invalidCommandRespond();
            this.question = null;
            this.answer = null;
            isValid = false;
            return;
        }

        this.question = args.substring(qIndex + 2, aIndex).trim();
        this.answer = args.substring(aIndex + 2).trim();

        // Validate non-empty fields
        if (this.question.isEmpty()) {
            Ui.invalidCommandRespond();
            isValid = false;
        } else if (this.answer.isEmpty()) {
            Ui.invalidCommandRespond();
            isValid = false;
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
        Ui.showFlashcardAdded(flashcard);
        storage.writeToFile(flashcards);
    }
}
