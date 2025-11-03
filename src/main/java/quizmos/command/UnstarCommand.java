package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UnstarCommand extends Command {
    private static final Logger logger = Logger.getLogger("quizmos");
    private final int index;

    /**
     * Constructor to parse user-provided index.
     *
     * @param index String form of index (1-based from user input)
     */
    public UnstarCommand(String index) {
        this.index = Integer.parseInt(index) - 1;
    }

    /**
     * Executes the unstar command: validates input, updates data, and persists.
     *
     * @param flashcards list of all flashcards
     * @param storage    storage handler for saving changes
     * @throws QuizMosInputException if index invalid or flashcard already unstarred
     */
    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws QuizMosInputException {
        // --- Assertions: sanity checks for developer ---
        assert flashcards != null : "FlashcardList must not be null";
        assert storage != null : "Storage must not be null";
        assert index >= 0 : "Index must be non-negative";

        logger.log(Level.INFO, "Executing UnstarCommand with index: " + index);

        // --- Validate index range ---
        if (index > flashcards.getSize()-1) {
            logger.log(Level.WARNING, "Index out of range: " + index);
            throw new QuizMosInputException("Index is out of range!");
        }

        // --- Retrieve flashcard ---
        Flashcard unstarredFlashcard = flashcards.getFlashcard(index);
        assert unstarredFlashcard != null : "Retrieved flashcard should not be null";

        // --- Check if flashcard is already unstarred ---
        if (!unstarredFlashcard.checkIsStarred()) {
            logger.log(Level.WARNING, "Flashcard already unstarred at index " + index);
            throw new QuizMosInputException("This flashcard is already unstarred!");
        }

        // --- Toggle star and update lists ---
        unstarredFlashcard.toggleStar();
        flashcards.removeStarredFlashcard(unstarredFlashcard);
        Ui.respond(FlashcardMessages.showUnstarredFlashcard(unstarredFlashcard));
        logger.log(Level.INFO, "Unstarred flashcard: " + unstarredFlashcard.toString());

        // --- Save to storage ---
        try {
            storage.writeToFile(flashcards);
            logger.log(Level.INFO, "Updated storage after unstarring flashcard.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update storage after unstarring: " + e.getMessage());
            throw new QuizMosInputException("Failed to save changes after unstarring the flashcard.");
        }
    }
}
