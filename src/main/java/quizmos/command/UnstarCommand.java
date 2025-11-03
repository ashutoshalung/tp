package quizmos.command;

import quizmos.common.FlashcardMessages;
import quizmos.exception.QuizMosInputException;
import quizmos.flashcard.Flashcard;
import quizmos.flashcardlist.FlashcardList;
import quizmos.storage.Storage;
import quizmos.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to unstar a flashcard by its index.
 * This command removes the starred status of a flashcard, updates the
 * list of starred flashcards in FlashcardList, displays the updated
 * flashcard to the user via Ui, and persists the change in Storage.
 * Throws QuizMosInputException if the index is invalid or the flashcard
 * is already unstarred.
 * <p>
 * Logging is performed to track execution, errors, and persistence.
 */
public class UnstarCommand extends Command {
    private static final Logger logger = Logger.getLogger("quizmos");
    private final int index;

    /**
     * Constructs an UnstarCommand with the user-provided index.
     *
     * @param index the 1-based index of the flashcard to unstar (as String)
     *              internally converted to 0-based integer
     */
    public UnstarCommand(String index) {
        this.index = Integer.parseInt(index) - 1;
    }

    /**
     * Executes the UnstarCommand.
     *
     * @param flashcards the FlashcardList containing all flashcards; must not be null
     * @param storage    the Storage handler for persisting changes; must not be null
     * @throws QuizMosInputException if the index is out of range, the flashcard is already unstarred,
     *                               or if persistence fails
     */
    @Override
    public void execute(FlashcardList flashcards, Storage storage) throws QuizMosInputException {
        // --- Assertions: sanity checks for developer ---
        assert flashcards != null : "FlashcardList must not be null";
        assert storage != null : "Storage must not be null";
        assert index >= 0 : "Index must be non-negative";

        logger.log(Level.INFO, "Executing UnstarCommand with index: " + index);

        // --- Validate index range ---
        if (index > flashcards.getSize() - 1) {
            logger.log(Level.WARNING, "Index out of range: " + index);
            throw new QuizMosInputException("Index is out of range!");
        }

        // --- Retrieve flashcard ---
        Flashcard flashcardToBeUnstarred = flashcards.getFlashcard(index);
        assert flashcardToBeUnstarred != null : "Retrieved flashcard should not be null";

        // --- Check if flashcard is already unstarred ---
        if (!flashcardToBeUnstarred.checkIsStarred()) {
            logger.log(Level.WARNING, "Flashcard already unstarred at index " + index);
            throw new QuizMosInputException("This flashcard is already unstarred!");
        }

        // --- Toggle star and update lists ---
        flashcards.removeStarredFlashcard(flashcardToBeUnstarred);
        flashcardToBeUnstarred.toggleStar();
        Ui.respond(FlashcardMessages.showUnstarredFlashcard(flashcardToBeUnstarred));
        logger.log(Level.INFO, "Unstarred flashcard: " + flashcardToBeUnstarred.toString());

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
